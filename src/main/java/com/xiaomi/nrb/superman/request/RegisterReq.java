package com.xiaomi.nrb.superman.request;

import lombok.Data;

/**
 * 注册请求实体
 *
 * @author niuruobing@xiaomi.com
 * @since 2019-08-02 14:17
 **/
@Data
public class RegisterReq extends BaseRequest {
    /**
     * 微信昵称
     */
    private String nickName;
    /**
     * 微信头像
     */
    private String avatarUrl;
    /**
     * 性别 0未知、1男、2女
     */
    private Integer gender;
    /**
     * 国家
     */
    private String country;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
}
