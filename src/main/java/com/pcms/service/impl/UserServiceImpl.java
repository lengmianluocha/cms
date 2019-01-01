package com.pcms.service.impl;


import com.pcms.dao.UserinfoMapper;
import com.pcms.domain.Userinfo;
import com.pcms.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserinfoMapper userinfoMapper;

    public Userinfo getUserByParam(Map param) {
        return userinfoMapper.selectByPrimaryKey(1L);
    }

    public boolean dologin(Userinfo user) {
        Map map = new HashMap();
        map.put("username", user.getUsername());
        Userinfo userinfo = userinfoMapper.getUserinfoByParam(map);
        if (userinfo != null && StringUtils.equals(user.getPassword(), userinfo.getPassword())) {
            return true;
        }
        return false;
    }

    public int getUserCountByParam(Map param) {
        return userinfoMapper.getUserCountByParam(param);
    }

    public List<Userinfo> searchUserByParam(Map param) {
        return userinfoMapper.searchUserByParam(param);
    }


}
