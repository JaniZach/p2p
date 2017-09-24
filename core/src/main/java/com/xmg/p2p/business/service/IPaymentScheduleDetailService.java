package com.xmg.p2p.business.service;

import java.util.Date;

import com.xmg.p2p.base.page.PageResult;
import com.xmg.p2p.base.query.QueryObject;
import com.xmg.p2p.business.domain.PaymentScheduleDetail;

public interface IPaymentScheduleDetailService {
	int insert(PaymentScheduleDetail record);

	int updateByPrimaryKey(PaymentScheduleDetail record);

	PageResult queryPage(QueryObject qo);

	/**
	 * 根据还款对象id,修改相应还款明细状态
	 * @param id
	 * @param date
	 */
	void updateState(Long id, Date date);
}
