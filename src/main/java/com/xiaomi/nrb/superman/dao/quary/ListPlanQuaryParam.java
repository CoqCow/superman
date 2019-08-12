package com.xiaomi.nrb.superman.dao.quary;

import com.xiaomi.nrb.superman.domain.BaseQuaryParam;
import lombok.Data;

import java.util.List;

/**
 * @author niuruobing@xiaomi.com
 * @since 2019-08-05 15:16
 **/
@Data
public class ListPlanQuaryParam extends BaseQuaryParam {
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 开始时间
     */
    private Long startTime;
    /**
     * 结束时间
     */
    private Long endTime;
    /**
     * 计划类型，1私密计划、2公开计划、3挑战计划
     */
    private Integer type;
    /**
     * 计划状态，1待发布、2未开始、3进行中、4已完成、5未完成
     */
    private List<Integer> status;
}
