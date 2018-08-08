package com.pcms.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.pcms.domain.Moive;
import com.pcms.service.MoiveService;
import com.pcms.weixin.Article;
import com.pcms.weixin.ReplyMessage;
import com.pcms.weixin.RequestTextMessage;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * 提供外部调用，搜索查询
 */
@RestController()
public class WXController {

    private Logger logger = LoggerFactory.getLogger(WXController.class);

    @Autowired
    private MoiveService moiveService;


    // 设置一个全局的token,开发者自己设置。api这样解释：Token可由开发者可以任意填写，
    // 用作生成签名（该Token会和接口URL中包含的Token进行比对，从而验证安全性）
    private String TOKEN = "hello123455";



    //第一次调用，用于微信服务器验证
    @RequestMapping(value = "/wx",method= RequestMethod.GET)
    public String WXGET(HttpServletRequest request, HttpServletResponse response){

        String  signature= request.getParameter("signature");
        String  timestamp= request.getParameter("timestamp");
        String  nonce= request.getParameter("nonce");
        String  echostr= request.getParameter("echostr");
        String  token= request.getParameter("token");

        List arr = new ArrayList();

        arr.add(signature);
        arr.add(timestamp);
        arr.add(nonce);

        Collections.sort(arr);

        return echostr;
    }

    @RequestMapping(value = "/wx",method= RequestMethod.POST)
    public String WXPOST(HttpServletRequest request, HttpServletResponse response){

        response.setContentType("text/html;charset=UTF-8");
        RequestTextMessage textMsg =null ;
        String returnXml ="";

        try {


            PrintWriter pw = response.getWriter();
            String wxMsgXml =IOUtils.toString(request.getInputStream(), "utf-8");

            logger.info("获取的数据信息>>>>>"+wxMsgXml);

            textMsg = ReplyMessage.getRequestTextMessage(wxMsgXml);//解析用户输入的信息
            String content = textMsg.getContent().trim();//用户发送信息
            String openId= textMsg.getFromUserName().trim();//发送方账号（OpenID）
            String creattime=textMsg.getCreateTime();
            String msgType=textMsg.getMessageType();// 发送类型
            String tousername=textMsg.getToUserName();//用户微信号
            boolean  flag= isContainsNumOrLetter(content);


            logger.error(content);

            Map map = new HashMap();
            map.put("mnamelike", content);
            List<Moive> list = moiveService.searchMoiveByParam(map);
            List<Article> articles=new ArrayList<>(list.size());
            for(Moive moive :list){
                Article article= new Article();
                article.setDescription(moive.getAbstracts());
                article.setTitle(moive.getMname());
                //article.setPicUrl();
                articles.add(article);
            }

            returnXml = ReplyMessage.getReplyPicTextMessage(tousername, openId,articles);


            logger.info("wxMsgXml>>>>>>>>>>>>>>"+wxMsgXml);
            logger.info("returnXml>>>>>>>>>>>>>>"+returnXml);
            pw.println(returnXml);

        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

        return "success";

    }

    @RequestMapping(value = "/moive/search",method= RequestMethod.GET)
    public String SearchMovie(@RequestParam("keyword") String keyword) {

        Map map = new HashMap();
        map.put("mnamelike", keyword);

        List<Moive> list = moiveService.searchMoiveByParam(map);

        return JSONObject.toJSONString(list);
    }

    @RequestMapping(value = "/moive/search",method= RequestMethod.POST)
    public String SearchMovie2(@RequestParam("keyword") String keyword) {

        Map map = new HashMap();
        map.put("mnamelike", keyword);

        List<Moive> list = moiveService.searchMoiveByParam(map);

        return JSONObject.toJSONString(list);
    }


    /*
     * 第一步：验证服务器地址的有效性 开发者提交信息后，微信服务器将发送GET请求到填写的服务器地址URL上，
     * GET请求携带四个参数：signature、timestamp、nonce、echostr
     * 开发者通过检验signature对请求进行校验（下面有校验方式）。 若确认此次GET请求来自微信服务器，请原样返回echostr参数内容，
     * 则接入生效， 成为开发者成功，否则接入失败。
     *
     * 加密/校验流程如下：
     * 1. 将token、timestamp、nonce三个参数进行字典序排序
     * 2. 将三个参数字符串拼接成一个字符串进行sha1加密
     * 3. 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
     *
     *  字典排序（lexicographical order）是一种对于随机变量形成序列的排序方法。其方法是，按照字母顺序，或者数字小大顺序，由小到大的形成序列。
     */
    private boolean checkToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean flag = false;
        // 微信加密签名
        String signature = request.getParameter("signature");
        // 随机字符串
        String echostr = request.getParameter("echostr");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");

        String[] str = { TOKEN, timestamp, nonce };
        Arrays.sort(str); // 字典序排序

        String bigStr = str[0] + str[1] + str[2];
        // SHA1加密
        //String digest = new SHA1().getDigestOfString(bigStr.getBytes()).toLowerCase();
        String digest=null;
        // 确认请求来至微信
        if (digest.equals(signature)) {
            response.getWriter().print(echostr);
            flag = true;
        }else {
            System.out.println("TAG"+"接入失败");
        }
        return flag;
    }


    /**
     *
     * @Title: isContainsNumOrLetter
     * @Description: TODO（判断字符是否符合要求即包含数字和字母且长度为12）
     * @param @param str
     * @param @return    参数
     * @return boolean    返回类型
     * @throws
     */
    private  boolean isContainsNumOrLetter(String  str) {
        boolean flag = false;

        boolean isDigit = false;//定义一个boolean值，用来表示是否包含数字
        boolean isLetter = false;//定义一个boolean值，用来表示是否包含字母
        //假设有一个字符串
        for (int i=0 ; i<str.length() ; i++){ //循环遍历字符串
            if (Character.isDigit(str.charAt(i))){     //用char包装类中的判断数字的方法判断每一个字符
                isDigit = true;
            }
            if (Character.isLetter(str.charAt(i))){   //用char包装类中的判断字母的方法判断每一个字符
                isLetter = true;
            }
        }
        /*循环完毕以后
         *如果isDigit为true，则代表字符串中包含数字，否则不包含
         *如果isLetter为true，则代表字符串中包含字母，否则不包含
         */
        //System.out.println(isDigit);          //System.out.println(isLetter);

        if(isDigit && isLetter) {
            //根据字符长度、判断输入是Wifi 还是蓝牙
            int len=str.length();
            if( len == 12) {
                flag = true;
            }
        }
        return flag;
    }

}

