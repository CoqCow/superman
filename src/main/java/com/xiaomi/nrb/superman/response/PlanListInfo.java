package com.xiaomi.nrb.superman.response;

import lombok.Data;


/**
 * 计划列表实体
 *
 * @author niuruobing@xiaomi.com
 * @since 2019-08-05 11:10
 **/
@Data
public class PlanListInfo {
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
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 围观数量
     */
    private Integer seeNum;
    /**
     * 点赞数量
     */
    private Integer zanNum;
    /**
     * 挑战数量
     */
    private Integer challengeNum;
    /**
     * 创建时间
     */
    private String ctime;
    /**
     * 计划类型，1私密计划、2公开计划、3挑战计划
     */
    private Integer type;
    /**
     * 计划状态，1待发布、2未开始、3进行中、4已完成、5未完成
     */
    private Integer status;

    /**
     * 动态消息
     */
    private String message;
    /**
     * 微信昵称
     */
    private String nickName;
    /**
     * 微信头像
     */
    private String avartarUrl;
    /**
     * 性别 0未知、1男、2女
     */
    private Integer gender;
}
