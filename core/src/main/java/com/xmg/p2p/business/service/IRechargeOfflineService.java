package com.xmg.p2p.business.service;
import com.xmg.p2p.base.page.PageResult;
import com.xmg.p2p.base.query.QueryObject;
import com.xmg.p2p.business.domain.RechargeOffline;

public interface IRechargeOfflineService {
    RechargeOffline selectByPrimaryKey(Long id);
	PageResult queryPage(QueryObject qo);
	/**
	 * 提交充值记录的方法
	 * @param ro
	 */
	void apply(RechargeOffline ro);
	/**
	 * 后台线下充值单审核的方法
	 * @param id 充值单id
	 * @param remark 审核备注
	 * @param state 充值单状态
	 */
	void audit(Long rId, String remark, int state);
}
