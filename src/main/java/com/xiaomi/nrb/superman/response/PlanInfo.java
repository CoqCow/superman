package com.xiaomi.nrb.superman.response;

import com.xiaomi.nrb.superman.domain.Plan;
import lombok.Data;

import java.util.List;

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
     * 是否围观
     */
    private boolean seeTag;
    /**
     * 是否点赞
     */
    private boolean zanTag;
    /**
     * 是否挑战
     */
    private boolean challengeTag;
    /**
     * 是否为you计划
     */
    private boolean youTag;
    /**
     * 围观数量
     */
    private Integer seeNum;
    /**
     * 点赞数量
     */
    private Integer zanNum;
    /**
     * 挑战数量
     */
    private Integer challengeNum;

    /**
     * 创建时间
     */
    private String ctimeForShow;
    /**
     * 点赞用户头像
     */
    private List<String> zanAvartarUrls;
    /**
     * 挑战用户头像
     */
    private List<String> challengeAvartarUrls;
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
