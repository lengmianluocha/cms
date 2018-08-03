package com.pcms.service;

import com.pcms.domain.Userinfo;

import java.util.Map;

public interface UserService {

    public Userinfo getUserByParam(Map param);

    public boolean dologin(Userinfo user);
}
