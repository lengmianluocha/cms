package com.pcms.handler;

import com.pcms.domain.Moive;
import com.pcms.service.MoiveService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.weixin4j.model.message.Articles;
import org.weixin4j.model.message.OutputMessage;
import org.weixin4j.model.message.normal.ImageInputMessage;
import org.weixin4j.model.message.normal.LinkInputMessage;
import org.weixin4j.model.message.normal.LocationInputMessage;
import org.weixin4j.model.message.normal.ShortVideoInputMessage;
import org.weixin4j.model.message.normal.TextInputMessage;
import org.weixin4j.model.message.normal.VideoInputMessage;
import org.weixin4j.model.message.normal.VoiceInputMessage;
import org.weixin4j.model.message.output.NewsOutputMessage;
import org.weixin4j.model.message.output.TextOutputMessage;
import org.weixin4j.spi.INormalMessageHandler;

import java.util.*;

@Component
public class AtsNormalMessageHandler implements INormalMessageHandler {

    protected final Logger logger = LoggerFactory.getLogger(AtsNormalMessageHandler.class);

    @Autowired
    private MoiveService moiveService;

    @Override
    public OutputMessage textTypeMsg(TextInputMessage msg) {
        logger.info("文本消息：" + msg.getContent());

        Map map = new HashMap();
        map.put("mnamelike", msg.getContent());
        List<Moive> list = moiveService.searchMoiveForWXByParam(map);

        if (list == null || list.size() == 0) {
            NewsOutputMessage outno = new NewsOutputMessage();
            outno.setCreateTime(new Date().getTime());
            outno.setFromUserName(msg.getFromUserName());
            outno.setToUserName(msg.getToUserName());
            Articles article = new Articles();

            article.setDescription("暂无简介");
            article.setTitle("很遗憾，没有找到您要的电影，请戳求片页留言");
            article.setUrl("http://www.nitethoughts.club/moive/qmoive");
            List<Articles> articles = new ArrayList<>(list.size());
            articles.add(article);
            outno.setArticles(articles);
            return outno;
        }

        NewsOutputMessage out = new NewsOutputMessage();
        out.setCreateTime(new Date().getTime());
        //out.setFromUserName(msg.getToUserName());
        //out.setToUserName(msg.getFromUserName());
        out.setFromUserName(msg.getFromUserName());
        out.setToUserName(msg.getToUserName());

        List<Articles> articles = new ArrayList<>(list.size());
        for (Moive moive : list) {
            Articles article = new Articles();
            if(StringUtils.isBlank(moive.getAbstracts())){
                article.setDescription("暂无简介");
            }else{
                article.setDescription(moive.getAbstracts());
            }
            article.setTitle(moive.getMname());
            article.setUrl(moive.getMurl());
            articles.add(article);
        }
        out.setArticles(articles);
        return out;
    }

    @Override
    public OutputMessage imageTypeMsg(ImageInputMessage msg) {
        TextOutputMessage out = new TextOutputMessage();
        out.setContent("你的消息已经收到！");
        return out;
    }

    @Override
    public OutputMessage voiceTypeMsg(VoiceInputMessage msg) {

        logger.info("语音消息识别结果："+msg.getRecognition());

        Map map = new HashMap();

        String keyword = msg.getRecognition().replace("。","");
        logger.info("语音消息排除标点",keyword);
        map.put("mnamelike", keyword);
        List<Moive> list = moiveService.searchMoiveForWXByParam(map);

        if (list == null || list.size() == 0) {

            NewsOutputMessage outno = new NewsOutputMessage();
            outno.setCreateTime(new Date().getTime());
            outno.setFromUserName(msg.getFromUserName());
            outno.setToUserName(msg.getToUserName());
            Articles article = new Articles();

            article.setDescription("暂无简介");
            article.setTitle("很遗憾，没有找到您要的电影，请戳求片页留言");
            article.setUrl("http://www.nitethoughts.club/moive/qmoive");
            List<Articles> articles = new ArrayList<>(list.size());
            articles.add(article);
            outno.setArticles(articles);
            return outno;

//            TextOutputMessage out = new TextOutputMessage();
//            out.setCreateTime(new Date().getTime());
//            out.setFromUserName(msg.getToUserName());
//            out.setToUserName(msg.getFromUserName());
//
//            String cont = "很遗憾，没有找到相关内容，请换其它关键词试试。由于微信只能返回一条结果，请输入电影全称；如没有搜到电影，请移步求片页";
//            out.setContent(cont);
//            return out;




        }

        NewsOutputMessage out = new NewsOutputMessage();
        out.setCreateTime(new Date().getTime());
        //out.setFromUserName(msg.getToUserName());
        //out.setToUserName(msg.getFromUserName());
        out.setFromUserName(msg.getFromUserName());
        out.setToUserName(msg.getToUserName());

        List<Articles> articles = new ArrayList<>(list.size());
        for (Moive moive : list) {
            Articles article = new Articles();
            if(StringUtils.isBlank(moive.getAbstracts())){
                article.setDescription("暂无简介");
            }else{
                article.setDescription(moive.getAbstracts());
            }

            article.setTitle(moive.getMname());
            article.setUrl(moive.getMurl());
            articles.add(article);
        }
        out.setArticles(articles);
        return out;
    }

    @Override
    public OutputMessage videoTypeMsg(VideoInputMessage msg) {
        TextOutputMessage out = new TextOutputMessage();
        out.setContent("你的消息已经收到！");
        return out;
    }

    @Override
    public OutputMessage shortvideoTypeMsg(ShortVideoInputMessage msg) {
        TextOutputMessage out = new TextOutputMessage();
        out.setContent("你的消息已经收到！");
        return out;
    }

    @Override
    public OutputMessage locationTypeMsg(LocationInputMessage msg) {
        TextOutputMessage out = new TextOutputMessage();
        out.setContent("你的消息已经收到！");
        return out;
    }

    @Override
    public OutputMessage linkTypeMsg(LinkInputMessage msg) {
        TextOutputMessage out = new TextOutputMessage();
        out.setContent("你的消息已经收到！");
        return out;
    }
}
