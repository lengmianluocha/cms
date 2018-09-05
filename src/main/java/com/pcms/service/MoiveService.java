package com.pcms.service;

import com.pcms.domain.Moive;

import java.util.List;
import java.util.Map;

public interface MoiveService {

    int insert(Moive record);

    List<Moive> searchMoiveByParam(Map param);

    List<Moive> searchMoiveForWXByParam(Map param);

    public int getMoiveCountByParam(Map map);

    Moive getMoiveByParam(Map param);

    int updateByPrimaryKeySelective(Moive record);

    int updateByMoiveName(Moive moive);

    int insertSelective(Moive record);

    Moive selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);


}
