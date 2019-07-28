package com.xiaomi.nrb.superman.dao;

import com.xiaomi.nrb.superman.domain.Relation;

public interface RelationMapper {


    int insertSelective(Relation record);

    Relation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Relation record);

}