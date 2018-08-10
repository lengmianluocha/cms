package com.pcms.service.impl;

import com.pcms.domain.Moive;
import com.pcms.service.FileService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Service("fileService")
public class FileServiceImpl implements FileService {

    @Autowired
    private Configuration configuration;

    @Override
    public void genFile(Map param) {

        Moive moive = (Moive) param.get("moive");

        String id = String.format("%06d", moive.getId());
        try {
            Template template = configuration.getTemplate("/vm/moivetmp.ftl");
            //获得保存静态资源路径
            //String htmlRealPath = "/Users/feng/html";
            String htmlRealPath = "/usr/local/website";



            //实际部署保存路径
            //String htmlRealPath=contentHtmlLocation;
            //System.out.println("保存的绝对路径是:" + htmlRealPath + "/" + id + ".html");
            // 创建文件对象
            File htmlFile = new File(htmlRealPath + "/" + id + ".html");
            htmlFile.getParentFile().mkdirs();
            //创建map集合
            Map<String, Object> map = new HashMap<>();
            map.put("title", moive.getMname());
            map.put("content", moive.getAbstracts());
            map.put("panurl", moive.getPanurl());
            map.put("panpwd", moive.getPanpwd());

            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(htmlFile), "UTF-8"));
            // 合并输出 创建页面文件
            template.process(map, out);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
