
package com.xiaomi.nrb.superman.aop;


import com.xiaomi.nrb.superman.annotation.CheckLogin;
import com.xiaomi.nrb.superman.common.ApiEnum;
import com.xiaomi.nrb.superman.common.Result;
import com.xiaomi.nrb.superman.request.BaseRequest;
import com.xiaomi.nrb.superman.service.TokenService;
import com.xiaomi.nrb.superman.service.impl.TokenServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;


@Aspect
@Component
public class LoginAspect {


    @Resource
    private TokenService tokenService;

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void annotationPointCut() {
    }

    @Around("annotationPointCut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        return exe(joinPoint, joinPoint.getArgs());
    }

    private Object exe(ProceedingJoinPoint joinPoint, Object[] args) throws Throwable {
        Object result = null;
        try {
            //拦截实体类
            Class targetClass = joinPoint.getTarget().getClass();
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            Method method = methodSignature.getMethod();
            //获取拦截的方法名称、请求参数
            String methodName = methodSignature.getName();
            //访问路径不存在
            if ("errorHtml".equals(methodName)) {
                return joinPoint.proceed();
            }
            // 拦截处理逻辑，打印出入参数
            if (args == null || args.length == 0) {
                return Result.error("入参为空");
            }
            if (!(args[0] instanceof BaseRequest)) {
                return Result.error("请求参数格式错误呢");
            }
            BaseRequest request = (BaseRequest) args[0];
            //方法添加@CheckLogin注解，检验登录信息
            CheckLogin checkLogin = method.getAnnotation(CheckLogin.class);
            if (null != checkLogin && checkLogin.login() == true) {

                if (StringUtils.isBlank(request.getToken()) || !tokenService.validateToken(request.getToken())) {
                    return Result.fail(ApiEnum.USER_LOGIN_ERROR.getCode());
                }
                String id = tokenService.getParam(request.getToken(), "id");
                if (StringUtils.isBlank(id)) {
                    return Result.fail(ApiEnum.USER_LOGIN_ERROR.getCode());
                }
                request.setUserId(Long.parseLong(id));
            }
            //真实方法试行
            result = joinPoint.proceed();
        } catch (Exception e) {
            result = Result.error("服务端error");
        }
        return result;
    }
}

