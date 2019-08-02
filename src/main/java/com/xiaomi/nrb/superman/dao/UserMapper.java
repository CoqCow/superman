package com.xiaomi.nrb.superman.dao;

import com.xiaomi.nrb.superman.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserMapper {

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    User selectByOpenId(String openId);

    int updateByPrimaryKeySelective(User record);

}