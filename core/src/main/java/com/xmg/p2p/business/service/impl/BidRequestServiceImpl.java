package com.xmg.p2p.business.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xmg.p2p.base.domain.Account;
import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.page.PageResult;
import com.xmg.p2p.base.query.QueryObject;
import com.xmg.p2p.base.service.IAccountService;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.util.BidConst;
import com.xmg.p2p.base.util.BitStatesUtils;
import com.xmg.p2p.base.util.UserContext;
import com.xmg.p2p.business.domain.Bid;
import com.xmg.p2p.business.domain.BidRequest;
import com.xmg.p2p.business.domain.PaymentSchedule;
import com.xmg.p2p.business.domain.PaymentScheduleDetail;
import com.xmg.p2p.business.mapper.BidMapper;
import com.xmg.p2p.business.mapper.BidRequestMapper;
import com.xmg.p2p.business.query.BidRequestQueryObject;
import com.xmg.p2p.business.query.PaymentScheduleQueryObject;
import com.xmg.p2p.business.service.IAccountFlowService;
import com.xmg.p2p.business.service.IBidRequestService;
import com.xmg.p2p.business.service.IPaymentScheduleDetailService;
import com.xmg.p2p.business.service.IPaymentScheduleService;
import com.xmg.p2p.business.service.ISystemAccountService;
import com.xmg.p2p.business.util.CalculatetUtil;
@Service
public class BidRequestServiceImpl implements IBidRequestService {
	@Autowired
	private BidRequestMapper bidRequestMapper;
	@Autowired
	private IUserinfoService userinfoService;
	@Autowired
	private IAccountService accountService;
	@Autowired
	private BidMapper bidMapper;
	@Autowired
	private IAccountFlowService flowService;
	@Autowired
	private IPaymentScheduleService psService;
	@Autowired
	private IPaymentScheduleDetailService psdService;
	@Autowired
	private ISystemAccountService systemAccountService;

	public int insert(BidRequest record) {
		int ret = bidRequestMapper.insert(record);
		return ret;
	}

	public BidRequest selectByPrimaryKey(Long id) {
		return bidRequestMapper.selectByPrimaryKey(id);
	}

	//乐观锁控制
	public int updateByPrimaryKey(BidRequest record) {
		int ret = bidRequestMapper.updateByPrimaryKey(record);
		if(ret==0){
			throw new RuntimeException("乐观锁:bidRequestId="+record.getId());
		}
		return ret;
	}

	@Override
	public PageResult queryPage(QueryObject qo) {
		Long count = bidRequestMapper.queryPageCount(qo);
		if(count<=0){
			return PageResult.empty(qo.getPageSize());
		}
		List<BidRequest> result = bidRequestMapper.queryPageData(qo);
		PageResult pageResult = new PageResult(result,count.intValue(),qo.getCurrentPage(),qo.getPageSize());
		return pageResult;
	}

	@Override
	public void apply(BidRequest br) {
		//判断用户有没有借款资格
		Account account = this.accountService.getCurrent();
		Userinfo userinfo = this.userinfoService.getCurrent();
		if(!userinfo.getHasBasicInfo()//基本信息完成
				|| !userinfo.getHasRealAuth()//真实身份认证
				|| !userinfo.getHasVedioAuth()//视频认证
				|| userinfo.getScore()<BidConst.BORROW_LIMIT_SCORE//材料认证达到分数要求
				|| userinfo.getHasBidRequestProcess()){//没有待处理的借款流程
			throw new RuntimeException("用户没有借款资格");
		}
		if(br.getBidRequestAmount().compareTo(BidConst.SMALLEST_BIDREQUEST_AMOUNT)<0//系统最小借款金额<=借款金额
				|| br.getBidRequestAmount().compareTo(account.getRemainBorrowLimit())>0//借款金额<=剩余信用金额
				|| br.getCurrentRate().compareTo(BidConst.SMALLEST_CURRENT_RATE)<0//系统最小借款利率<=借款利率
				|| br.getCurrentRate().compareTo(BidConst.MAX_CURRENT_RATE)>0//借款利率<=系统最大借款利率
				|| br.getMinBidAmount().compareTo(BidConst.SMALLEST_BID_AMOUNT)<0){//最小投标>=系统最小投标
			throw new RuntimeException("借款信息不符合平台要求");
		}
		//进行借款处理
		BidRequest b = new BidRequest();
		b.setApplyTime(new Date());
		b.setBidRequestAmount(br.getBidRequestAmount());
		b.setBidRequestState(BidConst.BIDREQUEST_STATE_PUBLISH_PENDING);
		b.setBidRequestType(BidConst.BIDREQUEST_TYPE_NORMAL);
		b.setCreateUser(UserContext.getCurrent());
		b.setCurrentRate(br.getCurrentRate());
		b.setDescription(br.getDescription());
		b.setDisableDate(br.getDisableDate());
		b.setDisableDays(br.getDisableDays());
		b.setMinBidAmount(br.getMinBidAmount());
		b.setMonthes2Return(br.getMonthes2Return());
		b.setReturnType(br.getReturnType());
		b.setTitle(br.getTitle());
		//计算标的总回报金额
		b.setTotalRewardAmount(CalculatetUtil.calTotalInterest(b.getReturnType(), b.getBidRequestAmount(), 
				b.getCurrentRate(), b.getMonthes2Return()));
		this.bidRequestMapper.insert(b);
		//更新用户状态码
		userinfo.addState(BitStatesUtils.OP_HAS_BIDREQUEST_PROCESS);
		this.userinfoService.updateByPrimaryKey(userinfo);
	}

	@Override
	public List<BidRequest> listByUserId(Long userId) {
		return this.bidRequestMapper.listByUserId(userId);
	}

	@Override
	public List<BidRequest> listIndex(int i) {
		BidRequestQueryObject qo = new BidRequestQueryObject();
		qo.setPageSize(i);//设置分页大小
		qo.setBidRequestStates(new int[]{
				BidConst.BIDREQUEST_STATE_BIDDING,
                BidConst.BIDREQUEST_STATE_PAYING_BACK,BidConst.BIDREQUEST_STATE_COMPLETE_PAY_BACK});//设置查询借款标的状态集
		qo.setOrderBy("bidRequestType");//根据哪一列查询(借款类型)
		qo.setOrderType("ASC");//设置查询规则
		return this.bidRequestMapper.queryPageData(qo);
	}

	@Override
	public void invest(Long bidRequestId, BigDecimal amount) {
		//1.判断
		BidRequest br = this.bidRequestMapper.selectByPrimaryKey(bidRequestId);
		Account bidAccount = this.accountService.selectByPrimaryKey(UserContext.getCurrent().getId());
		if(br!=null && !br.getCreateUser().getId().equals(UserContext.getCurrent().getId())//当前用户不是借款人
				&& br.getBidRequestState()==BidConst.BIDREQUEST_STATE_BIDDING//借款标处于招标中
				&& amount.compareTo(br.getMinBidAmount())>=0//投标金额必须大于最小投标金额;
				&& amount.compareTo(br.getRemainAmount())<=0//投标金额必须小于借款标的剩余金额
				&& amount.compareTo(bidAccount.getUsableAmount())<=0){//投标金额必须小于借款人的可用金额
			//2.操作
				//创建bid对象
			Bid bid = new Bid();
			bid.setActualRate(br.getCurrentRate());
			bid.setAvailableAmount(amount);
			bid.setBidRequestId(bidRequestId);
			bid.setBidRequestTitle(br.getTitle());
			bid.setBidUser(UserContext.getCurrent());
			bid.setBidTime(new Date());
				//修改借款标信息
			br.setBidCount(br.getBidCount() + 1);//借款标中 投标次数+1
			List<Bid> bids = br.getBids();
			bids.add(bid);
			br.setBids(bids);
			br.setCurrentSum(br.getCurrentSum().add(amount));
			if(br.getRemainAmount().compareTo(BidConst.ZERO)==0){
				//3.如果满标
				br.setBidRequestState(BidConst.BIDREQUEST_STATE_APPROVE_PENDING_1);
				bid.setBidRequestState(br.getBidRequestState());
			}
			//4.投标人账户信息修改--可用金额减少,冻结金额增加
			bidAccount.setUsableAmount(bidAccount.getUsableAmount().subtract(amount));
			bidAccount.setFreezedAmount(bidAccount.getFreezedAmount().add(amount));
			//5.生成一条投标流水记录
			this.flowService.createBidFlow(bidAccount,bid);
			this.accountService.updateByPrimaryKey(bidAccount);
			this.bidMapper.insert(bid);//保存一条投标对象
			this.updateByPrimaryKey(br);//注意使用service的updatByPrimaryKey方法,才可以使用乐观锁
		}
	}

	@Override
	public void returnMoney(Long psId) {
		//1.判断
		PaymentSchedule ps = this.psService.selectByPrimaryKey(psId);
		Account borrowAccount = this.accountService.getCurrent();
		if(ps!=null && ps.getState()==BidConst.PAYMENT_STATE_NORMAL//还款对象处于正常还款状态
				&& UserContext.getCurrent().getId().equals(ps.getBorrowUser().getId())//当前用户是还款用户
				&& ps.getTotalAmount().compareTo(borrowAccount.getUsableAmount())<=0){//还款金额<=账户可用余额
			//2.操作
			//2.1对于还款人
				//可用金额减少
			borrowAccount.setUsableAmount(borrowAccount.getUsableAmount().subtract(ps.getTotalAmount()));
				//待还总额减少
			borrowAccount.setUnReturnAmount(borrowAccount.getUnReturnAmount().subtract(ps.getTotalAmount()));
				//生成一条还款成功流水
			this.flowService.createBorrowReturn(ps, borrowAccount);
				//剩余信用额度增加(注意,这是增加的是本金)
			borrowAccount.setRemainBorrowLimit(borrowAccount.getRemainBorrowLimit().add(ps.getPrincipal()));
			//************更新还款人账户信息************//
			this.accountService.updateByPrimaryKey(borrowAccount);
			//2.2对于投资人
			for (int i = 0; i < ps.getPaymentScheduleDetails().size(); i++) {
				PaymentScheduleDetail psd = ps.getPaymentScheduleDetails().get(i);
				Account toReturnAccount = this.accountService.selectByPrimaryKey(psd.getToLogininfoId());
				//可用金额增加
				toReturnAccount.setUsableAmount(toReturnAccount.getUsableAmount().add(psd.getTotalAmount()));
				//待收本金减少,待收利息减少
				toReturnAccount.setUnReceivePrincipal(toReturnAccount.getUnReceivePrincipal().subtract(psd.getPrincipal()));
				toReturnAccount.setUnReceiveInterest(toReturnAccount.getUnReceiveInterest().subtract(psd.getInterest()));
				//生成一条收取还款流水
				this.flowService.createReceiveBidReturn(psd,toReturnAccount);
				//上交利息管理费用
				BigDecimal manageFee = CalculatetUtil.calInterestManagerCharge(psd.getInterest());
				//投资人账户减去利息管理费
				toReturnAccount.setUsableAmount(toReturnAccount.getUsableAmount().subtract(manageFee));
				//生成一条用户上交利息管理费流水
				this.flowService.createPayInterestManageFee(psd,toReturnAccount,manageFee);
				//************更新投资人账户信息************//
				this.accountService.updateByPrimaryKey(toReturnAccount);
			//2.3对于平台
				//系统收取利息管理费用,并生成相应流水
				this.systemAccountService.receiveInterestManageFee(psd,manageFee);
			}
			//3.对于还款本身
				//设置付款时间
			ps.setPayDate(new Date());
				//设置还款对象状态
			ps.setState(BidConst.PAYMENT_STATE_DONE);
				//**************更新借款对象状态***************
			this.psService.updateByPrimaryKey(ps);
				//设置相应还款明细付款时间
			this.psdService.updateState(ps.getId(),new Date());
			//3.对于最后一期还款的处理
				//判断
			PaymentScheduleQueryObject qo = new PaymentScheduleQueryObject();
			qo.setBorrowUserId(ps.getBorrowUser().getId());
			qo.setPageSize(0);
			List<PaymentSchedule> pss = this.psService.ListByQo(qo);
			boolean b = false;
			for (PaymentSchedule ps2 : pss) {
				if(ps2.getState()==BidConst.PAYMENT_STATE_NORMAL || ps2.getState()==BidConst.PAYMENT_STATE_OVERDUE){
					b = true;
				}
			}
			//如果是最后一次还款
			if(!b){
				BidRequest br = this.selectByPrimaryKey(ps.getBidRequestId());
				br.setBidRequestState(BidConst.BIDREQUEST_STATE_COMPLETE_PAY_BACK);
				this.updateByPrimaryKey(br);
				this.bidMapper.updateState(br.getId(), BidConst.BIDREQUEST_STATE_COMPLETE_PAY_BACK);
			}
		}
	}
}
