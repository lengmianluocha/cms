package com.pcms.service.impl;

import com.pcms.service.CrawlerService;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
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


//            MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE, "", Charset.defaultCharset());
//            multipartEntity.addPart("pwd", new StringBody("1111", Charset.forName("UTF-8")));


            HttpPost request = new HttpPost("https://pan.baidu.com/share/verify?surl=JR32hajJo56iBlNSfU-02Q&t=1546439428392&channel=chunlei&web=1&app_id=250528&bdstoken=null&logid=MTU0NjQzOTQyODM5NDAuODY3MDYxNjQ5OTk0MDI0Mg==&clienttype=0");
//            request.addHeader("Host", " pan.baidu.com");
//            request.addHeader("Connection", "keep-alive");
//            request.addHeader("Content-Length", " 26");
//            request.addHeader(" Accept", " */*");
//            request.addHeader("Origin", " https://pan.baidu.com");
//            request.addHeader("X-Requested-With", " XMLHttpRequest");
//            request.addHeader("User-Agent", " Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36");
//            request.addHeader("Content-Type", " application/x-www-form-urlencoded; charset=UTF-8");
//            request.addHeader("Referer", " https://pan.baidu.com/share/init?surl=JR32hajJo56iBlNSfU-02Q");
//            request.addHeader("Accept-Encoding", " gzip, deflate, br");
//            request.addHeader("Accept-Language:", "zh-CN,zh;q=0.9");
            //request.addHeader("Cookie", "PANWEB=1; BAIDUID=26122992E6C760C7C059EF2BBBB7517D:FG=1; Hm_lvt_7a3960b6f067eb0085b7f96ff5e660b0=1546438291; BIDUPSID=26122992E6C760C7C059EF2BBBB7517D; PSTM=1546438572; delPer=0; BDORZ=B490B5EBF6F3CD402E515D22BCDA1598; PSINO=3; H_PS_PSSID=1433_21119_18560_28205_28131_28139_27508; Hm_lpvt_7a3960b6f067eb0085b7f96ff5e660b0=1546439232");

//            HttpParams params =new DefaultedHttpParams();
//            request.setParams(params);

            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpResponse response = httpClient.execute(request);

            InputStream is = response.getEntity().getContent();
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = in.readLine()) != null) {
                buffer.append(line);
            }

            System.out.println("发送消息收到的返回：" + buffer.toString());


        } catch (Exception e) {

        }

    }


}
