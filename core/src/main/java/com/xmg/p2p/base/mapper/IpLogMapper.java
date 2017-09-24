package com.xmg.p2p.base.mapper;

import java.util.List;

import com.xmg.p2p.base.domain.IpLog;
import com.xmg.p2p.base.query.QueryObject;

public interface IpLogMapper {
    int insert(IpLog record);
	Long queryPageCount(QueryObject qo);
	List<IpLog> queryPageData(QueryObject qo);
}