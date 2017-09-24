package com.xmg.p2p.business.mapper;

import java.util.List;

import com.xmg.p2p.base.query.QueryObject;
import com.xmg.p2p.business.domain.BidRequestAudit;

/**
 * 借款标的审核对象
 * @author Jani
 */
public interface BidRequestAuditMapper {
    int insert(BidRequestAudit record);
    BidRequestAudit selectByPrimaryKey(Long id);
    int updateByPrimaryKey(BidRequestAudit record);
	int queryPageCount(QueryObject qo);
	List<BidRequestAudit> queryPageData(QueryObject qo);
	/**
	 * 根据 借款标 的id 查询相关的审核记录
	 * @param bidRequestId 借款标id
	 * @return
	 */
	List<BidRequestAudit> listByBidRequestId(Long bidRequestId);
}