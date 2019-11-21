package com.xiaomi.nrb.superman.controller;

import com.xiaomi.nrb.superman.common.Result;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author niuruobing@xiaomi.com
 * @since 2019-11-20 14:46
 **/
@RestController
@RequestMapping("/superman")
public class AliveController {

    private String message;

    @RequestMapping("/message")
    private Result messageTest(@RequestBody String message) {
        this.message = message;
        return Result.ok(message);
    }

    @RequestMapping("/alive")
    public Result aliveTest() {
        return Result.ok("message" + message);
    }
}
