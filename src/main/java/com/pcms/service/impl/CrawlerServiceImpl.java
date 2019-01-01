package com.pcms.service.impl;

import com.pcms.service.CrawlerService;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


@Service("crawlerService")
public class CrawlerServiceImpl implements CrawlerService {

    private Logger logger = LoggerFactory.getLogger(CrawlerServiceImpl.class);

    @Override
    public boolean isOk(String path) {
        try {
            URL url = new URL(path);
            HttpURLConnection urlcon = (HttpURLConnection) url.openConnection();
            urlcon.connect();         //获取连接
            InputStream is = urlcon.getInputStream();
            BufferedReader buffer = new BufferedReader(new InputStreamReader(is));
            StringBuffer bs = new StringBuffer();
            String l = null;
            while ((l = buffer.readLine()) != null) {
                bs.append(l).append("/n");
            }

            if (bs.toString().contains("页面不存在") || bs.toString().contains("链接已失效")) {
                return false;
            }
        } catch (Exception e) {
            logger.error("程序判断网盘地址是否失效过程，出错。", e);
            return false;
        }
        return true;
    }

    @Override
    public void getTag(String name) {
        try {
            URL url = new URL("https://baike.baidu.com/item/" + "你好");
            HttpURLConnection urlcon = (HttpURLConnection) url.openConnection();
            urlcon.connect();         //获取连接
            InputStream is = urlcon.getInputStream();
            BufferedReader buffer = new BufferedReader(new InputStreamReader(is));
            StringBuffer bs = new StringBuffer();
            String l = null;

            while ((l = buffer.readLine()) != null) {
                bs.append(l).append("/n");
            }

            Document doc = Jsoup.parse(bs.toString());

            Elements elements = doc.getElementsByClass("basicInfo-item name");
            for (Element element : elements) {
                if (StringUtils.equals(element.text(), "外文名")) {
                    Element va = element.nextElementSibling();
                    String value = va.text().replaceAll("/n", "");

                }
            }
        } catch (Exception e) {

        }
    }

    public static void main(String[] args) {
        try {
            URL url = new URL("https://baike.baidu.com/item/" + "无敌破坏王2：大闹互联网 Ralph Breaks the Internet (2018)");
            HttpURLConnection urlcon = (HttpURLConnection) url.openConnection();
            urlcon.connect();         //获取连接
            InputStream is = urlcon.getInputStream();
            BufferedReader buffer = new BufferedReader(new InputStreamReader(is));
            StringBuffer bs = new StringBuffer();
            String l = null;

            while ((l = buffer.readLine()) != null) {
                bs.append(l).append("/n");
            }

            Document doc = Jsoup.parse(bs.toString());

            Elements elements = doc.getElementsByClass("basicInfo-item name");
            for (Element element : elements) {
                if (StringUtils.equals(element.text(), "外文名")) {
//                    Element va = element.nextElementSibling();
//                    String value = va.text().replaceAll("/n","");

                }
            }
        } catch (Exception e) {

        }

    }


}
