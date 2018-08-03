package com.pcms.service.impl;


import com.pcms.dao.UserinfoMapper;
import com.pcms.domain.Userinfo;
import com.pcms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service("userService")
public class UserServiceImpl implements UserService {


    @Autowired
    private UserinfoMapper userinfoMapper;


    public Userinfo getUserByParam(Map param){
        return userinfoMapper.selectByPrimaryKey(1L);
    }


    public boolean dologin(Userinfo user){
        Map map = new HashMap();
        map.put("username",user.getUsername());
        Userinfo userinfo = userinfoMapper.getUserinfoByParam(map);

        if(userinfo!=null && userinfo.getPassword()==user.getPassword()){
            return true;
        }
        return false;
    }



}
