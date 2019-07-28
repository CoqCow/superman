package com.xiaomi.nrb.superman.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class User implements Serializable {
    /**
     * 用户id
     */
    private Long id;
    /**
     * 微信openid
     */
    private String openId;
    /**
     * 微信unionId
     */
    private String unionId;
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
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 用户等级，默认1
     */
    private Integer grade;
    /**
     * 用户状态，默认1
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