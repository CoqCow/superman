package com.xiaomi.nrb.superman.controller;

import com.xiaomi.nrb.superman.common.ApiEnum;
import com.xiaomi.nrb.superman.common.Result;
import com.xiaomi.nrb.superman.domain.User;
import com.xiaomi.nrb.superman.request.BaseRequest;
import com.xiaomi.nrb.superman.request.RegisterReq;
import com.xiaomi.nrb.superman.response.UserInfoRes;
import com.xiaomi.nrb.superman.service.TokenService;
import com.xiaomi.nrb.superman.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * de
 *
 * @author niuruobing@xiaomi.com
 * @since 2019-07-27 21:42
 **/
@RestController
@RequestMapping("/superman/user/green")
@Slf4j
public class UserController {

    @Resource
    private TokenService tokenService;

    @Resource
    private UserService userService;

    @RequestMapping("/getUserInfo")
    public Result getUserInfo(@RequestBody BaseRequest request) {
        //1、code 机制 微信登陆态
        if (StringUtils.isBlank(request.getToken()) || !tokenService.validateToken(request.getToken())) {
            String openId = userService.getOpneid(request.getCode());

            User user = null;
            if (StringUtils.isBlank(openId)) {
                return Result.fail(ApiEnum.USER_CODE_EXPIRED.getCode());
            } else {
                user = userService.getUserByOpenId(openId);
            }
            //根据openId获取用户
            if (null == user) {
                return Result.fail(ApiEnum.USER_NOT_REGISTER.getCode());
            } else {
                //生成新token
                String token = tokenService.createToken(user);
                UserInfoRes userInfoRes = UserInfoRes.builder().user(user).token(token).build();
                return Result.ok(userInfoRes);
            }

        }

        //2、token 机制 自己的登陆态

        String id = tokenService.getParam(request.getToken(), "id");
        if (StringUtils.isBlank(id)) {
            return Result.fail(ApiEnum.USER_LOGIN_ERROR.getCode());
        }
        User user = userService.getUserByUserId(Long.parseLong(id));

        if (null == user) {
            return Result.fail(ApiEnum.USER_NOT_REGISTER.getCode());
        } else {
            UserInfoRes userInfoRes = UserInfoRes.builder().user(user).token(request.getToken()).build();
            return Result.ok(userInfoRes);
        }
    }


    @RequestMapping("/register")
    public Result register(@RequestBody RegisterReq request) {
        if (StringUtils.isBlank(request.getNickName()) || StringUtils.isBlank(request.getAvartarUrl())) {
            return Result.fail(ApiEnum.PARAM_INVALID.getCode());
        }
        try {
            return userService.registerUser(request);
        } catch (Exception e) {
            log.error("UserController.register.error:", e);
            return Result.fail(ApiEnum.ERROR.getCode());
        }

    }


}
