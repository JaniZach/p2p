package com.xmg.p2p.business.mapper;

import java.util.List;

import com.xmg.p2p.base.query.QueryObject;
import com.xmg.p2p.business.domain.PlatformBankinfo;

public interface PlatformBankinfoMapper {
    int deleteByPrimaryKey(Long id);
    int insert(PlatformBankinfo record);
    PlatformBankinfo selectByPrimaryKey(Long id);
    int updateByPrimaryKey(PlatformBankinfo record);
	int queryPageCount(QueryObject qo);
	List<PlatformBankinfo> queryPageData(QueryObject qo);
	/**
	 * 查询全部平台账号的方法
	 * @return
	 */
	List<PlatformBankinfo> selectAll();
}