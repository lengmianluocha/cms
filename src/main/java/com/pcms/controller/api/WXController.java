package com.pcms.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.pcms.domain.MoiveFail;
import com.pcms.domain.RequestMoive;
import com.pcms.handler.AtsNormalMessageHandler;
import com.pcms.service.MoiveService;
import com.pcms.util.DateUtil;
import com.pcms.util.PcmsConst;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.weixin4j.WeixinException;
import org.weixin4j.spi.DefaultMessageHandler;
import org.weixin4j.util.TokenUtil;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 提供外部调用，搜索查询
 */
@RestController
public class WXController {

    private Logger logger = LoggerFactory.getLogger(WXController.class);

    @Autowired
    private AtsNormalMessageHandler atsNormalMessageHandler;

    @Autowired
    private MoiveService moiveService;

    //第一次调用，用于微信服务器验证
    @RequestMapping(value = "/wx", method = RequestMethod.GET)
    public void WXGET(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");

        String tToken = TokenUtil.get();

        //成为开发者验证
        //确认此次GET请求来自微信服务器，原样返回echostr参数内容，则接入生效，成为开发者成功，否则接入失败
        if (TokenUtil.checkSignature(tToken, signature, timestamp, nonce)) {
            response.getWriter().write(echostr);
        }
    }

    @RequestMapping(value = "/wx", method = RequestMethod.POST)
    public void WXPOST(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("进入wx方法。。。。。。");
        //消息来源可靠性验证
        String signature = request.getParameter("signature");// 微信加密签名
        String timestamp = request.getParameter("timestamp");// 时间戳
        String nonce = request.getParameter("nonce");       // 随机数
        //Token为weixin4j.properties中配置的Token
        String token = TokenUtil.get();

        //确认此次GET请求来自微信服务器，原样返回echostr参数内容，则接入生效，成为开发者成功，否则接入失败
        if (!TokenUtil.checkSignature(token, signature, timestamp, nonce)) {
            logger.error("验签不通过");
            //消息不可靠，直接返回
            response.getWriter().write("");
            return;
        }
        //用户每次向公众号发送消息、或者产生自定义菜单点击事件时，响应URL将得到推送
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/xml");
            //获取POST流
            ServletInputStream in = request.getInputStream();
            //非注解方式，依然采用消息处理工厂模式调用
            //IMessageHandler messageHandler = HandlerFactory.getMessageHandler();

            DefaultMessageHandler messageHandler = new DefaultMessageHandler(atsNormalMessageHandler, null);
            //处理输入消息，返回结果
            String xml = messageHandler.invoke(in);

            logger.info("响应:" + xml);
            //返回结果
            response.getWriter().write(xml);
        } catch (IOException | WeixinException ex) {
            response.getWriter().write("");
        }
    }


    /**
     * 求电影页面
     *
     * @param mav
     * @return
     */
    @RequestMapping("/moive/qmoive")
    public ModelAndView requestListView(ModelAndView mav) {
        mav.setViewName("moive/moiveNotFound");
        return mav;
    }


    @RequestMapping(value = "/moive/request", method = RequestMethod.POST)
    @ResponseBody
    public String requestOrAdvice(HttpServletRequest request, HttpServletResponse response) {
        JSONObject rep = new JSONObject();
        //TODO 获取微信用户的信息
        String name = request.getParameter("moiveName");
        String desc = request.getParameter("moiveDesc");

        if (StringUtils.isBlank(name)) {

        }
        RequestMoive requestMoive = new RequestMoive();
        requestMoive.setMoivename(name);
        requestMoive.setMoivedesc(desc);
        requestMoive.setStatus(PcmsConst.RequestMoive.STATUS_INIT);
        requestMoive.setUpdatetime(DateUtil.getCurTimestamp());

        moiveService.insertRequestMoive(requestMoive);

        rep.put("desc", "");
        rep.put("result", "success");
        return rep.toJSONString();
    }


    /**
     * 链接失效
     *
     * @param mav
     * @return
     */
    @RequestMapping("/moive/limoive")
    public ModelAndView linkInvalid(ModelAndView mav) {
        mav.setViewName("moive/linkInvalid");
        return mav;
    }

    @RequestMapping(value = "/moive/invalid", method = RequestMethod.POST)
    @ResponseBody
    public String invalid(HttpServletRequest request, HttpServletResponse response) {
        JSONObject rep = new JSONObject();
        //TODO 获取微信用户的信息
        String name = request.getParameter("moiveName");
        if (StringUtils.isBlank(name)) {
        }
        MoiveFail moiveFail = new MoiveFail();
        moiveFail.setMoivename(name);
        moiveFail.setStatus(PcmsConst.RequestMoive.STATUS_INIT);
        moiveFail.setUpdatetime(DateUtil.getCurTimestamp());

        moiveService.insertMoiveFail(moiveFail);

        rep.put("desc", "");
        rep.put("result", "success");
        return rep.toJSONString();
    }

}

