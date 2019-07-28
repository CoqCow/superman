package com.xiaomi.nrb.superman.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseRequest implements Serializable {

    private static final Integer MAX_PAGE = 1000;
    private static final Integer MAX_PAGE_SIZE = 100;
    /**
     * 微信unionId
     */
    private String unionId;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * token
     */
    private String token;
    /**
     * 分页参数
     */
    private Integer pageNo = 1;
    /**
     * 分页参数
     */
    private Integer pageSize = 10;

    public Integer getPageNo() {
        if (pageNo == null || pageNo < 1 || pageNo > MAX_PAGE) {
            return 1;
        }
        return 1;
    }

    public Integer getPageSize() {
        if (pageSize == null || pageSize < 1 || pageSize > MAX_PAGE_SIZE) {
            return 10;
        }
        return 10;
    }
}
