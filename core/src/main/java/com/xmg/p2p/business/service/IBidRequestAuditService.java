package com.xmg.p2p.business.service;
import java.util.List;

import com.xmg.p2p.base.page.PageResult;
import com.xmg.p2p.base.query.QueryObject;
import com.xmg.p2p.business.domain.BidRequestAudit;

public interface IBidRequestAuditService {
	PageResult queryPage(QueryObject qo);

	/**
	 * 后台发标前的审核方法
	 * @param id 关联的借款标
	 * @param remark 审核备注
	 * @param state 审核状态
	 */
	void publishAudit(Long bidRequestId, String remark, int state);

	/**
	 * 根据 借款标 的id查询相关的审核记录
	 * @param bidRequestId 借款标的id
	 * @return 相关的审核记录
	 */
	List<BidRequestAudit> listByBidRequestId(Long bidRequestId);

	/**
	 * 后台满标一审的方法
	 * @param rId 借款标id
	 * @param remark 审核备注
	 * @param state 借款标审核后状态
	 */
	void fullAudit1(Long rId, String remark, int state);

	/**
	 * 后台满标二审的方法
	 * @param rId
	 * @param remark
	 * @param state
	 */
	void fullAudit2(Long rId, String remark, int state);
}
