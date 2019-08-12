package com.xiaomi.nrb.superman.service;

import com.xiaomi.nrb.superman.common.PageInfo;
import com.xiaomi.nrb.superman.domain.Plan;
import com.xiaomi.nrb.superman.request.BaseRequest;
import com.xiaomi.nrb.superman.request.ListPlanReq;
import com.xiaomi.nrb.superman.response.PlanInfo;
import com.xiaomi.nrb.superman.response.PlanListInfo;


/**
 * @author niuruobing@xiaomi.com
 * @since 2019-08-04 21:27
 **/
public interface PlanService {

    /**
     * 添加计划
     *
     * @author niuruobing@xiaomi.com
     * @since 2019-08-04 21:27
     */
    Plan addPlan(Plan plan);


    /**
     * 计划列表
     *
     * @author niuruobing@xiaomi.com
     * @since 2019-08-05 10:19
     */
    PageInfo<PlanListInfo> listPlan(ListPlanReq request);

    /**
     * 计划详情
     *
     * @author niuruobing@xiaomi.com
     * @since 2019-08-05 22:29
     */
    PlanInfo detailPlan(BaseRequest request);

    /**
     * 完成计划
     *
     * @author niuruobing@xiaomi.com
     * @since 2019-08-11 16:56
     */
    PlanInfo donePlan(BaseRequest request);

    /**
     * 判断是否为YOU计划
     *
     * @author niuruobing@xiaomi.com
     * @since 2019-08-12 11:37
     */
    boolean isYouPlan(Plan plan);

    /**
     * 挑战计划
     *
     * @author niuruobing@xiaomi.com
     * @since 2019-08-12 14:12
     */
    boolean challengePlan(BaseRequest request);
}
