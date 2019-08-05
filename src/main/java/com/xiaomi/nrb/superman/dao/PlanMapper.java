package com.xiaomi.nrb.superman.dao;

import com.xiaomi.nrb.superman.domain.Plan;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface PlanMapper {

    int insertSelective(Plan record);

    Plan selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Plan record);

    List<Plan> listBySelective(Plan plan);

    int countBySelective(Plan plan);

}