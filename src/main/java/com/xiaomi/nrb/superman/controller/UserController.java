package com.xiaomi.nrb.superman.controller;

import com.xiaomi.nrb.superman.annotation.CheckLogin;
import com.xiaomi.nrb.superman.common.ApiEnum;
import com.xiaomi.nrb.superman.common.Result;
import com.xiaomi.nrb.superman.domain.User;
import com.xiaomi.nrb.superman.enums.UseStatusEnum;
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
import java.util.Date;


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

        try {
            String openId = null;
            //获取openId
            if (StringUtils.isBlank(request.getToken()) || !tokenService.validateToken(request.getToken())) {
                openId = userService.getOpneid(request.getCode());

            } else {
                openId = tokenService.getParam(request.getToken(), "openId");
            }

            //获取openId失败
            if (StringUtils.isBlank(openId)) {
                return Result.fail(ApiEnum.USER_CODE_EXPIRED.getCode());
            }


            User user = userService.getUserByOpenId(openId);


            //已注册用户
            if (null != user && user.getStatus() != UseStatusEnum.NOT_REGISTER.getCode()) {
                String token = tokenService.createToken(user);
                UserInfoRes userInfoRes = UserInfoRes.builder().user(user).token(token).build();
                return Result.ok(userInfoRes);
            }


            //未注册用户
            User notRegisterUser = null;
            if (null == user) {
                user = new User();
                user.setOpenId(openId);
                user.setStatus(UseStatusEnum.NOT_REGISTER.getCode());
                user.setCtime(new Date());
                notRegisterUser = userService.insertNotRegisterUser(user);

            } else {
                notRegisterUser = userService.getUserByOpenId(openId);
            }
            String token = tokenService.createToken(notRegisterUser);

            return Result.error(ApiEnum.USER_NOT_REGISTER.getCode(), token);
        } catch (Exception e) {
            log.error("UserController.getUserInfo.error:", e);
            return Result.fail(ApiEnum.ERROR.getCode());
        }

    }


    @RequestMapping("/register")
    @CheckLogin
    public Result register(@RequestBody RegisterReq request) {
        if (StringUtils.isBlank(request.getNickName()) || StringUtils.isBlank(request.getAvatarUrl())) {
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
