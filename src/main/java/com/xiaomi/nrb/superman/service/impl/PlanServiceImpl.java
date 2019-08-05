package com.xiaomi.nrb.superman.service.impl;

import com.xiaomi.nrb.superman.common.PageInfo;
import com.xiaomi.nrb.superman.dao.PlanMapper;
import com.xiaomi.nrb.superman.domain.Plan;
import com.xiaomi.nrb.superman.domain.User;
import com.xiaomi.nrb.superman.response.PlanListInfo;
import com.xiaomi.nrb.superman.service.PlanService;
import com.xiaomi.nrb.superman.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author niuruobing@xiaomi.com
 * @since 2019-08-04 21:28
 **/
@Slf4j
@Service
public class PlanServiceImpl implements PlanService {

    @Resource
    private PlanMapper planMapper;

    @Resource
    private UserService userService;

    @Override
    public Plan addPlan(Plan plan) {
        planMapper.insertSelective(plan);
        return planMapper.selectByPrimaryKey(plan.getId());
    }

    @Override
    public PageInfo<PlanListInfo> listPlan(Plan plan) {

        List<Plan> list = planMapper.listBySelective(plan);
        int total = planMapper.countBySelective(plan);
        if (CollectionUtils.isEmpty(list)) {
            return PageInfo.<PlanListInfo>builder().list(new ArrayList<>()).total(total).build();
        }
        List<PlanListInfo> listInfos = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        list.forEach(k -> {
            User user = userService.getUserByUserId(k.getUserId());
            PlanListInfo planListInfo = new PlanListInfo();
            planListInfo.setId(k.getId());
            planListInfo.setUserId(k.getUserId());
            planListInfo.setTitle(k.getTitle());
            planListInfo.setContent(k.getContent());
            planListInfo.setStartTime(simpleDateFormat.format(k.getStartTime()));
            planListInfo.setEndTime(simpleDateFormat.format(k.getEndTime()));
            planListInfo.setCtime(simpleDateFormat.format(k.getCtime()));
            planListInfo.setType(k.getType());
            planListInfo.setStatus(k.getStatus());
            planListInfo.setNickName(user.getNickName());
            planListInfo.setAvartarUrl(user.getAvartarUrl());
            planListInfo.setGender(user.getGender());
            listInfos.add(planListInfo);
        });


        return PageInfo.<PlanListInfo>builder().list(listInfos).total(total).build();
    }
}
