package com.xiaomi.nrb.superman.domain;

import lombok.Data;

/**
 * @author niuruobing@xiaomi.com
 * @since 2019-08-05 10:22
 **/
@Data
public class BaseQuaryParam {
    /**
     * 分页参数
     */
    private Integer pageNo;
    /**
     * 分页参数
     */
    private Integer pageSize;
}
