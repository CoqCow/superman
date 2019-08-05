package com.xiaomi.nrb.superman.service.impl;

import com.xiaomi.nrb.superman.common.PageInfo;
import com.xiaomi.nrb.superman.dao.PlanMapper;
import com.xiaomi.nrb.superman.dao.quary.ListPlanQuaryParam;
import com.xiaomi.nrb.superman.domain.Plan;
import com.xiaomi.nrb.superman.domain.User;
import com.xiaomi.nrb.superman.dao.quary.PlanStatusEnum;
import com.xiaomi.nrb.superman.request.BaseRequest;
import com.xiaomi.nrb.superman.request.ListPlanReq;
import com.xiaomi.nrb.superman.response.PlanInfo;
import com.xiaomi.nrb.superman.response.PlanListInfo;
import com.xiaomi.nrb.superman.service.PlanService;
import com.xiaomi.nrb.superman.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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
        return parsePlanStatus(planMapper.selectByPrimaryKey(plan.getId()));
    }

    @Override
    public PageInfo<PlanListInfo> listPlan(ListPlanReq request) {

        ListPlanQuaryParam quaryParam = new ListPlanQuaryParam();
        quaryParam.setStartTime(request.getStartTime());
        quaryParam.setEndTime(request.getEndTime());
        quaryParam.setType(request.getType());
        quaryParam.setStatus(request.getStatus());
        quaryParam.setPageNo((request.getPageNo() - 1) * request.getPageSize());
        quaryParam.setPageSize(request.getPageSize());


        List<Plan> list = planMapper.listBySelective(quaryParam);
        int total = planMapper.countBySelective(quaryParam);
        if (CollectionUtils.isEmpty(list)) {
            return PageInfo.<PlanListInfo>builder().list(new ArrayList<>()).total(total).build();
        }
        List<PlanListInfo> listInfos = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        list.forEach(k -> {
            parsePlanStatus(k);
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

    @Override
    public PlanInfo detailPlan(BaseRequest request) {
        Plan plan = planMapper.selectByPrimaryKey(request.getPlanId());
        if (plan == null) return null;
        User user=userService.getUserByUserId(plan.getUserId());
        PlanInfo planInfo = new PlanInfo();
        BeanUtils.copyProperties(plan, planInfo);
        planInfo.setNickName(user.getNickName());
        planInfo.setAvartarUrl(user.getAvartarUrl());
        planInfo.setGender(user.getGender());
        if (request.getUserId() == plan.getUserId()) {
            planInfo.setTag(true);
        } else {
            planInfo.setTag(false);
        }
        return planInfo;
    }

    /**
     * 实时计算计划的完成状态
     *
     * @author niuruobing@xiaomi.com
     * @since 2019-08-05 14:07
     */
    private Plan parsePlanStatus(Plan plan) {
        //待发布计划、已完成计划、未完成计划
        if (null == plan || PlanStatusEnum.NOT_RELEASED.getCode() == plan.getStatus() || PlanStatusEnum.COMPLETE.getCode() == plan.getStatus() ||
                PlanStatusEnum.NOT_COMPLETE.getCode() == plan.getStatus()) {
            return plan;
        }
        long curTime = System.currentTimeMillis();
        long beginTime = plan.getStartTime().getTime();
        long endTime = plan.getEndTime().getTime();

        Integer status = null;
        if (curTime < beginTime) {
            status = PlanStatusEnum.NOT_BEGIN.getCode();
        } else if (curTime <= endTime) {
            status = PlanStatusEnum.ONGOING.getCode();
        } else {
            status = PlanStatusEnum.NOT_COMPLETE.getCode();
        }

        if (status != plan.getStatus()) {
            plan.setStatus(status);
            //更新数据库
            Plan updatePlan = new Plan();
            updatePlan.setId(plan.getId());
            updatePlan.setStatus(status);
            planMapper.updateByPrimaryKeySelective(updatePlan);
        }

        return plan;

    }
}
