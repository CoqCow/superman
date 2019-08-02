package com.xiaomi.nrb.superman.service;

import com.xiaomi.nrb.superman.domain.User;

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
}
