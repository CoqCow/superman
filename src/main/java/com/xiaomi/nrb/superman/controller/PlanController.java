package com.xiaomi.nrb.superman.controller;

import com.xiaomi.nrb.superman.annotation.CheckLogin;
import com.xiaomi.nrb.superman.common.ApiEnum;
import com.xiaomi.nrb.superman.common.Result;
import com.xiaomi.nrb.superman.domain.Plan;
import com.xiaomi.nrb.superman.enums.PlanStatusEnum;
import com.xiaomi.nrb.superman.request.AddPlanReq;
import com.xiaomi.nrb.superman.request.RegisterReq;
import com.xiaomi.nrb.superman.service.PlanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author niuruobing@xiaomi.com
 * @since 2019-07-28 08:29
 **/
@Slf4j
@RestController
@RequestMapping("/superman/plan/apple")
public class PlanController {

    @Resource
    private PlanService planService;

    @RequestMapping("/addPlan")
    @CheckLogin
    public Result addPlan(@RequestBody AddPlanReq request) {

        try {
            Plan plan = new Plan();
            plan.setTitle(request.getTitle());
            plan.setContent(request.getContent());
            plan.setStartTime(new Date(request.getStartTime()));
            plan.setEndTime(new Date(request.getEndTime()));
            plan.setUserId(request.getUserId());
            plan.setCtime(new Date());
            plan.setType(request.getType());
            //状态不对 暂时不写
            plan.setStatus(PlanStatusEnum.BEGIN.getCode());
            return Result.ok(planService.addPlan(plan));
        } catch (Exception e) {
            log.error("PlanController.addPlan.error:", e);
            return Result.fail(ApiEnum.ERROR.getCode());
        }

    }

    @RequestMapping("/listPlan")
    @CheckLogin
    public Result listPlan(@RequestBody RegisterReq request) {


        return Result.ok(null);
    }
}
