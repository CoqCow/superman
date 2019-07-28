package com.xiaomi.nrb.superman.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Relation implements Serializable {
    /**
     * 关系id
     */
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 计划id
     */
    private Long planId;
    /**
     * 发布计划者的用户id
     */
    private Long planUserId;
    /**
     * 关系类型，1围观、2点赞、3挑战
     */
    private Integer type;
    /**
     * 关系状态，0无效、1有效
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date ctime;
    /**
     * 修改时间
     */
    private Date utime;
}