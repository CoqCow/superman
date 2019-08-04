package com.xiaomi.nrb.superman.service.impl;

import com.xiaomi.nrb.superman.dao.PlanMapper;
import com.xiaomi.nrb.superman.domain.Plan;
import com.xiaomi.nrb.superman.service.PlanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author niuruobing@xiaomi.com
 * @since 2019-08-04 21:28
 **/
@Slf4j
@Service
public class PlanServiceImpl implements PlanService {

    @Resource
    private PlanMapper planMapper;

    @Override
    public Plan addPlan(Plan plan) {
        planMapper.insertSelective(plan);
        return planMapper.selectByPrimaryKey(plan.getId());
    }
}
