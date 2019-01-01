package com.pcms.weixin;


import java.util.Date;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;


public class ReplyMessage {


    //  获取关注事件
    public static RequestTextMessage getRequestFocus(String xml) {

        XStream xstream = new XStream(new DomDriver());

        xstream.alias("xml", RequestTextMessage.class);
        xstream.aliasField("ToUserName", RequestTextMessage.class, "toUserName");
        xstream.aliasField("FromUserName", RequestTextMessage.class, "fromUserName");
        xstream.aliasField("CreateTime", RequestTextMessage.class, "createTime");
        xstream.aliasField("MsgType", RequestTextMessage.class, "messageType");
        xstream.aliasField("Event", RequestTextMessage.class, "event");
        xstream.aliasField("EventKey", RequestTextMessage.class, "eventKey");
//            xstream.aliasField("Content", RequestTextMessage.class, "content");
//            xstream.aliasField("MsgId", RequestTextMessage.class, "msgId");
        RequestTextMessage requestTextMessage = (RequestTextMessage) xstream.fromXML(xml);
        return requestTextMessage;

    }

    //  获取推送文本消息
    public static RequestTextMessage getRequestTextMessage(String xml) {
        XStream xstream = new XStream(new DomDriver());
        xstream.alias("xml", RequestTextMessage.class);
        xstream.aliasField("URL", RequestTextMessage.class, "url");
        xstream.aliasField("ToUserName", RequestTextMessage.class, "toUserName");
        xstream.aliasField("FromUserName", RequestTextMessage.class, "fromUserName");
        xstream.aliasField("CreateTime", RequestTextMessage.class, "createTime");
        xstream.aliasField("MsgType", RequestTextMessage.class, "messageType");
        xstream.aliasField("Content", RequestTextMessage.class, "content");
        xstream.aliasField("MsgId", RequestTextMessage.class, "msgId");

        RequestTextMessage requestTextMessage = (RequestTextMessage) xstream.fromXML(xml);
        return requestTextMessage;
    }


    //    回复文本消息
    public static String getReplyTextMessage(String content, String fromUserName, String toUserName) {
        ReplyTextMessage we = new ReplyTextMessage();
        we.setMessageType("text");
        we.setFuncFlag("0");
        we.setCreateTime(new Date().getTime() + "");
        we.setContent(content);
        we.setToUserName(fromUserName);
        we.setFromUserName(toUserName);
        XStream xstream = new XStream(new DomDriver());
        xstream.alias("xml", ReplyTextMessage.class);
        xstream.aliasField("ToUserName", ReplyTextMessage.class, "toUserName");
        xstream.aliasField("FromUserName", ReplyTextMessage.class, "fromUserName");
        xstream.aliasField("CreateTime", ReplyTextMessage.class, "createTime");
        xstream.aliasField("MsgType", ReplyTextMessage.class, "messageType");
        xstream.aliasField("Content", ReplyTextMessage.class, "content");
        xstream.aliasField("FuncFlag", ReplyTextMessage.class, "funcFlag");
        String xml = xstream.toXML(we);
        return xml;

    }


    //    回复图文消息
    public static String getReplyPicTextMessage(String fromUserName, String toUserName, List<Article> articleList) {
        ReplyPicTextMessage we = new ReplyPicTextMessage();

        we.setMsgType("news");
        we.setCreateTime(new Long(new Date().getTime()).toString());
        we.setToUserName(fromUserName);
        we.setFromUserName(toUserName);
        we.setArticleCount(articleList.size() + "");
        we.setArticles(articleList);

        XStream xstream = new XStream(new DomDriver());
        xstream.alias("xml", ReplyPicTextMessage.class);
        xstream.aliasField("ToUserName", ReplyPicTextMessage.class, "toUserName");
        xstream.aliasField("FromUserName", ReplyPicTextMessage.class, "fromUserName");
        xstream.aliasField("CreateTime", ReplyPicTextMessage.class, "createTime");
        xstream.aliasField("MsgType", ReplyPicTextMessage.class, "messageType");
        xstream.aliasField("Article", ReplyPicTextMessage.class, "Article");

        xstream.aliasField("ArticleCount", ReplyPicTextMessage.class, "articleCount");
        xstream.aliasField("FuncFlag", ReplyPicTextMessage.class, "funcFlag");

        //xstream.aliasField("item", Article.class, "item");
        xstream.alias("item", new Article().getClass());
        xstream.aliasField("Title", Article.class, "title");
        xstream.aliasField("Description", Article.class, "description");
        xstream.aliasField("PicUrl", Article.class, "picUrl");
        xstream.aliasField("Url", Article.class, "url");
        String xml = xstream.toXML(we);
        return xml;
    }

}
