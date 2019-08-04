package com.xiaomi.nrb.superman.enums;


/**
 * @author niuruobing@xiaomi.com
 * @since 2019-08-04 09:14
 **/
public enum UseStatusEnum {

    NOT_REGISTER(0, "未注册"),
    NEW_REGISTER(1, "新用户");

    private int code;
    private String desc;

    UseStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }
}
