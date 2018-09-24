package com.pcms.dao;

import com.pcms.domain.RequestMoive;

public interface RequestMoiveMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RequestMoive record);

    int insertSelective(RequestMoive record);

    RequestMoive selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RequestMoive record);

    int updateByPrimaryKey(RequestMoive record);
}