package com.pcms.dao;

import com.pcms.domain.MoiveFail;

public interface MoiveFailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MoiveFail record);

    int insertSelective(MoiveFail record);

    MoiveFail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MoiveFail record);

    int updateByPrimaryKey(MoiveFail record);
}