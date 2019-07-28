package com.xiaomi.nrb.superman.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @author niuruobing@xiaomi.com
 * @since 2019-07-27 21:42
 **/
@Data
public class Result<T> implements Serializable {
    private int code;
    private String msg;
    private T data;

    public static Result error(int code, String message) {
        Result resultVo = new Result();
        resultVo.setCode(code);
        resultVo.setMsg(message);
        return resultVo;
    }

    public static Result fail(int code) {
        Result resultVo = new Result();
        resultVo.setCode(code);
        resultVo.setMsg(ApiEnum.getValueByCode(code));
        return resultVo;
    }

    public static Result ok(Object data) {
        Result resultVo = new Result();
        resultVo.setCode(ApiEnum.SUCCESS.getCode());
        resultVo.setMsg(ApiEnum.SUCCESS.getValue());
        resultVo.setData(data);
        return resultVo;
    }

    public static Result error(String message) {
        return error(ApiEnum.ERROR.getCode(), message);
    }
}
