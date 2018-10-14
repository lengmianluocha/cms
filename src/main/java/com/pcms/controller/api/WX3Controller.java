package com.pcms.controller.api;

import com.pcms.domain.Moive;
import com.pcms.service.MoiveService;
import com.pcms.weixin.Article;
import com.pcms.weixin.ReplyMessage;
import com.pcms.weixin.RequestTextMessage;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * 提供外部调用，搜索查询
 */
//@RestController
public class WX3Controller {

    private Logger logger = LoggerFactory.getLogger(WX3Controller.class);

    @Autowired
    private MoiveService moiveService;


    //@RequestMapping(value = "/wx", method = RequestMethod.POST)
    public void WXPOST(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=UTF-8");
        RequestTextMessage textMsg = null;
        String returnXml = "";
        try {
            PrintWriter pw = response.getWriter();
            String wxMsgXml = IOUtils.toString(request.getInputStream(), "utf-8");

            logger.info("获取的数据信息>>>>>" + wxMsgXml);

            textMsg = ReplyMessage.getRequestTextMessage(wxMsgXml);//解析用户输入的信息
            String content = textMsg.getContent().trim();//用户发送信息
            String openId = textMsg.getFromUserName().trim();//发送方账号（OpenID）
            String creattime = textMsg.getCreateTime();
            String msgType = textMsg.getMessageType();// 发送类型
            String tousername = textMsg.getToUserName();//用户微信号

            Map map = new HashMap();
            map.put("mnamelike", content);
            List<Moive> list = moiveService.searchMoiveForWXByParam(map);

            if (list == null || list.size() == 0) {
                String cont = "很遗憾，没有找到相关内容，请换其它关键词试试。";
                returnXml = ReplyMessage.getReplyTextMessage(cont, openId, tousername);
                //logger.info("wxMsgXml>>>>>>>>>>>>>>" + wxMsgXml);
                logger.info("returnXml>>>>>>>>>>>>>>" + returnXml);
                pw.println(returnXml);
            }

            List<Article> articles = new ArrayList<>(list.size());
            for (Moive moive : list) {
                Article article = new Article();
                article.setDescription(moive.getAbstracts());
                article.setTitle(moive.getMname());
                article.setUrl(moive.getMurl());
                articles.add(article);
            }
            returnXml = ReplyMessage.getReplyPicTextMessage(openId, tousername, articles);
            //logger.info("wxMsgXml>>>>>>>>>>>>>>" + wxMsgXml);
            logger.info("returnXml>>>>>>>>>>>>>>" + returnXml);
            pw.println(returnXml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

