package com.pcms.dao;

import com.pcms.domain.Userinfo;

import java.util.List;
import java.util.Map;

public interface UserinfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Userinfo record);

    int insertSelective(Userinfo record);

    Userinfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Userinfo record);

    int updateByPrimaryKey(Userinfo record);

    Userinfo getUserinfoByParam(Map param);

    int getUserCountByParam(Map param);

    List<Userinfo> searchUserByParam(Map param);
}