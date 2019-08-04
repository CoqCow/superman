package com.xiaomi.nrb.superman.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xiaomi.nrb.superman.common.ApiEnum;
import com.xiaomi.nrb.superman.common.Result;
import com.xiaomi.nrb.superman.dao.UserMapper;
import com.xiaomi.nrb.superman.domain.User;
import com.xiaomi.nrb.superman.request.RegisterReq;
import com.xiaomi.nrb.superman.response.UserInfoRes;
import com.xiaomi.nrb.superman.service.TokenService;
import com.xiaomi.nrb.superman.service.UserService;
import com.xiaomi.nrb.superman.utils.HttpRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author niuruobing@xiaomi.com
 * @since 2019-08-02 07:39
 **/
@Service
public class UserServiceImpl implements UserService {


    @Value("${wechat.appId}")
    private String wechatAppId;

    @Value("${wechat.secretKey}")
    private String wechatSecretKey;

    @Resource
    private UserMapper userMapper;

    @Resource
    private TokenService tokenService;

    @Override
    public User getUserByUserId(Long userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    @Override
    public User getUserByOpenId(String openId) {
        return userMapper.selectByOpenId(openId);
    }

    @Override
    public Result<UserInfoRes> registerUser(RegisterReq registerReq) {
        String openId = getOpneid(registerReq.getCode());
        if (StringUtils.isBlank(openId)) {
            return Result.fail(ApiEnum.USER_CODE_EXPIRED.getCode());
        }
        User user = new User();
        user.setOpenId(openId);
        user.setNickName(registerReq.getNickName());
        user.setAvartarUrl(registerReq.getAvartarUrl());
        user.setGender(registerReq.getGender());
        user.setCountry(registerReq.getCountry());
        user.setProvince(registerReq.getProvince());
        user.setCity(registerReq.getCity());
        user.setCtime(new Date());
        userMapper.insertSelective(user);
        User newUser = userMapper.selectByOpenId(user.getOpenId());
        if (null == newUser) {
            Result.fail(ApiEnum.USER_REGISTER_ERROR.getCode());
        }
        UserInfoRes userInfoRes = UserInfoRes.builder().user(newUser).token(tokenService.createToken(newUser)).build();
        return Result.ok(userInfoRes);
    }

    @Override
    public User insertNotRegisterUser(User user) {
        userMapper.insertSelective(user);
        return userMapper.selectByOpenId(user.getOpenId());
    }

    public String getOpneid(String code) {
        if (StringUtils.isBlank(code)) return null;
        String params = "appid=" + wechatAppId + "&secret=" + wechatSecretKey + "&js_code=" + code + "&grant_type=authorization_code";
        String sr = HttpRequest.sendGet("https://api.weixin.qq.com/sns/jscode2session", params);
        JSONObject json = JSONObject.parseObject(sr);
        if (null == json.get("openid")) {
            return null;
        }
        return json.get("openid").toString();
    }
}
