package com.xmg.p2p.business.service;

import java.util.List;

import com.xmg.p2p.base.page.PageResult;
import com.xmg.p2p.base.query.QueryObject;
import com.xmg.p2p.business.domain.PaymentSchedule;
import com.xmg.p2p.business.query.PaymentScheduleQueryObject;

public interface IPaymentScheduleService {
	int insert(PaymentSchedule record);

	int updateByPrimaryKey(PaymentSchedule record);
	
	PaymentSchedule selectByPrimaryKey(Long id);

	PageResult queryPage(QueryObject qo);

	/**
	 * 根据高级查询对象查询还款对象集合
	 * @param qo
	 * @return
	 */
	List<PaymentSchedule> ListByQo(PaymentScheduleQueryObject qo);
}
