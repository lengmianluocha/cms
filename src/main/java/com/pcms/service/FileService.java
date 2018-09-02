package com.pcms.service;

import java.util.Map;

public interface FileService {

    /**
     * 生成HTML
     *
     * @param param
     */
    public void genFile(Map param);

    public void deleFile(String filePath);
}
