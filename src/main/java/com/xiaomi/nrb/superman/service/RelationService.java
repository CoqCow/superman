package com.xiaomi.nrb.superman.service;

import com.xiaomi.nrb.superman.request.AddRelationReq;
import com.xiaomi.nrb.superman.response.PlanInfo;

/**
 * @author niuruobing@xiaomi.com
 * @since 2019-08-08 08:05
 **/
public interface RelationService {

    /**
     * 围观计划
     *
     * @author niuruobing@xiaomi.com
     * @since 2019-08-08 08:07
     */
    PlanInfo addRelation(AddRelationReq addRelationReq);
}
