package com.xiaomi.nrb.superman.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckLogin {
    /**
     * 登陆校验注解 ture 强校验  false 、不加注解不校验
     */
    boolean login() default true;
}
