package com.xiaomi.nrb.superman.service;

import com.xiaomi.nrb.superman.common.Result;
import com.xiaomi.nrb.superman.domain.User;
import com.xiaomi.nrb.superman.request.RegisterReq;
import com.xiaomi.nrb.superman.response.UserInfoRes;

/**
 * @author niuruobing@xiaomi.com
 * @since 2019-08-02 07:37
 **/
public interface UserService {
    /**
     * 根据用户id获取用户信息
     *
     * @author niuruobing@xiaomi.com
     * @since 2019-08-02 07:38
     */
    User getUserByUserId(Long userId);

    /**
     * 根据openid获取用户信息
     *
     * @author niuruobing@xiaomi.com
     * @since 2019-08-02 07:38
     */
    User getUserByOpenId(String openId);


    /**
     * 注册逻辑
     *
     * @author niuruobing@xiaomi.com
     * @since 2019-08-02 14:24
     */
    Result<UserInfoRes> registerUser(RegisterReq registerReq);


    /**
     * 未注册用户
     *
     * @author niuruobing@xiaomi.com
     * @since 2019-08-04 09:56
     */
    User insertNotRegisterUser(User user);

    /**
     * 获取openId
     *
     * @author niuruobing@xiaomi.com
     * @since 2019-08-02 14:28
     */
    String getOpneid(String code);
}
