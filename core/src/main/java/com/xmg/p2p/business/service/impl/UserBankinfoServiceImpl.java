package com.xmg.p2p.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.util.UserContext;
import com.xmg.p2p.business.domain.UserBankinfo;
import com.xmg.p2p.business.mapper.UserBankinfoMapper;
import com.xmg.p2p.business.service.IUserBankinfoService;
@Service
public class UserBankinfoServiceImpl implements IUserBankinfoService {
	@Autowired
	private UserBankinfoMapper userBankinfoMapper;
	@Autowired
	private IUserinfoService userinfoService;

	@Override
	public UserBankinfo getCurrent() {
		UserBankinfo bankinfo = this.userBankinfoMapper.selectByUserId(UserContext.getCurrent().getId());
		return bankinfo;
	}

	@Override
	public void bindBank(String accountNumber,String bankName,String forkName) {
		UserBankinfo b = new UserBankinfo();
		Userinfo userinfo = this.userinfoService.getCurrent();
		if(userinfo != null && userinfo.getHasBindBank()){
			//执行绑定银行卡操作
			b.setAccountName(userinfo.getRealName());
			b.setAccountNumber(accountNumber);
			b.setBankName(bankName);
			b.setForkName(forkName);
			b.setLogininfoId(UserContext.getCurrent().getId());
		}
	}
}
