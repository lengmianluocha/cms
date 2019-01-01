package com.pcms.service;

public interface CrawlerService {

    /**
     * 判断百度网盘地址地址是否有效
     *
     * @param path
     * @return
     */
    public boolean isOk(String path);


    /**
     * 获取 电影的别名
     *
     * @param name
     */
    public void getTag(String name);
}
