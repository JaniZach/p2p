package com.xmg.p2p.business.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xmg.p2p.base.domain.Account;
import com.xmg.p2p.base.page.PageResult;
import com.xmg.p2p.base.query.QueryObject;
import com.xmg.p2p.base.service.IAccountService;
import com.xmg.p2p.base.util.UserContext;
import com.xmg.p2p.business.domain.RechargeOffline;
import com.xmg.p2p.business.mapper.RechargeOfflineMapper;
import com.xmg.p2p.business.service.IRechargeOfflineService;
@Service
public class RechargeOfflineServiceImpl implements IRechargeOfflineService {
	@Autowired
	private RechargeOfflineMapper rechargeOfflineMapper;
	@Autowired
	private IAccountService accountService;
	
	public RechargeOffline selectByPrimaryKey(Long id) {
		return rechargeOfflineMapper.selectByPrimaryKey(id);
	}

	@Override
	public PageResult queryPage(QueryObject qo) {
		int count = rechargeOfflineMapper.queryPageCount(qo);
		if(count<=0){
			return PageResult.empty(qo.getPageSize());
		}
		List<RechargeOffline> result = rechargeOfflineMapper.queryPageData(qo);
		PageResult pageResult = new PageResult(result,count,qo.getCurrentPage(),qo.getPageSize());
		return pageResult;
	}

	@Override
	public void apply(RechargeOffline ro) {
		//ro只是用来接收值
		RechargeOffline r = new RechargeOffline();
		r.setAmount(ro.getAmount());
		r.setApplier(UserContext.getCurrent());
		r.setApplyTime(new Date());
		r.setBankinfo(ro.getBankinfo());
		r.setNote(ro.getNote());
		r.setState(RechargeOffline.STATE_NORMAL);
		r.setTradeCode(ro.getTradeCode());
		r.setTradeTime(ro.getTradeTime());
		this.rechargeOfflineMapper.insert(r);
	}

	@Override
	public void audit(Long rId, String remark, int state) {
		//判断
		RechargeOffline ro = this.rechargeOfflineMapper.selectByPrimaryKey(rId);
		if(ro!=null && ro.getState()==RechargeOffline.STATE_NORMAL){
			//操作
			ro.setRemark(remark);
			ro.setState(state);
			ro.setAuditor(UserContext.getCurrent());
			ro.setAuditTime(new Date());
			if(state==RechargeOffline.STATE_PASS){
				//审核通过
				Account account = this.accountService.selectByPrimaryKey(ro.getApplier().getId());
				//申请人账户可用余额增加
				account.setUsableAmount(account.getUsableAmount().add(ro.getAmount()));
				this.accountService.updateByPrimaryKey(account);
			}//审核拒绝无额外操作
			this.rechargeOfflineMapper.updateByPrimaryKey(ro);
		}
	}
}
