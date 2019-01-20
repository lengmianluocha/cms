package com.pcms.util;

import redis.clients.jedis.Jedis;

public class HelloRedis {

    public static void main(String[] args) {
        try {
            /**以下参数分别填写您的Redis实例内网IP，端口号，实例ID和密码*/
            String host = "172.27.0.2";
            int port = 6379;
            String instanceid = "redis01";
            String password = "dj5LLOHBVx";
            //连接Redis
            Jedis jedis = new Jedis(host, port);

            //鉴权
            jedis.auth(instanceid + ":" + password);

            /**接下来可以开始操作Redis实例，可以参考：https://github.com/xetorthio/jedis */
            //设置Key
            jedis.set("redis", "tencent");

            System.out.println("set key redis suc, value is: tencent");
            //获取Key
            String value = jedis.get("redis");
            System.out.println("get key redis is: " + value);

            //关闭退出
            jedis.quit();
            jedis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
