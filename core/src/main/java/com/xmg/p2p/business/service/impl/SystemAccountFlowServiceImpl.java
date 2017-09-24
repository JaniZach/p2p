package com.xmg.p2p.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xmg.p2p.base.page.PageResult;
import com.xmg.p2p.base.query.QueryObject;
import com.xmg.p2p.business.domain.SystemAccountFlow;
import com.xmg.p2p.business.mapper.SystemAccountFlowMapper;
import com.xmg.p2p.business.service.ISystemAccountFlowService;

@Service
public class SystemAccountFlowServiceImpl implements ISystemAccountFlowService {
	@Autowired
	private SystemAccountFlowMapper systemAccountFlowMapper;

	@Override
	public int insert(SystemAccountFlow record) {
		return systemAccountFlowMapper.insert(record);
	}

	@Override
	public SystemAccountFlow selectByPrimaryKey(Long id) {
		return systemAccountFlowMapper.selectByPrimaryKey(id);
	}

	@Override
	public PageResult queryPage(QueryObject qo) {
		int count = systemAccountFlowMapper.queryPageCount(qo);
		if (count <= 0) {
			return PageResult.empty(qo.getPageSize());
		}
		List<SystemAccountFlow> result = systemAccountFlowMapper.queryPageData(qo);
		PageResult pageResult = new PageResult(result, count, qo.getCurrentPage(), qo.getPageSize());
		return pageResult;
	}
}
