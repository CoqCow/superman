package com.xiaomi.nrb.superman.service;

import com.xiaomi.nrb.superman.common.PageInfo;
import com.xiaomi.nrb.superman.domain.Plan;
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
    PageInfo<PlanListInfo> listPlan(Plan plan);

}
