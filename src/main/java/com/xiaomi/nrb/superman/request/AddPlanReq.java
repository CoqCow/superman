package com.xiaomi.nrb.superman.request;

import lombok.Data;

import java.util.Date;

/**
 * @author niuruobing@xiaomi.com
 * @since 2019-08-04 21:25
 **/
@Data
public class AddPlanReq extends BaseRequest {
    /**
     * 计划标题
     */
    private String title;
    /**
     * 计划内容
     */
    private String content;
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
    private Integer status;
}
