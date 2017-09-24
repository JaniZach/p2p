package com.xmg.p2p.business.service;

import com.xmg.p2p.business.domain.UserBankinfo;

public interface IUserBankinfoService {
	/*
	 * 查询当前登录用户绑定银行卡的方法
	 */
	UserBankinfo getCurrent();

	/*
	 * 用户绑定银行卡的方法
	 */
	void bindBank(String accountNumber,String bankName,String forkName);
}
