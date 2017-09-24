package com.xmg.p2p.business.mapper;

import java.util.List;

import com.xmg.p2p.base.query.QueryObject;
import com.xmg.p2p.business.domain.SystemAccountFlow;

public interface SystemAccountFlowMapper {
    int insert(SystemAccountFlow record);
    SystemAccountFlow selectByPrimaryKey(Long id);
	int queryPageCount(QueryObject qo);
	List<SystemAccountFlow> queryPageData(QueryObject qo);
}