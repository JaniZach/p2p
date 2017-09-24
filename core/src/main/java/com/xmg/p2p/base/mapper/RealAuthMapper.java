package com.xmg.p2p.base.mapper;

import java.util.List;

import com.xmg.p2p.base.domain.RealAuth;
import com.xmg.p2p.base.query.QueryObject;
import org.apache.ibatis.annotations.Param;

public interface RealAuthMapper {
    int deleteByPrimaryKey(Long id);

    int insert(@Param("r") RealAuth record,@Param("key") String key);

    RealAuth selectByPrimaryKey(@Param("id") Long id,@Param("key") String key);

    List<RealAuth> selectAll();

    int updateByPrimaryKey(@Param("r") RealAuth record,@Param("key") String key);

    Long queryPageCount(@Param("qo") QueryObject qo,@Param("key") String key);

    List<RealAuth> queryPageData(@Param("qo") QueryObject qo,@Param("key") String key);
}