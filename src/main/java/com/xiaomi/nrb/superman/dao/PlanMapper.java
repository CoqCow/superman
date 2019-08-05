package com.xiaomi.nrb.superman.dao;

import com.xiaomi.nrb.superman.dao.quary.ListPlanQuaryParam;
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

    List<Plan> listBySelective(ListPlanQuaryParam quaryParam);

    int countBySelective(ListPlanQuaryParam quaryParam);

}