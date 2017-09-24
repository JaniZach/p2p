package com.xmg.p2p.business.mapper;

import java.util.List;

import com.xmg.p2p.base.query.QueryObject;
import com.xmg.p2p.business.domain.BidRequest;

/**
 * 借款申请相关的mapper
 * @author Jani
 */
public interface BidRequestMapper {
    int insert(BidRequest record);
    BidRequest selectByPrimaryKey(Long id);
    int updateByPrimaryKey(BidRequest record);
	Long queryPageCount(QueryObject qo);
	List<BidRequest> queryPageData(QueryObject qo);
	/**
	 * 根据用户id,查询其所拥有的全部标信息
	 * @param userId
	 * @return
	 */
	List<BidRequest> listByUserId(Long userId);
}