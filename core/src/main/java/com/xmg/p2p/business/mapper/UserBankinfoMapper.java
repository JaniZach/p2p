package com.xmg.p2p.business.mapper;

import java.util.List;

import com.xmg.p2p.base.query.QueryObject;
import com.xmg.p2p.business.domain.UserBankinfo;

public interface UserBankinfoMapper {
    int insert(UserBankinfo record);
	int queryPageCount(QueryObject qo);
	List<UserBankinfo> queryPageData(QueryObject qo);
	/*
	 * 根据当前用户id,查询绑定的银行卡
	 */
	UserBankinfo selectByUserId(Long id);
}