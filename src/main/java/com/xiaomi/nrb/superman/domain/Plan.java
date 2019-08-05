package com.xiaomi.nrb.superman.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Plan implements Serializable {
    /**
     * 计划id
     */
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
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
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 计划类型，1私密计划、2公开计划、3挑战计划
     */
    private Integer type;
    /**
     * 计划状态，1待发布、2未开始、3进行中、4已完成、5未完成
     */
    private Integer status;
    /**
     * 照片1
     */
    private String processFirstImg;
    /**
     * 照片2
     */
    private String processSecondImg;
    /**
     * 照片3
     */
    private String processThirdImg;
    /**
     * 创建时间
     */
    private Date ctime;
    /**
     * 修改时间
     */
    private Date utime;
}