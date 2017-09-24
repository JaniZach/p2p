package com.xmg.p2p.business.service;

import java.math.BigDecimal;

import com.xmg.p2p.business.domain.BidRequest;
import com.xmg.p2p.business.domain.PaymentScheduleDetail;
import com.xmg.p2p.business.domain.SystemAccount;

public interface ISystemAccountService {
	int insert(SystemAccount record);

	SystemAccount selectCurrent();

	int updateByPrimaryKey(SystemAccount record);

	/**
	 * 初始化平台账户的方法
	 */
	void initSystemAccount();

	/**
	 * 平台收取借款管理费用,并生成相应的流水记录
	 * @param br
	 * @param borrowFee
	 */
	void receiveBorrowFee(BidRequest br, BigDecimal borrowFee);

	/**
	 * 平台收取利息管理费,并生成相应的流水
	 * @param psd
	 * @param manageFee
	 */
	void receiveInterestManageFee(PaymentScheduleDetail psd, BigDecimal manageFee);
}
