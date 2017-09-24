package com.xmg.p2p.business.service.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xmg.p2p.base.domain.Account;
import com.xmg.p2p.base.page.PageResult;
import com.xmg.p2p.base.query.QueryObject;
import com.xmg.p2p.base.util.BidConst;
import com.xmg.p2p.business.domain.AccountFlow;
import com.xmg.p2p.business.domain.Bid;
import com.xmg.p2p.business.domain.BidRequest;
import com.xmg.p2p.business.domain.PaymentSchedule;
import com.xmg.p2p.business.domain.PaymentScheduleDetail;
import com.xmg.p2p.business.mapper.AccountFlowMapper;
import com.xmg.p2p.business.service.IAccountFlowService;
@Service
public class AccountFlowServiceImpl implements IAccountFlowService {
	@Autowired
	private AccountFlowMapper accountFlowMapper;
	
	@Override
	public PageResult queryPage(QueryObject qo) {
		int count = accountFlowMapper.queryPageCount(qo);
		if(count<=0){
			return PageResult.empty(qo.getPageSize());
		}
		List<AccountFlow> result = accountFlowMapper.queryPageData(qo);
		PageResult pageResult = new PageResult(result,count,qo.getCurrentPage(),qo.getPageSize());
		return pageResult;
	}

	@Override
	public void createBidFlow(Account bidAccount, Bid bid) {
		AccountFlow flow = new AccountFlow();
		flow.setAccountId(bidAccount.getId());
		flow.setActionTime(new Date());
		flow.setActionType(BidConst.ACCOUNT_ACTIONTYPE_BID_FREEZED);//流水类型:投标冻结资金
		flow.setAmount(bid.getAvailableAmount());
		flow.setFreezedAmount(bidAccount.getFreezedAmount());
		flow.setNote("用户投标:"+bid.getBidRequestTitle()+",冻结账户金额"+bid.getAvailableAmount());
		flow.setUsableAmount(bidAccount.getUsableAmount());
		this.accountFlowMapper.insert(flow);
	}

	@Override
	public void createBidFailureFlow(Account bidAccount, Bid bid) {
		AccountFlow flow = new AccountFlow();
		flow.setAccountId(bidAccount.getId());
		flow.setActionTime(new Date());
		flow.setActionType(BidConst.ACCOUNT_ACTIONTYPE_BID_UNFREEZED);//流水类型:标审核失败,取消冻结资金
		flow.setAmount(bid.getAvailableAmount());
		flow.setFreezedAmount(bidAccount.getFreezedAmount());
		flow.setNote("用户投标:"+bid.getBidRequestTitle()+"失败,账户剩余可用金额"+bidAccount.getUsableAmount());
		flow.setUsableAmount(bidAccount.getUsableAmount());
		this.accountFlowMapper.insert(flow);
	}

	@Override
	public void createBorrowSuccess(BidRequest br, Account account) {
		AccountFlow flow = new AccountFlow();
		flow.setAccountId(account.getId());
		flow.setActionTime(new Date());
		flow.setActionType(BidConst.ACCOUNT_ACTIONTYPE_BIDREQUEST_SUCCESSFUL);//流水类型:借款成功,可用余额增加
		flow.setAmount(br.getBidRequestAmount());
		flow.setFreezedAmount(account.getFreezedAmount());
		flow.setNote("用户借款:"+br.getTitle()+"成功,账户可用金额"+account.getUsableAmount());
		flow.setUsableAmount(account.getUsableAmount());
		this.accountFlowMapper.insert(flow);
	}

	@Override
	public void createPayBorrowManageFee(BidRequest br, BigDecimal borrowFee,Account account) {
		AccountFlow flow = new AccountFlow();
		flow.setAccountId(account.getId());
		flow.setActionTime(new Date());
		flow.setActionType(BidConst.ACCOUNT_ACTIONTYPE_CHARGE);//流水类型:用户支付平台借款管理费
		flow.setAmount(br.getBidRequestAmount());
		flow.setFreezedAmount(account.getFreezedAmount());
		flow.setNote("用户借款:"+br.getTitle()+"成功,支付借款管理费"+borrowFee);
		flow.setUsableAmount(account.getUsableAmount());
		this.accountFlowMapper.insert(flow);
	}

	@Override
	public void createBorrowReturn(PaymentSchedule ps, Account borrowAccount) {
		AccountFlow flow = new AccountFlow();
		flow.setAccountId(borrowAccount.getId());
		flow.setActionTime(new Date());
		flow.setActionType(BidConst.ACCOUNT_ACTIONTYPE_RETURN_MONEY);//流水类型:还款
		flow.setAmount(ps.getTotalAmount());
		flow.setFreezedAmount(borrowAccount.getFreezedAmount());
		flow.setNote("用户借款:"+ps.getBidRequestTitle()+"成功,还款"+ps.getTotalAmount());
		flow.setUsableAmount(borrowAccount.getUsableAmount());
		this.accountFlowMapper.insert(flow);
	}

	@Override
	public void createReceiveBidReturn(PaymentScheduleDetail psd, Account toReturnAccount) {
		AccountFlow flow = new AccountFlow();
		flow.setAccountId(toReturnAccount.getId());
		flow.setActionTime(new Date());
		flow.setActionType(BidConst.ACCOUNT_ACTIONTYPE_CALLBACK_MONEY);//流水类型:回款
		flow.setAmount(psd.getTotalAmount());
		flow.setFreezedAmount(toReturnAccount.getFreezedAmount());
		flow.setNote("用户投资:"+psd.getBidRequestTitle()+"=,收到回款"+psd.getTotalAmount());
		flow.setUsableAmount(toReturnAccount.getUsableAmount());
		this.accountFlowMapper.insert(flow);
	}

	@Override
	public void createPayInterestManageFee(PaymentScheduleDetail psd, Account toReturnAccount, BigDecimal manageFee) {
		AccountFlow flow = new AccountFlow();
		flow.setAccountId(toReturnAccount.getId());
		flow.setActionTime(new Date());
		flow.setActionType(BidConst.ACCOUNT_ACTIONTYPE_INTEREST_SHARE);//流水类型:用户支付平台利息管理费
		flow.setAmount(psd.getInterest());
		flow.setFreezedAmount(toReturnAccount.getFreezedAmount());
		flow.setNote("用户投资:"+psd.getBidRequestTitle()+"成功,支付利息管理费"+manageFee);
		flow.setUsableAmount(toReturnAccount.getUsableAmount());
		this.accountFlowMapper.insert(flow);
	}
}