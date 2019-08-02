package com.xiaomi.nrb.superman.response;

import com.xiaomi.nrb.superman.domain.User;
import lombok.Builder;
import lombok.Data;

/**
 * @author niuruobing@xiaomi.com
 * @since 2019-08-02 08:02
 **/
@Data
@Builder
public class UserInfoRes {
    private User user;
    /**
     * 登录态
     */
    private String token;
}
