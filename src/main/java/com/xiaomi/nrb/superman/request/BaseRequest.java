package com.xiaomi.nrb.superman.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseRequest implements Serializable {

    private static final Integer MAX_PAGE = 1000;
    private static final Integer MAX_PAGE_SIZE = 100;
    /**
     * 微信code
     */
    private String code;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 计划id
     */
    private Long planId;
    /**
     * 微信openId
     */
    private String openId;
    /**
     * token
     */
    private String token;
    /**
     * 分页参数
     */
    private Integer pageNo;
    /**
     * 分页参数
     */
    private Integer pageSize;

    public Integer getPageNo() {
        if (pageNo == null || pageNo < 1 || pageNo > MAX_PAGE) {
            return 1;
        }
        return pageNo;
    }

    public Integer getPageSize() {
        if (pageSize == null || pageSize < 1 || pageSize > MAX_PAGE_SIZE) {
            return 10;
        }
        return pageSize;
    }
}
