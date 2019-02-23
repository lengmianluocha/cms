package com.pcms.controller.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pcms.domain.RequestMoive;
import com.pcms.service.MoiveService;
import com.pcms.service.impl.RedisService;
import com.pcms.service.impl.XiaoTokenService;
import com.pcms.util.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.weixin4j.util.TokenUtil;
import org.weixin4j.util.XStreamFactory;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * 提供外部调用，搜索查询
 */
@Controller
public class XIAOController {

    private Logger logger = LoggerFactory.getLogger(XIAOController.class);

    @Autowired
    private MoiveService moiveService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private XiaoTokenService xiaoTokenService;

    @Value("${pcms.xiao.sendCustomerMessageUrl}")
    private String sendCustomerMessageUrl;



    //第一次调用，用于微信服务器验证
    @RequestMapping(value = "/xiao", method = RequestMethod.GET)
    public void XIAOGET(HttpServletRequest request, HttpServletResponse response) throws IOException {

        logger.info("进入wx方法。。。。。。");
        try {
            String signature = request.getParameter("signature");
            String timestamp = request.getParameter("timestamp");
            String nonce = request.getParameter("nonce");
            String echostr = request.getParameter("echostr")==null?"":request.getParameter("echostr");

            String tToken = TokenUtil.get();

            //成为开发者验证
            //确认此次GET请求来自微信服务器，原样返回echostr参数内容，则接入生效，成为开发者成功，否则接入失败
//        if (TokenUtil.checkSignature(tToken, signature, timestamp, nonce)) {
//            response.getWriter().write(echostr);
//        }
            logger.info("echostr:"+echostr);
            response.getWriter().write(echostr);
        }catch (Exception e){
            e.printStackTrace();
            response.getWriter().write("");
        }



    }




    @RequestMapping(value = "/xiao", method = RequestMethod.POST)
    public void XIAOPOST(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("进入xiao方法。。。。。。");
        //消息来源可靠性验证
        String signature = request.getParameter("signature");// 微信加密签名
        String timestamp = request.getParameter("timestamp");// 时间戳
        String nonce = request.getParameter("nonce");       // 随机数

        String token = TokenUtil.get();

        //确认此次GET请求来自微信服务器，原样返回echostr参数内容，则接入生效，成为开发者成功，否则接入失败
        if (!TokenUtil.checkSignature(token, signature, timestamp, nonce)) {
            logger.error("验签不通过");
            //消息不可靠，直接返回
            response.getWriter().write("");
            return;
        }

        try {
            //获取POST流
            ServletInputStream in = request.getInputStream();
            String reqMsg = XStreamFactory.inputStream2String(in);

            logger.info("请求参数："+reqMsg);

            JSONObject reqJSON = JSONObject.parseObject(reqMsg);

            String msgtype  = reqJSON.getString("MsgType");

            if("event".equals(msgtype)){
                JSONObject resp = new JSONObject();
                resp.put("touser",reqJSON.getString("FromUserName"));
                resp.put("msgtype","text");
                JSONObject text = new JSONObject();
                text.put("content","输入电影名并以#开头");
                resp.put("text",text);

                String url = xiaoTokenService.getAccessToken();
                if(StringUtils.isNotBlank(url)){
                    String result= HttpClient.doPostForJson("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="+url,resp.toJSONString());
                    logger.info("实时回复用户打开小程序事件: "+result);
                    return;
                }else {
                    logger.info("未能获取小程序给用户发送请求地址，实时回复用户打开小程序事件:null ");
                }
            }


            if(StringUtils.equals(msgtype,"text")){
                String name = reqJSON.getString("Content");

                JSONObject resp = new JSONObject();
                resp.put("touser",reqJSON.getString("FromUserName"));
                resp.put("msgtype","text");
                JSONObject text = new JSONObject();
                if(name.startsWith("#")){
                    text.put("content","求片成功，请耐心等待通知");
                }else{
                    text.put("content","求片，请输入电影名并以#开头");
                }
                resp.put("text",text);

                //响应用户请求
                String url = xiaoTokenService.getAccessToken();
                if(StringUtils.isNotBlank(url)){
                    String result= HttpClient.doPostForJson("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="+url,resp.toJSONString());
                    logger.info("实时回复用户请求信息信息: "+result);
                }


                //非求片请求消息，直接返回。
                if(!name.startsWith("#")){
                    response.getWriter().write("");
                    return;
                }

                name = name.replaceAll("#","").trim();
                //用户请求入库
                //判断是否是求片请求
                //求片信息入库
                Map param = new HashMap();
                param.put("mname", name);
                RequestMoive hasRequest = moiveService.getRequestMoiveByParam(param);
                if (hasRequest != null) {
                    hasRequest.setCounter(hasRequest.getCounter() + 1);
                    moiveService.updateByPrimaryKeySelective(hasRequest);
                }else {
                    RequestMoive requestMoive = new RequestMoive();
                    requestMoive.setMoivename(name);
                    requestMoive.setMoivedesc("");
                    requestMoive.setCounter(1);
                    requestMoive.setStatus(PcmsConst.RequestMoive.STATUS_INIT);
                    requestMoive.setUpdatetime(DateUtil.getCurTimestamp());
                    moiveService.insertRequestMoive(requestMoive);
                }



                //小程序求片信息队列维护
                logger.info("======>小程序求片信息队列维护start<=========== ");
                //构建对象
                JSONObject newRequest = new JSONObject();
                newRequest.put("requestUserId",reqJSON.getString("FromUserName"));
                Calendar now = Calendar.getInstance();
                now.set(Calendar.DATE,now.get(Calendar.DATE)+2);
                newRequest.put("expire",now.getTimeInMillis());

                //获取原来的对象list
                String requestUsers = redisService.hget(RedisConts.REQUEST_MOIVE_KEY,UnicodeUtil.string2Unicode(name));
                JSONArray requestUserList=null;
                if(StringUtils.isNotBlank(requestUsers)){
                     requestUserList = JSONArray.parseArray(requestUsers);
                    //遍历 数据，将超时的数据剔除
                    requestUserList.add(newRequest);
                }else {
                     requestUserList=new JSONArray();
                    requestUserList.add(newRequest);
                }
                logger.info("求片用户信息列表 ："+requestUserList.toJSONString());
                long result= redisService.hset(RedisConts.REQUEST_MOIVE_KEY,UnicodeUtil.string2Unicode(name),requestUserList.toJSONString());
                logger.info("求片信息放入缓存结果： "+result);

                logger.info("======>小程序求片信息队列维护end<=========== ");
            }
        } catch (Exception e) {
            response.getWriter().write("");
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
//        JSONObject resp = new JSONObject();
//        resp.put("touser","oItyA4qWyN1x63y9tKsSCsltEILE");
//        resp.put("msgtype","text");
//        JSONObject text = new JSONObject();
//        text.put("content","Hello World");
//        resp.put("text",text);
//
//        String result= HttpClient.doPostForJson("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=17__wj7COEtuIxdhEmJXyrxevVX1AvkpYB0nRIPihibWxHAN0QZuBs2dbpqFSsT2AUY6KegYMBOx_fdje0hGcveN44LnuQK9NzxjU6WJd1cGeiB_f17o94nnR66RsVZCmG6ol0EsCecdYr6U89EZHYeAIATBS",resp.toJSONString());
//
//        System.out.println("http post result: "+result);

        JSONObject newRequest = new JSONObject();
        newRequest.put("requestUserId","3333333");
        Calendar now = Calendar.getInstance();
        now.set(Calendar.DATE,now.get(Calendar.DATE)+2);
        newRequest.put("expire",now.getTimeInMillis());

        System.out.println(newRequest.toJSONString());
    }



}

