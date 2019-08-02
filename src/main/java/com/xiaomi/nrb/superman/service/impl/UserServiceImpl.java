package com.xiaomi.nrb.superman.service.impl;

import com.xiaomi.nrb.superman.dao.UserMapper;
import com.xiaomi.nrb.superman.domain.User;
import com.xiaomi.nrb.superman.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author niuruobing@xiaomi.com
 * @since 2019-08-02 07:39
 **/
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public User getUserByUserId(Long userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    @Override
    public User getUserByOpenId(String openId) {
        return userMapper.selectByOpenId(openId);
    }
}
