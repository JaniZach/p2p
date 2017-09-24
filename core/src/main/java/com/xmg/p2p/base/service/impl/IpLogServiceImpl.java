package com.xmg.p2p.base.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xmg.p2p.base.domain.IpLog;
import com.xmg.p2p.base.mapper.IpLogMapper;
import com.xmg.p2p.base.page.PageResult;
import com.xmg.p2p.base.query.QueryObject;
import com.xmg.p2p.base.service.IIpLogService;
@Service
public class IpLogServiceImpl implements IIpLogService {
	@Autowired
	private IpLogMapper ipLogMapper;

	public int insert(IpLog record) {
		return ipLogMapper.insert(record);
	}

	@Override
	public PageResult queryPage(QueryObject qo) {
		Long count = ipLogMapper.queryPageCount(qo);
		if(count<=0){
			return PageResult.empty(qo.getPageSize());
		}
		List<IpLog> result = ipLogMapper.queryPageData(qo);
		PageResult pageResult = new PageResult(result,count.intValue(),qo.getCurrentPage(),qo.getPageSize());
		return pageResult;
	}
}
