package com.xiaomi.nrb.superman.response;

import com.xiaomi.nrb.superman.domain.Plan;
import lombok.Data;

/**
 * @author niuruobing@xiaomi.com
 * @since 2019-08-05 22:26
 **/
@Data
public class PlanInfo extends Plan {
    /**
     * 是否是自己的计划
     */
    private boolean tag;
    /**
     * 微信昵称
     */
    private String nickName;
    /**
     * 微信头像
     */
    private String avartarUrl;
    /**
     * 性别 0未知、1男、2女
     */
    private Integer gender;
}
