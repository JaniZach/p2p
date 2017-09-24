package com.xmg.p2p.business.service;
import com.xmg.p2p.base.page.PageResult;
import com.xmg.p2p.base.query.QueryObject;
import com.xmg.p2p.business.domain.SystemAccountFlow;

public interface ISystemAccountFlowService {
    int insert(SystemAccountFlow record);
    SystemAccountFlow selectByPrimaryKey(Long id);
	PageResult queryPage(QueryObject qo);
}
