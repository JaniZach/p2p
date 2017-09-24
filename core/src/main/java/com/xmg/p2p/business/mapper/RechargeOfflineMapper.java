package com.xmg.p2p.business.mapper;

import java.util.List;

import com.xmg.p2p.base.query.QueryObject;
import com.xmg.p2p.business.domain.RechargeOffline;

public interface RechargeOfflineMapper {
    int insert(RechargeOffline record);
    RechargeOffline selectByPrimaryKey(Long id);
	int queryPageCount(QueryObject qo);
	List<RechargeOffline> queryPageData(QueryObject qo);
	void updateByPrimaryKey(RechargeOffline ro);
}