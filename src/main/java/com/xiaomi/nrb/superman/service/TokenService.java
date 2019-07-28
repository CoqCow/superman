package com.xiaomi.nrb.superman.service;

import com.xiaomi.nrb.superman.domain.User;

/**
 * @author niuruobing@xiaomi.com
 * @since 2019-07-28 08:33
 **/
public interface TokenService {

    String createToken(User user);

    boolean validateToken(String token);

    String getParam(String token, String key);
}
