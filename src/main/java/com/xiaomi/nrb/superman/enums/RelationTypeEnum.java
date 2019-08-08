package com.xiaomi.nrb.superman.enums;


/**
 * @author niuruobing@xiaomi.com
 * @since 2019-08-04 09:14
 **/
public enum RelationTypeEnum {

    RELATION_SEE(1, "围观计划"),
    RELATION_UPVOTE(2, "点赞计划"),
    RELATION_CHALLEGE(3, "挑战计划");


    private int code;
    private String desc;

    RelationTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }
}
