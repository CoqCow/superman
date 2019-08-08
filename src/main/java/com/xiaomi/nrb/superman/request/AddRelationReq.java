package com.xiaomi.nrb.superman.request;

import lombok.Data;

/**
 * @author niuruobing@xiaomi.com
 * @since 2019-08-08 21:49
 **/
@Data
public class AddRelationReq extends BaseRequest {
    /**
     * 关系类型
     */
    private Integer type;
}
