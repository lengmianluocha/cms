package com.pcms.dao;

import com.pcms.domain.RequestMoive;

import java.util.List;
import java.util.Map;

public interface RequestMoiveMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RequestMoive record);

    int insertSelective(RequestMoive record);

    RequestMoive selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RequestMoive record);

    int getMoiveRequestCountByParam(Map param);

    List<RequestMoive> SearchMoiveRequestByParam(Map param);

    RequestMoive getRequestMoiveByParam(Map param);


}