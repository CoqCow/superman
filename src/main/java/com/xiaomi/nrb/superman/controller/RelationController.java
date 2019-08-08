package com.xiaomi.nrb.superman.controller;

import com.xiaomi.nrb.superman.annotation.CheckLogin;
import com.xiaomi.nrb.superman.common.ApiEnum;
import com.xiaomi.nrb.superman.common.Result;
import com.xiaomi.nrb.superman.request.AddRelationReq;
import com.xiaomi.nrb.superman.request.BaseRequest;
import com.xiaomi.nrb.superman.service.RelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author niuruobing@xiaomi.com
 * @since 2019-07-28 08:29
 **/
@RestController
@RequestMapping("/superman/relation/tomatoes")
@Slf4j
public class RelationController {


    @Resource
    private RelationService relationService;

    @RequestMapping("/addRelation")
    @CheckLogin
    public Result see(@RequestBody AddRelationReq request) {
        try {
            return Result.ok(relationService.addRelation(request));
        } catch (Exception e) {
            log.error("RelationController.see.error:", e);
            return Result.fail(ApiEnum.ERROR.getCode());
        }
    }
}
