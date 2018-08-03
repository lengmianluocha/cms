package com.pcms.dao;

import com.pcms.domain.Moive;

import java.util.List;
import java.util.Map;

public interface MoiveMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Moive record);

    int insertSelective(Moive record);

    Moive selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Moive record);

    int updateByPrimaryKey(Moive record);

    List<Moive> searchMoiveByParam(Map param);
}