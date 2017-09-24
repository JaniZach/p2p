package com.xmg.p2p.business.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xmg.p2p.base.util.BidConst;
import com.xmg.p2p.business.domain.BidRequest;
import com.xmg.p2p.business.domain.PaymentScheduleDetail;
import com.xmg.p2p.business.domain.SystemAccount;
import com.xmg.p2p.business.domain.SystemAccountFlow;
import com.xmg.p2p.business.mapper.SystemAccountFlowMapper;
import com.xmg.p2p.business.mapper.SystemAccountMapper;
import com.xmg.p2p.business.service.ISystemAccountService;

@Service
public class SystemAccountServiceImpl implements ISystemAccountService {
	@Autowired
	private SystemAccountMapper systemAccountMapper;

	@Autowired
	private SystemAccountFlowMapper flowMapper;

	@Override
	public int insert(SystemAccount record) {
		return systemAccountMapper.insert(record);
	}

	@Override
	public SystemAccount selectCurrent() {
		return systemAccountMapper.selectCurrent();
	}

	@Override
	public int updateByPrimaryKey(SystemAccount record) {
		int ret = systemAccountMapper.updateByPrimaryKey(record);
		if (ret == 0) {
			throw new RuntimeException("操作失败;乐观锁");
		}
		return ret;
	}

	@Override
	public void initSystemAccount() {
		SystemAccount current = this.systemAccountMapper.selectCurrent();
		if (current == null) {
			current = new SystemAccount();
			this.systemAccountMapper.insert(current);
		}
	}

	@Override
	public void receiveBorrowFee(BidRequest br, BigDecimal borrowFee) {
		// 获取系统账户
		SystemAccount account = this.selectCurrent();
		account.setUsableAmount(account.getUsableAmount().add(borrowFee));
		// 更新账户信息(必须使用service中的update方法,保证使用乐观锁)
		this.updateByPrimaryKey(account);
		// 生成获取借款管理费流水记录
		SystemAccountFlow flow = new SystemAccountFlow();
		flow.setActionTime(new Date());
		flow.setActionType(BidConst.SYSTEM_ACCOUNT_ACTIONTYPE_MANAGE_CHARGE);// 流水类型:平台收取借款管理费
		flow.setAmount(borrowFee);
		flow.setFreezedAmount(account.getFreezedAmount());
		flow.setNote("用户投标:" + br.getTitle() + ",收取借款管理费" + borrowFee);
		flow.setUsableAmount(account.getUsableAmount());
		this.flowMapper.insert(flow);
	}

	@Override
	public void receiveInterestManageFee(PaymentScheduleDetail psd, BigDecimal manageFee) {
		// 获取系统账户
		SystemAccount account = this.selectCurrent();
		account.setUsableAmount(account.getUsableAmount().add(manageFee));
		// 更新账户信息(必须使用service中的update方法,保证使用乐观锁)
		this.updateByPrimaryKey(account);
		// 生成获取借款管理费流水记录
		SystemAccountFlow flow = new SystemAccountFlow();
		flow.setActionTime(new Date());
		flow.setActionType(BidConst.SYSTEM_ACCOUNT_ACTIONTYPE_INTREST_MANAGE_CHARGE);// 流水类型:平台收取利息管理费
		flow.setAmount(manageFee);
		flow.setFreezedAmount(account.getFreezedAmount());
		flow.setNote("用户投标:" + psd.getBidRequestTitle() + "成功,收取利息管理费" + manageFee);
		flow.setUsableAmount(account.getUsableAmount());
		this.flowMapper.insert(flow);
	}
}
