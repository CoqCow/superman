package com.xiaomi.nrb.superman.dao;

import com.xiaomi.nrb.superman.domain.Plan;

public interface PlanMapper {

    int insertSelective(Plan record);

    Plan selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Plan record);

}