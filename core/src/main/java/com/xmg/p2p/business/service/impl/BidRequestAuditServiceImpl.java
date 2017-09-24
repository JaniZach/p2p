package com.xmg.p2p.business.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
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
import com.xmg.p2p.business.domain.BidRequestAudit;
import com.xmg.p2p.business.domain.PaymentSchedule;
import com.xmg.p2p.business.domain.PaymentScheduleDetail;
import com.xmg.p2p.business.mapper.BidMapper;
import com.xmg.p2p.business.mapper.BidRequestAuditMapper;
import com.xmg.p2p.business.service.IAccountFlowService;
import com.xmg.p2p.business.service.IBidRequestAuditService;
import com.xmg.p2p.business.service.IBidRequestService;
import com.xmg.p2p.business.service.IPaymentScheduleDetailService;
import com.xmg.p2p.business.service.IPaymentScheduleService;
import com.xmg.p2p.business.service.ISystemAccountService;
import com.xmg.p2p.business.util.CalculatetUtil;

@Service
public class BidRequestAuditServiceImpl implements IBidRequestAuditService {
	@Autowired
	private BidRequestAuditMapper bidRequestAuditMapper;
	@Autowired
	private IBidRequestService bidRequestService;
	@Autowired
	private IUserinfoService userinfoService;
	@Autowired
	private BidMapper bidMapper;
	@Autowired
	private IAccountService accountService;
	@Autowired
	private IAccountFlowService flowService;
	@Autowired
	private ISystemAccountService systemAccountService;
	@Autowired
	private IPaymentScheduleService paymentScheduleService;
	@Autowired
	private IPaymentScheduleDetailService psDetailService;

	@Override
	public PageResult queryPage(QueryObject qo) {
		int count = bidRequestAuditMapper.queryPageCount(qo);
		if (count <= 0) {
			return PageResult.empty(qo.getPageSize());
		}
		List<BidRequestAudit> result = bidRequestAuditMapper.queryPageData(qo);
		PageResult pageResult = new PageResult(result, count, qo.getCurrentPage(), qo.getPageSize());
		return pageResult;
	}

	@Override
	public List<BidRequestAudit> listByBidRequestId(Long bidRequestId) {
		return this.bidRequestAuditMapper.listByBidRequestId(bidRequestId);
	}

	@Override
	public void publishAudit(Long bidRequestId, String remark, int state) {
		BidRequest br = this.bidRequestService.selectByPrimaryKey(bidRequestId);
		// 对于 待审核 状态的借款标才进行审核
		if (br != null && br.getBidRequestState() == BidConst.BIDREQUEST_STATE_PUBLISH_PENDING) {
			BidRequestAudit audit = new BidRequestAudit();
			audit.setBidRequestId(bidRequestId);
			audit.setApplier(br.getCreateUser());
			audit.setApplyTime(br.getApplyTime());
			audit.setAuditor(UserContext.getCurrent());
			audit.setAuditTime(new Date());
			audit.setAuditType(BidRequestAudit.PUBLISH_AUDIT);// 设置审核类型,此时是发标前审核
			audit.setRemark(remark);
			audit.setState(state);
			this.bidRequestAuditMapper.insert(audit);// 保存审核记录
			if (state == BidRequestAudit.STATE_PASS) {
				// 借款标的审核通过时,应修改标的状态
				br.setBidRequestState(BidConst.BIDREQUEST_STATE_BIDDING);// 设置标状态
																			// 招标中
				br.setPublishTime(new Date());// 设置发布时间
				br.setDisableDate(DateUtils.addDays(new Date(), br.getDisableDays()));// 设置招标到期时间
				br.setNote(remark);// 设置风控评审意见
			} else {
				br.setBidRequestState(BidConst.BIDREQUEST_STATE_PUBLISH_REFUSE);// 设置借款标的状态为发标前拒绝
				Userinfo userinfo = this.userinfoService.selectByPrimaryKey(br.getCreateUser().getId());
				userinfo.setBitState(
						BitStatesUtils.removeState(userinfo.getBitState(), BitStatesUtils.OP_HAS_BIDREQUEST_PROCESS));// 移除当前用户状态码,改为没有借款在流程中
				this.userinfoService.updateByPrimaryKey(userinfo);
			}
			// 更新借款标
			this.bidRequestService.updateByPrimaryKey(br);
			Collections.reverse(new ArrayList<Object>());
		}
	}

	@Override
	public void fullAudit1(Long rId, String remark, int state) {
		// 1.判断
		BidRequest br = this.bidRequestService.selectByPrimaryKey(rId);
		if (br != null && br.getBidRequestState() == BidConst.BIDREQUEST_STATE_APPROVE_PENDING_1) {
			// 2.满标一审操作
			// 创建审核对象
			BidRequestAudit audit = new BidRequestAudit();
			audit.setApplier(br.getCreateUser());
			audit.setApplyTime(br.getPublishTime());// 设置申请时间:此处应为发布前审核通过时间
			audit.setAuditor(UserContext.getCurrent());
			audit.setAuditTime(new Date());
			audit.setAuditType(BidRequestAudit.FULL_AUDIT_1);
			audit.setBidRequestId(rId);
			audit.setRemark(remark);
			audit.setState(state);
			this.bidRequestAuditMapper.insert(audit);// 保存审核对象
			if (state == BidRequestAudit.STATE_PASS) {
				// 3.审核通过
				// 进入满标二审状态
				br.setBidRequestState(BidConst.BIDREQUEST_STATE_APPROVE_PENDING_2);
				this.bidMapper.updateState(rId, BidConst.BIDREQUEST_STATE_APPROVE_PENDING_2);
			} else {
				// 4.审核不通过
				// 进入满标审核被拒绝状态
				br.setBidRequestState(BidConst.BIDREQUEST_STATE_REJECTED);
				this.bidMapper.updateState(rId, BidConst.BIDREQUEST_STATE_REJECTED);
				for (Bid b : br.getBids()) {
					// 投资人账户资金修改
					Account bidAccount = this.accountService.selectByPrimaryKey(b.getBidUser().getId());
					bidAccount.setUsableAmount(bidAccount.getUsableAmount().add(b.getAvailableAmount()));
					bidAccount.setFreezedAmount(bidAccount.getFreezedAmount().subtract(b.getAvailableAmount()));
					this.accountService.updateByPrimaryKey(bidAccount);// 更新投资人账户信息
					//生成一条投资失败流水
					this.flowService.createBidFailureFlow(bidAccount, b);
				}
				// 移除用户有一个借款在流程中的状态码
				Userinfo applierinfo = this.userinfoService.selectByPrimaryKey(br.getCreateUser().getId());
				applierinfo.removeState(BitStatesUtils.OP_HAS_BIDREQUEST_PROCESS);
				this.userinfoService.updateByPrimaryKey(applierinfo);// 更新借款用户信息
			}
			this.bidRequestService.updateByPrimaryKey(br);// 更新借款标信息
		}
	}

	@Override
	public void fullAudit2(Long rId, String remark, int state) {
		// 1.判断
		BidRequest br = this.bidRequestService.selectByPrimaryKey(rId);
		if (br != null && br.getBidRequestState() == BidConst.BIDREQUEST_STATE_APPROVE_PENDING_2) {
			// 2.满标二审操作
			// 创建审核对象
			BidRequestAudit audit = new BidRequestAudit();
			audit.setApplier(br.getCreateUser());
			audit.setApplyTime(new Date());// 设置申请时间:此处应为满标一审的时间,暂设置为当前时间
			audit.setAuditor(UserContext.getCurrent());
			audit.setAuditTime(new Date());
			audit.setAuditType(BidRequestAudit.FULL_AUDIT_2);
			audit.setBidRequestId(rId);
			audit.setRemark(remark);
			audit.setState(state);
			this.bidRequestAuditMapper.insert(audit);// 保存审核对象
			if(state==BidRequestAudit.STATE_PASS){
				// 3.审核通过
				br.setBidRequestState(BidConst.BIDREQUEST_STATE_PAYING_BACK);//设置借款标状态:还款中
				this.bidMapper.updateState(rId, BidConst.BIDREQUEST_STATE_PAYING_BACK);//设置投标标的状态:还款中
					//3.1针对借款人(无论审核成功还是失败,状态码都会改变)
				Account borrowAccount = this.accountService.selectByPrimaryKey(br.getCreateUser().getId());
						//收款(借款人账户)
				borrowAccount.setUsableAmount(borrowAccount.getUsableAmount().add(br.getBidRequestAmount()));
						//信用额度减少(借款人账户)
				borrowAccount.setRemainBorrowLimit(borrowAccount.getBorrowLimit().subtract(br.getBidRequestAmount()));
						//待还金额增加(本金+利息)
				borrowAccount.setUnReturnAmount(borrowAccount.getUnReturnAmount().add(br.getBidRequestAmount().add(br.getTotalRewardAmount())));
						//----生成用户收款流水-------
				this.flowService.createBorrowSuccess(br,borrowAccount);
						//用户支付平台借款手续费
				BigDecimal borrowFee = CalculatetUtil.calAccountManagementCharge(br.getBidRequestAmount());
				borrowAccount.setUsableAmount(borrowAccount.getUsableAmount().subtract(borrowFee));
						//生成用户支付借款管理费的流水记录
				this.flowService.createPayBorrowManageFee(br, borrowFee, borrowAccount);
						//平台收款+平台账户生成流水记录
				this.systemAccountService.receiveBorrowFee(br,borrowFee);
				this.accountService.updateByPrimaryKey(borrowAccount);//更新借款用户账户信息
					//3.2针对投资人
				for (Bid b : br.getBids()) {
					Account bidAccount = this.accountService.selectByPrimaryKey(b.getBidUser().getId());
						//某个投资标应收到的利息
					/*BigDecimal bidInterest = b.getAvailableAmount()
							.divide(br.getBidRequestAmount(), BidConst.CAL_SCALE, RoundingMode.HALF_UP)
							.multiply(br.getTotalRewardAmount()
									.setScale(BidConst.CAL_SCALE, RoundingMode.HALF_UP));*/
						//冻结金额减少
					bidAccount.setFreezedAmount(bidAccount.getFreezedAmount().subtract(b.getAvailableAmount()));
						//待收本金增加,待收利息增加
					/*bidAccount.setUnReceivePrincipal(bidAccount.getUnReceivePrincipal().add(b.getAvailableAmount()));
					bidAccount.setUnReceiveInterest(bidAccount.getUnReceiveInterest().add(bidInterest));*/
						//更新用户信息
					this.accountService.updateByPrimaryKey(bidAccount);
				}
				//后期业务
				//生成还款对象和还款明细对象
				List<PaymentSchedule> pss = this.createReturnMoney(br);
				for (PaymentSchedule ps : pss) {
					for (PaymentScheduleDetail psd : ps.getPaymentScheduleDetails()) {
						Account bidAccount = this.accountService.selectByPrimaryKey(psd.getToLogininfoId());
						//待收本金增加,待收利息增加
						bidAccount.setUnReceiveInterest(bidAccount.getUnReceiveInterest().add(psd.getInterest()));
						bidAccount.setUnReceivePrincipal(bidAccount.getUnReceivePrincipal().add(psd.getPrincipal()));
						//这里不会出现乐观锁的问题:因为每次的bidAccount都是查询出来的,version都是最新的
						this.accountService.updateByPrimaryKey(bidAccount);
					}
				}
			}else{
				// 4.审核拒绝
				// 进入满标审核被拒绝状态
				br.setBidRequestState(BidConst.BIDREQUEST_STATE_REJECTED);
				this.bidMapper.updateState(rId, BidConst.BIDREQUEST_STATE_REJECTED);
				for (Bid b : br.getBids()) {
					// 投资人账户资金修改
					Account bidAccount = this.accountService.selectByPrimaryKey(b.getBidUser().getId());
					bidAccount.setUsableAmount(bidAccount.getUsableAmount().add(b.getAvailableAmount()));
					bidAccount.setFreezedAmount(bidAccount.getFreezedAmount().subtract(b.getAvailableAmount()));
					this.accountService.updateByPrimaryKey(bidAccount);// 更新投资人账户信息
					//生成一条投资失败流水
					this.flowService.createBidFailureFlow(bidAccount, b);
				}
			}
			// 满标二审结束后,无论审核通过,还是拒绝,都移除用户有一个借款在流程中的状态码
			Userinfo applierinfo = this.userinfoService.selectByPrimaryKey(br.getCreateUser().getId());
			applierinfo.removeState(BitStatesUtils.OP_HAS_BIDREQUEST_PROCESS);
			this.userinfoService.updateByPrimaryKey(applierinfo);// 更新借款用户信息
			this.bidRequestService.updateByPrimaryKey(br);// 更新借款标信息
		}
	}

	/**
	 * 生成还款对象和还款明细的方法
	 * @param br 借款标
	 * @return 生成的还款对象集合
	 */
	private List<PaymentSchedule> createReturnMoney(BidRequest br) {
		List<PaymentSchedule> pss = new ArrayList<>();
		//还款对象:数量还款期限决定
		BigDecimal totalInterest = BidConst.ZERO;
		BigDecimal totalReturnMoney = BidConst.ZERO;
		for (int i = 0; i < br.getMonthes2Return(); i++) {
			PaymentSchedule ps = new PaymentSchedule();//还款对象
			ps.setBidRequestId(br.getId());
			ps.setBidRequestTitle(br.getTitle());
			ps.setBidRequestType(br.getBidRequestType());
			ps.setBorrowUser(br.getCreateUser());
			ps.setDeadLine(DateUtils.addMonths(new Date(), i+1));
			ps.setMonthIndex(i+1);
			ps.setReturnType(br.getReturnType());
			ps.setState(BidConst.PAYMENT_STATE_NORMAL);
			if(i < br.getMonthes2Return()-1){
				//计算每期利息
				BigDecimal interest = CalculatetUtil.calMonthlyInterest(br.getReturnType(), 
						br.getBidRequestAmount(), br.getCurrentRate(), i+1, br.getMonthes2Return());
				//计算每期还款金额
				BigDecimal returnMoney = CalculatetUtil.calMonthToReturnMoney(br.getReturnType(), 
						br.getBidRequestAmount(), br.getCurrentRate(), i+1, br.getMonthes2Return());
				ps.setTotalAmount(returnMoney);
				ps.setInterest(interest);
				ps.setPrincipal(returnMoney.subtract(interest));
				//累加还款利息,累加还款总金额
				totalInterest = totalInterest.add(interest);
				totalReturnMoney = totalReturnMoney.add(returnMoney);
			}else{
				//最后一期还款进行找平
				ps.setTotalAmount(br.getBidRequestAmount().add(br.getTotalRewardAmount()).subtract(totalReturnMoney));
				ps.setInterest(br.getTotalRewardAmount().subtract(totalInterest));
				ps.setPrincipal(ps.getTotalAmount().subtract(ps.getInterest()));
			}
			this.paymentScheduleService.insert(ps);//保存一条还款记录
			//创建还款明细单
			BigDecimal totalBidInterest = BidConst.ZERO;
			BigDecimal totalBidReturnMoney = BidConst.ZERO;
			for (int j = 0; j < br.getBids().size(); j++) {
				PaymentScheduleDetail detail = new PaymentScheduleDetail();//还款明细
				Bid bid = br.getBids().get(j);
				detail.setBidAmount(bid.getAvailableAmount());
				detail.setBidId(bid.getId());
				detail.setBidRequestId(br.getId());
				detail.setBidRequestTitle(br.getTitle());
				detail.setDeadline(ps.getDeadLine());
				detail.setFromLogininfo(br.getCreateUser());
				detail.setMonthIndex(ps.getMonthIndex());
				detail.setPaymentScheduleId(ps.getId());//必须要先保存还款对象,才可以自动生成id
				detail.setReturnType(br.getReturnType());
				detail.setToLogininfoId(bid.getBidUser().getId());
				if(j < br.getBids().size()-1){
					//获取收款利息
					BigDecimal bidInterest = bid.getAvailableAmount().divide(br.getBidRequestAmount(), 
							BidConst.CAL_SCALE, RoundingMode.HALF_UP).multiply(ps.getInterest())
							.setScale(BidConst.STORE_SCALE, RoundingMode.HALF_UP);
					BigDecimal bidPrincipal = bid.getAvailableAmount().divide(br.getBidRequestAmount(), 
							BidConst.CAL_SCALE, RoundingMode.HALF_UP).multiply(ps.getPrincipal())
							.setScale(BidConst.STORE_SCALE, RoundingMode.HALF_UP);
					detail.setInterest(bidInterest);
					detail.setPrincipal(bidPrincipal);
					detail.setTotalAmount(detail.getInterest().add(detail.getPrincipal()));
					//累加收款利息,累加收款总金额
					totalBidInterest = totalBidInterest.add(bidInterest);
					totalBidReturnMoney = totalBidReturnMoney.add(detail.getInterest().add(detail.getPrincipal()));
				}else{
					//收款对象最后一个找平
					BigDecimal bidInterest = ps.getInterest().subtract(totalBidInterest);
					//获取收款总金额
					BigDecimal bidReturnMoney = ps.getTotalAmount().subtract(totalBidReturnMoney);
					detail.setTotalAmount(bidReturnMoney);
					detail.setInterest(bidInterest);
					detail.setPrincipal(bidReturnMoney.subtract(bidInterest));
				}
				this.psDetailService.insert(detail);//生成一条还款明细记录
				ps.getPaymentScheduleDetails().add(detail);//添加到还款对象中的明细集合中
			}
			pss.add(ps);//添加到还款对象集合中
		}
		return pss;
	}
}
