package com.xiaomi.nrb.superman.request;

import lombok.Data;

import java.util.List;

/**
 * @author niuruobing@xiaomi.com
 * @since 2019-08-05 10:13
 **/
@Data
public class ListPlanReq extends BaseRequest {
    /**
     * 开始时间
     */
    private Long startTime;
    /**
     * 结束时间
     */
    private Long endTime;
    /**
     * 计划类型，1私密计划、2公开计划、3挑战计划、4YOU计划
     */
    private List<Integer> types;
    /**
     * 计划状态，1待发布、2未开始、3进行中、4已完成、5未完成
     */
    private List<Integer> status;
    /**
     * 页面来源
     */
    private String source;
    /**
     * 点赞标记
     */
    private String upvote;
}
