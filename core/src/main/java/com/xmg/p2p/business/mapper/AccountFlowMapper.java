package com.xmg.p2p.business.mapper;

import java.util.List;

import com.xmg.p2p.base.query.QueryObject;
import com.xmg.p2p.business.domain.AccountFlow;

public interface AccountFlowMapper {
    int insert(AccountFlow record);
    AccountFlow selectByPrimaryKey(Long id);
	int queryPageCount(QueryObject qo);
	List<AccountFlow> queryPageData(QueryObject qo);
}