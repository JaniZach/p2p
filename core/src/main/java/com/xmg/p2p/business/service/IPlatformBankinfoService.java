package com.xmg.p2p.business.service;
import java.util.List;

import com.xmg.p2p.base.page.PageResult;
import com.xmg.p2p.base.query.QueryObject;
import com.xmg.p2p.business.domain.PlatformBankinfo;

public interface IPlatformBankinfoService {
	int deleteByPrimaryKey(Long id);
    PlatformBankinfo selectByPrimaryKey(Long id);
	PageResult queryPage(QueryObject qo);
	/**
	 * 平台账户的保存或修改方法
	 * @param bankinfo
	 */
	void saveOrUpdate(PlatformBankinfo bankinfo);
	/**
	 * 查询全部平台账号信息
	 * @return
	 */
	List<PlatformBankinfo> selectAll();
}
