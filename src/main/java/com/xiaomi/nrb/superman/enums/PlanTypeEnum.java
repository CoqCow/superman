package com.xiaomi.nrb.superman.enums;


/**
 * @author niuruobing@xiaomi.com
 * @since 2019-08-04 09:14
 **/
public enum PlanTypeEnum {

    PLAN_PRIVITE(1, "私密计划"),
    PLAN_PUBLIC(2, "公开计划"),
    PLAN_CHALLENGE(3, "挑战计划"),
    PLAN_YOU(4, "YOU计划");

    private int code;
    private String desc;

    PlanTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }
}
