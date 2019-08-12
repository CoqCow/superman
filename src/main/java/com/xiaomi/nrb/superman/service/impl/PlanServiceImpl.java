package com.xiaomi.nrb.superman.service.impl;

import com.xiaomi.nrb.superman.common.PageInfo;
import com.xiaomi.nrb.superman.dao.PlanMapper;
import com.xiaomi.nrb.superman.dao.RelationMapper;
import com.xiaomi.nrb.superman.dao.UserMapper;
import com.xiaomi.nrb.superman.dao.quary.ListPlanQuaryParam;
import com.xiaomi.nrb.superman.domain.Plan;
import com.xiaomi.nrb.superman.domain.Relation;
import com.xiaomi.nrb.superman.domain.User;
import com.xiaomi.nrb.superman.enums.PlanStatusEnum;
import com.xiaomi.nrb.superman.enums.PlanTypeEnum;
import com.xiaomi.nrb.superman.enums.RelationTypeEnum;
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
import java.util.Date;
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

    @Resource
    private UserMapper userMapper;

    @Resource
    private RelationMapper relationMapper;

    /**
     * 点赞数量要求
     */
    private static final int YOU_PLAN_ZAN_NUM = 1;
    /**
     * 围观数量要求
     */
    private static final int YOU_PLAN_SEE_NUM = 5;

    @Override
    public Plan addPlan(Plan plan) {
        planMapper.insertSelective(plan);
        return parsePlanStatus(planMapper.selectByPrimaryKey(plan.getId()));
    }

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");

    @Override
    public PageInfo<PlanListInfo> listPlan(ListPlanReq request) {
        parseUserPlanStatus(request.getUserId());
        ListPlanQuaryParam quaryParam = new ListPlanQuaryParam();
        quaryParam.setStartTime(request.getStartTime());
        quaryParam.setEndTime(request.getEndTime());
        quaryParam.setTypes(request.getTypes());
        quaryParam.setStatus(request.getStatus());
        quaryParam.setPageNo((request.getPageNo() - 1) * request.getPageSize());
        quaryParam.setPageSize(request.getPageSize());


        List<Plan> list = planMapper.listBySelective(quaryParam);
        int total = planMapper.countBySelective(quaryParam);
        if (CollectionUtils.isEmpty(list)) {
            return PageInfo.<PlanListInfo>builder().list(new ArrayList<>()).total(total).build();
        }
        List<PlanListInfo> listInfos = new ArrayList<>();

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
            Relation relation = new Relation();
            relation.setUserId(request.getUserId());
            relation.setPlanId(k.getId());
            List<Relation> relations = relationMapper.listBySelective(relation);
            if (relations == null) {
                relations = new ArrayList<>();
            }
            Integer seeNum = 0;
            Integer zanNum = 0;
            Integer challengeNum = 0;
            for (Relation relation1 : relations) {
                if (RelationTypeEnum.RELATION_SEE.getCode() == relation1.getType()) {
                    seeNum++;
                } else if (RelationTypeEnum.RELATION_UPVOTE.getCode() == relation1.getType()) {
                    zanNum++;
                } else if (RelationTypeEnum.RELATION_CHALLEGE.getCode() == relation1.getType()) {
                    challengeNum++;
                }
            }
            planListInfo.setSeeNum(seeNum);
            planListInfo.setZanNum(zanNum);
            planListInfo.setChallengeNum(challengeNum);

            //是否为挑战计划
            if (k.getType() == PlanTypeEnum.PLAN_CHALLENGE.getCode()) {
                String message = "";
                User user1 = userMapper.selectByPrimaryKey(k.getUserId());
                message += planListInfo.getNickName();
                message += " 在 ";
                message += planListInfo.getStartTime();
                message += "挑战了该计划";

                planListInfo.setMessage(message);
            }
            listInfos.add(planListInfo);
        });


        return PageInfo.<PlanListInfo>builder().list(listInfos).total(total).build();
    }

    @Override
    public PlanInfo detailPlan(BaseRequest request) {
        Plan plan = planMapper.selectByPrimaryKey(request.getPlanId());
        if (plan == null) return null;
        User user = userService.getUserByUserId(plan.getUserId());
        PlanInfo planInfo = new PlanInfo();
        BeanUtils.copyProperties(plan, planInfo);
        planInfo.setYouTag(isYouPlan(plan));
        planInfo.setCtimeForShow(simpleDateFormat.format(plan.getCtime()));
        planInfo.setNickName(user.getNickName());
        planInfo.setAvartarUrl(user.getAvartarUrl());
        planInfo.setGender(user.getGender());
        if (request.getUserId() == plan.getUserId()) {
            planInfo.setTag(true);
        } else {
            planInfo.setTag(false);
        }
        planInfo.setChallengeTag(false);
        planInfo.setZanTag(false);
        planInfo.setSeeTag(false);
        //围观、点赞、挑战
        Integer seeNum = 0;
        Integer zanNum = 0;
        Integer challengeNum = 0;
        List<Long> zanUserIds = new ArrayList<>();
        List<Long> challengeUserIds = new ArrayList<>();
        Relation relation = new Relation();
        relation.setUserId(request.getUserId());
        relation.setPlanId(request.getPlanId());
        List<Relation> relations = relationMapper.listBySelective(relation);
        if (relations == null) {
            relations = new ArrayList<>();
        }
        for (Relation k : relations) {
            if (RelationTypeEnum.RELATION_SEE.getCode() == k.getType()) {
                planInfo.setSeeTag(true);
                seeNum++;
                zanUserIds.add(k.getUserId());
            } else if (RelationTypeEnum.RELATION_UPVOTE.getCode() == k.getType()) {
                planInfo.setZanTag(true);
                zanNum++;
            } else if (RelationTypeEnum.RELATION_CHALLEGE.getCode() == k.getType()) {
                planInfo.setChallengeTag(true);
                challengeUserIds.add(k.getUserId());
                challengeNum++;
            }
        }
        planInfo.setSeeNum(seeNum);
        planInfo.setZanNum(zanNum);
        planInfo.setChallengeNum(challengeNum);
        if (!CollectionUtils.isEmpty(zanUserIds)) {
            List<String> strings = userMapper.selectAvartarUrls(zanUserIds);
            planInfo.setZanAvartarUrls(strings);
        }
        if (!CollectionUtils.isEmpty(challengeUserIds)) {
            List<String> strings = userMapper.selectAvartarUrls(challengeUserIds);
            planInfo.setChallengeAvartarUrls(strings);
        }
        return planInfo;
    }

    @Override
    public PlanInfo donePlan(BaseRequest request) {
        Plan plan = planMapper.selectByPrimaryKey(request.getPlanId());
        if (null != plan && PlanStatusEnum.ONGOING.getCode() == plan.getStatus()) {
            plan = new Plan();
            plan.setId(request.getPlanId());
            plan.setStatus(PlanStatusEnum.COMPLETE.getCode());
            plan.setUtime(new Date());
            planMapper.updateByPrimaryKeySelective(plan);
        }

        return detailPlan(request);
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


    /**
     * 实时计算用户所有计划状态
     *
     * @author niuruobing@xiaomi.com
     * @since 2019-08-11 17:04
     */
    private void parseUserPlanStatus(Long userId) {
        if (null == userId) {
            return;
        }
        ListPlanQuaryParam quaryParam = new ListPlanQuaryParam();
        quaryParam.setUserId(userId);
        List<Plan> list = planMapper.listBySelective(quaryParam);
        list.forEach(k -> {
            parsePlanStatus(k);
        });
    }

    /**
     * 判断是否为you计划
     *
     * @author niuruobing@xiaomi.com
     * @since 2019-08-12 11:03
     */
    public boolean isYouPlan(Plan plan) {
        //私密计划
        if (plan.getType() == PlanTypeEnum.PLAN_PRIVITE.getCode()) {
            return false;
        }
        Relation relation = new Relation();
        relation.setPlanId(plan.getId());
        //1、计划已完成、点赞数量满足
        if (plan.getStatus() == PlanStatusEnum.COMPLETE.getCode()) {
            relation.setType(RelationTypeEnum.RELATION_UPVOTE.getCode());
            int zanNum = relationMapper.countBySelective(relation);
            if (zanNum >= YOU_PLAN_ZAN_NUM) {
                return true;
            }
        }
        //2、计划进行中、围观数量满足
        if (plan.getStatus() == PlanStatusEnum.ONGOING.getCode()) {
            relation.setType(RelationTypeEnum.RELATION_SEE.getCode());
            int seeNum = relationMapper.countBySelective(relation);
            if (seeNum >= YOU_PLAN_SEE_NUM) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean challengePlan(BaseRequest request) {


        Plan plan = planMapper.selectByPrimaryKey(request.getPlanId());
        if (null == plan) {
            return false;
        }
        Relation oldRelation = new Relation();
        oldRelation.setUserId(request.getUserId());
        oldRelation.setPlanId(request.getPlanId());
        oldRelation.setPlanUserId(plan.getUserId());
        oldRelation.setType(RelationTypeEnum.RELATION_CHALLEGE.getCode());
        int count = relationMapper.countBySelective(oldRelation);
        //已经挑战过
        if (count != 0) {
            return false;
        }
        //挑战计划 复制要挑战的计划
        Long startTime = plan.getStartTime().getTime();
        Long endTime = plan.getEndTime().getTime();
        Long curTime = System.currentTimeMillis();
        Plan newPlan = new Plan();

        newPlan.setUserId(request.getUserId());
        newPlan.setTitle(plan.getTitle());
        newPlan.setContent(plan.getContent());

        newPlan.setStartTime(new Date(curTime));
        newPlan.setEndTime(new Date(curTime + (endTime - startTime)));

        newPlan.setType(PlanTypeEnum.PLAN_CHALLENGE.getCode());
        newPlan.setStatus(PlanStatusEnum.ONGOING.getCode());
        newPlan.setCtime(new Date());
        newPlan.setUtime(new Date());
        planMapper.insertSelective(newPlan);
        //记录挑战日志
        Relation relation = new Relation();
        relation.setUserId(request.getUserId());
        relation.setPlanId(request.getPlanId());
        relation.setPlanUserId(plan.getUserId());
        relation.setType(RelationTypeEnum.RELATION_CHALLEGE.getCode());
        relation.setCtime(new Date(curTime));
        relation.setUtime(new Date(curTime));
        relationMapper.insertSelective(relation);

        return true;
    }
}
