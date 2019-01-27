package com.pcms.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.pcms.util.HttpClient;
import com.pcms.util.RedisConts;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 用于获取小程序 access_token 工具类
 */
@Service("xiaoTokenService")
public class XiaoTokenService {

    private Logger logger = LoggerFactory.getLogger(XiaoTokenService.class);

    @Autowired
    private RedisService redisService;

    @Value("${pcms.xiao.access_token_url}")
    private String accessTokenUrl;


    public String getAccessToken(){
        String accessToken  = redisService.get(RedisConts.XIAO_ACCESS_TOKEN_KEY);

        if(StringUtils.isNotBlank(accessToken)){
            logger.info("从redis 中获得 accessToken： "+accessToken);
            return accessToken;
        }else {
            String result = HttpClient.doGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxbbcbac036e9b5f61&secret=046c21d02b55d4db89c52811255c93b7");
            logger.info("调用请求获得  accessToken： "+result);
            JSONObject reslt = JSONObject.parseObject(result);

            String access_token=reslt.getString("access_token");

            if(StringUtils.isNotBlank(access_token)){
                redisService.set(RedisConts.XIAO_ACCESS_TOKEN_KEY,access_token,6000);
                return accessToken;
            }
            return "";
        }
    }

    public static void main(String[] args) {
        String result = HttpClient.doGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxbbcbac036e9b5f61&secret=046c21d02b55d4db89c52811255c93b7");
        System.out.println("调用请求获得  accessToken： "+result);

    }
}
