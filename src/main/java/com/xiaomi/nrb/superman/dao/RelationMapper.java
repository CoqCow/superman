package com.xiaomi.nrb.superman.dao;

import com.xiaomi.nrb.superman.domain.Relation;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface RelationMapper {


    int insertSelective(Relation record);

    Relation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Relation record);

    List<Relation> listBySelective(Relation record);

    int countBySelective(Relation record);

}