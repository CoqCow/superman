package com.xiaomi.nrb.superman.controller;

import com.xiaomi.nrb.superman.common.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author niuruobing@xiaomi.com
 * @since 2019-11-20 14:46
 **/
@RestController
@RequestMapping("/superman")
public class AliveController {

    @RequestMapping("/alive")
    public Result aliveTest() {
        return Result.ok("yes");
    }
}
