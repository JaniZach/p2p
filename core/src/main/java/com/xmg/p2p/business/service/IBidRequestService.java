package com.xmg.p2p.business.service;
import java.math.BigDecimal;
import java.util.List;

import com.xmg.p2p.base.page.PageResult;
import com.xmg.p2p.base.query.QueryObject;
import com.xmg.p2p.business.domain.BidRequest;

public interface IBidRequestService {
    int insert(BidRequest record);
    BidRequest selectByPrimaryKey(Long id);
    int updateByPrimaryKey(BidRequest record);
	PageResult queryPage(QueryObject qo);
	/**
	 * 前台借款信息提交处理的方法
	 * @param bidRequest
	 */
	void apply(BidRequest bidRequest);
	/**
	 * 根据用户id,查询其所拥有的全部借款标
	 * @param id 用户id
	 * @return
	 */
	List<BidRequest> listByUserId(Long userId);
	/**
	 * 首页查询出借款标相关信息
	 * @param i
	 * @return
	 */
	List<BidRequest> listIndex(int i);
	/**
	 * 前台处理投标的方法
	 * @param bidRequestId
	 * @param amount
	 */
	void invest(Long bidRequestId, BigDecimal amount);
	/**
	 * 前台进行还款的方法
	 * @param psId 还款对象的id
	 */
	void returnMoney(Long psId);
}
