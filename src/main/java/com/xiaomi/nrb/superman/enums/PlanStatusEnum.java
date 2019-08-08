package com.xiaomi.nrb.superman.enums;


/**
 * @author niuruobing@xiaomi.com
 * @since 2019-08-04 09:14
 **/
public enum PlanStatusEnum {

    //计划状态，1待发布、2未开始、3进行中、4已完成、5未完成
    NOT_RELEASED(1, "待发布"),
    NOT_BEGIN(2, "未开始"),
    ONGOING(3, "进行中"),
    COMPLETE(4, "已完成"),
    NOT_COMPLETE(5, "未完成");

    private int code;
    private String desc;

    PlanStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }
}
