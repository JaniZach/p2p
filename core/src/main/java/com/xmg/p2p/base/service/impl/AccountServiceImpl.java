package com.xmg.p2p.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xmg.p2p.base.domain.Account;
import com.xmg.p2p.base.mapper.AccountMapper;
import com.xmg.p2p.base.service.IAccountService;
import com.xmg.p2p.base.util.UserContext;

@Service
public class AccountServiceImpl implements IAccountService {
	@Autowired
	private AccountMapper accountMapper;

	public int insert(Account record) {
		int ret = accountMapper.insert(record);
		return ret;
	}

	public Account selectByPrimaryKey(Long id) {
		return accountMapper.selectByPrimaryKey(id);
	}

	public int updateByPrimaryKey(Account record) {
		int ret = accountMapper.updateByPrimaryKey(record);
		// 乐观锁的处理,版本不是最新版本,则抛出异常,事务回滚
		if (ret <= 0) {
			throw new RuntimeException("乐观锁,accountId=" + record.getId());
		}
		return ret;
	}

	@Override
	public Account getCurrent() {
		Long id = UserContext.getCurrent().getId();
		Account account = accountMapper.selectByPrimaryKey(id);
		return account;
	}
}
