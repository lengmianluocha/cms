package com.pcms.service;

import org.weixin4j.model.message.Articles;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface FileService {

    /**
     * 生成HTML
     *
     * @param param
     */
    public void genFile(Map param);

    public void deleFile(String filePath);

    /**
     * 解析上传的excel文件
     * @param file
     * @return
     */
    public ArrayList<ArrayList<String>> parseExcelFile(File file);


    public void genResultFile(List<Articles> articles,String user);

}
