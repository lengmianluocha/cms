package com.pcms.service;

import com.pcms.domain.RequestMoive;
import com.pcms.domain.Userinfo;

import java.util.List;
import java.util.Map;

public interface UserService {

    public Userinfo getUserByParam(Map param);

    public boolean dologin(Userinfo user);

    int getUserCountByParam(Map param);

    List<Userinfo> searchUserByParam(Map param);
}
