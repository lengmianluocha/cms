package com.pcms.service.impl;

import com.pcms.domain.Moive;
import com.pcms.service.FileService;
import com.pcms.util.ExcelUtil;
import com.pcms.util.PcmsConst;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.weixin4j.model.message.Articles;

import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Service("fileService")
public class FileServiceImpl implements FileService {

    @Autowired
    private Configuration configuration;

    @Override
    public void genFile(Map param) {
        Moive moive = (Moive) param.get("moive");
        try {
            Template template = configuration.getTemplate("/vm/moivetmp.ftl");
            //获得保存静态资源路径
            // 创建文件对象
            File htmlFile = new File(PcmsConst.FILEPATH + "/" + moive.getId() + ".html");
            htmlFile.getParentFile().mkdirs();
            //创建map集合
            Map<String, Object> map = new HashMap<>();
            map.put("title", moive.getMname());
            map.put("encodetitle", URLEncoder.encode(moive.getMname(),"UTF-8"));
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

    @Override
    public void deleFile(String filePath) {
        try {
            if (StringUtils.isNotBlank(filePath)) {
                File file = new File(filePath);
                if (file.exists()) {
                    file.delete();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析上传的excel文件
     *
     * @param file
     * @return
     */
    @Override
    public ArrayList<ArrayList<String>> parseExcelFile(File file) {

        try {
            ArrayList<ArrayList<String>> arrayLists = ExcelUtil.xlsx_reader(file, 0, 1, 2);
            return arrayLists;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public void genResultFile(List<Articles> articles, String user) {

        try {
            //获得保存静态资源路径
            Template template = configuration.getTemplate("/vm/qresult.ftl");

            // 创建文件对象
            File htmlFile = new File(PcmsConst.RESULTPATH + "/" + user + ".html");
            htmlFile.getParentFile().mkdirs();

            StringBuilder builder = new StringBuilder();

            for (Articles article : articles) {
                builder.append("<a href='" + article.getUrl() + "'><h3>" + article.getTitle() + "</h3></a>");
                builder.append("<br/>");
            }
            builder.append("<br/>");


            //创建map集合
            Map<String, Object> map = new HashMap<>();

            map.put("content", builder.toString());


            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(htmlFile), "UTF-8"));
            // 合并输出 创建页面文件
            template.process(map, out);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
