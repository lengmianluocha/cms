package com.pcms.dao;

import com.pcms.domain.WxInput;

public interface WxInputMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WxInput record);

    int insertSelective(WxInput record);

    WxInput selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WxInput record);

    int updateByPrimaryKey(WxInput record);
}