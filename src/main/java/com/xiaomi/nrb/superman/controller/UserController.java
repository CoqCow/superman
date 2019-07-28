package com.xiaomi.nrb.superman.controller;

import com.xiaomi.nrb.superman.annotation.CheckLogin;
import com.xiaomi.nrb.superman.common.Result;
import com.xiaomi.nrb.superman.request.BaseRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author niuruobing@xiaomi.com
 * @since 2019-07-27 21:42
 **/
@RestController
@RequestMapping("/superman/user/green")
public class UserController {

    @RequestMapping("/getUserInfo")
    @CheckLogin
    public Result getUserInfo(@RequestBody BaseRequest request) {

        return Result.ok(null);
    }

    @RequestMapping("/register")
    public Result register(@RequestBody BaseRequest request) {
        return Result.ok(null);
    }
}
