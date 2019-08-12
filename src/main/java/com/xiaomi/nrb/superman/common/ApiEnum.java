package com.xiaomi.nrb.superman.common;

/**
 * @program: union-jingtiao
 * @author: niuruobing
 * @create: 2019-02-22 10:36
 **/
public enum ApiEnum {
    //成功
    SUCCESS(200, "success"),
    PARAM_INVALID(400, "参数错误"),
    TOKEN_INVALID(401, "token不合法"),
    USER_LOGIN_ERROR(412, "用户登录态丢失"),
    LONG_OUT(420, "未登录"),
    ERROR(500, "服务端异常"),


    //用户code
    USER_NOT_REGISTER(1000, "用户没有注册"),
    USER_CODE_EXPIRED(1001, "微信code过期"),
    USER_EXIST(1002, "用户已经注册"),
    USER_REGISTER_ERROR(1003, "用户注册失败"),


    //计划
    PLAN_CHALLENGE_FIAL(2000, "挑战计划失败");

    private int code;
    private String value;

    ApiEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }


    public int getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static String getValueByCode(Integer code) {

        for (ApiEnum apiEnum : ApiEnum.values()) {
            if (apiEnum.getCode() == code) {
                return apiEnum.getValue();
            }
        }

        return null;

    }
}
