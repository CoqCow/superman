package com.xiaomi.nrb.superman.service.impl;

import com.xiaomi.nrb.superman.dao.PlanMapper;
import com.xiaomi.nrb.superman.dao.RelationMapper;
import com.xiaomi.nrb.superman.domain.Plan;
import com.xiaomi.nrb.superman.domain.Relation;
import com.xiaomi.nrb.superman.enums.PlanTypeEnum;
import com.xiaomi.nrb.superman.enums.RelationTypeEnum;
import com.xiaomi.nrb.superman.request.AddRelationReq;
import com.xiaomi.nrb.superman.response.PlanInfo;
import com.xiaomi.nrb.superman.service.PlanService;
import com.xiaomi.nrb.superman.service.RelationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author niuruobing@xiaomi.com
 * @since 2019-08-08 08:05
 **/
@Service
public class RelationServiceImpl implements RelationService {

    @Resource
    private PlanService planService;

    @Resource
    private RelationMapper relationMapper;

    @Resource
    private PlanMapper planMapper;

    @Override
    public PlanInfo addRelation(AddRelationReq request) {
        PlanInfo planInfo = planService.detailPlan(request);
        if (planInfo == null) {
            return planInfo;
        }
        //自己可以围观、点赞自己对计划  不可以挑战自己对计划
        Integer type = null;

        if (RelationTypeEnum.RELATION_SEE.getCode() == request.getType() && !planInfo.isSeeTag()) {
            type = RelationTypeEnum.RELATION_SEE.getCode();
        } else if (RelationTypeEnum.RELATION_UPVOTE.getCode() == request.getType() && !planInfo.isZanTag()) {
            type = RelationTypeEnum.RELATION_UPVOTE.getCode();
        } else if (RelationTypeEnum.RELATION_CHALLEGE.getCode() == request.getType() && !planInfo.isTag()) {
            type = RelationTypeEnum.RELATION_CHALLEGE.getCode();
        }

        if (null == type) {
            return planInfo;
        }
        Relation relation = new Relation();
        relation.setUserId(request.getUserId());
        relation.setPlanId(request.getPlanId());
        relation.setPlanUserId(planInfo.getUserId());
        relation.setType(type);
        relation.setCtime(new Date());
        relationMapper.insertSelective(relation);

        //是否更新为YOU计划
        if (planInfo.getType() != PlanTypeEnum.PLAN_YOU.getCode() && planService.isYouPlan(planInfo)) {
            planInfo.setYouTag(true);
            Plan plan = new Plan();
            plan.setId(planInfo.getId());
            plan.setType(PlanTypeEnum.PLAN_YOU.getCode());
            planMapper.updateByPrimaryKeySelective(plan);

        }


        return  planService.detailPlan(request);
    }
}
