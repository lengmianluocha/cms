package com.pcms.dao;

import com.pcms.domain.MoiveFail;

import java.util.List;
import java.util.Map;

public interface MoiveFailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MoiveFail record);

    int insertSelective(MoiveFail record);

    MoiveFail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MoiveFail record);

    int updateByPrimaryKey(MoiveFail record);

    int getMoiveFailCountByParam(Map param);

    List<MoiveFail> searchMoiveFailByParam(Map param);

    MoiveFail getMoiveFailByParam(Map param);
}