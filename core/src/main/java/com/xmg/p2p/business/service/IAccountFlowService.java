package com.xmg.p2p.business.service;
import java.math.BigDecimal;

import com.xmg.p2p.base.domain.Account;
import com.xmg.p2p.base.page.PageResult;
import com.xmg.p2p.base.query.QueryObject;
import com.xmg.p2p.business.domain.Bid;
import com.xmg.p2p.business.domain.BidRequest;
import com.xmg.p2p.business.domain.PaymentSchedule;
import com.xmg.p2p.business.domain.PaymentScheduleDetail;

public interface IAccountFlowService {
	PageResult queryPage(QueryObject qo);

	/**
	 * 创建一条投标账户流水记录
	 * @param bidAccount
	 * @param amount
	 */
	void createBidFlow(Account bidAccount, Bid bid);

	/**
	 * 创建一条投标失败流水记录
	 * @param bidAccount
	 * @param b
	 */
	void createBidFailureFlow(Account bidAccount, Bid bid);

	/**
	 * 创建一条借款成功,收款流水记录
	 * @param br 借款标的id
	 * @param account 借款人账户
	 */
	void createBorrowSuccess(BidRequest br, Account account);

	/**
	 * 创建一条用户支付借款管理费的流水记录
	 * @param br
	 * @param borrowFee
	 * @param account
	 */
	void createPayBorrowManageFee(BidRequest br, BigDecimal borrowFee,Account account);

	/**
	 * 创建一条用户还款成功流水
	 * @param ps
	 * @param borrowAccount
	 */
	void createBorrowReturn(PaymentSchedule ps, Account borrowAccount);

	/**
	 * 创建一条用户收取还款
	 * @param psd
	 * @param toReturnAccount
	 */
	void createReceiveBidReturn(PaymentScheduleDetail psd, Account toReturnAccount);

	/**
	 * 创建一条用户上交利息管理费的流水
	 * @param psd
	 * @param toReturnAccount
	 * @param manageFee
	 */
	void createPayInterestManageFee(PaymentScheduleDetail psd, Account toReturnAccount, BigDecimal manageFee);

}
