package com.pcms.service.impl;

import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

@Service
public class RedisService {

    Jedis jedis;

    {
        String host = "172.27.0.2";
        int port = 6379;
        String instanceid = "crs-c9ar95cg";
        String password = "dj5LLOHBVx";
        //连接Redis
        jedis = new Jedis(host, port);
        //鉴权
        jedis.auth(instanceid + ":" + password);
    }


    //=============================common============================
    /**
     * 指定缓存失效时间
     * @param key 键
     * @param time 时间(秒)
     * @return
     */
    public boolean expire(String key,int time){
        try {
            if(time>0){
                jedis.expire(key,time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断key是否存在
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key){
        try {
            return jedis.exists(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除缓存
     * @param key 可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
    public void del(String ... key){
        if(key!=null&&key.length>0){
            if(key.length==1){
                jedis.del(key[0]);
            }else{
                jedis.del(key);
            }
        }
    }

    //============================String=============================
    /**
     * 普通缓存获取
     * @param key 键
     * @return 值
     */
    public String get(String key){
        return key==null?null:jedis.get(key);
    }

    /**
     * 普通缓存放入
     * @param key 键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean set(String key,String value) {
        try {
            jedis.set(key,value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 普通缓存放入并设置时间
     * @param key 键
     * @param value 值
     * @param time 时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean set(String key,String value,int time){
        try {
            if(time>0){
                jedis.set(key,value);
                jedis.expire(key,time);
            }else{
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 递增
     * @param key 键
     * @return
     */
    public long incr(String key, long delta){
        if(delta<0){
            throw new RuntimeException("递增因子必须大于0");
        }
        return jedis.incrBy(key, delta);
    }

    /**
     * 递减
     * @param key 键
     * @return
     */
    public long decr(String key, long delta){
        if(delta<0){
            throw new RuntimeException("递减因子必须大于0");
        }
        return jedis.decrBy(key, -delta);
    }

    //================================Map=================================
    /**
     * HashGet
     * @param key 键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public String hget(String key,String item){
        return jedis.hget(key, item);
    }

    public long hset(String key,String Field,String value){
        return jedis.hset(key,Field,value);
    }
}

