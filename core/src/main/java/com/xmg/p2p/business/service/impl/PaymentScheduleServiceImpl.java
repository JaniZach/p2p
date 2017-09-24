package com.xmg.p2p.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xmg.p2p.base.page.PageResult;
import com.xmg.p2p.base.query.QueryObject;
import com.xmg.p2p.business.domain.PaymentSchedule;
import com.xmg.p2p.business.mapper.PaymentScheduleMapper;
import com.xmg.p2p.business.query.PaymentScheduleQueryObject;
import com.xmg.p2p.business.service.IPaymentScheduleService;

@Service
public class PaymentScheduleServiceImpl implements IPaymentScheduleService {
	@Autowired
	private PaymentScheduleMapper paymentScheduleMapper;

	@Override
	public int insert(PaymentSchedule record) {
		return paymentScheduleMapper.insert(record);
	}

	@Override
	public int updateByPrimaryKey(PaymentSchedule record) {
		return paymentScheduleMapper.updateByPrimaryKey(record);
	}

	@Override
	public PaymentSchedule selectByPrimaryKey(Long id) {
		return this.paymentScheduleMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<PaymentSchedule> ListByQo(PaymentScheduleQueryObject qo) {
		return this.paymentScheduleMapper.queryPageData(qo);
	}

	@Override
	public PageResult queryPage(QueryObject qo) {
		int count = paymentScheduleMapper.queryPageCount(qo);
		if (count <= 0) {
			return PageResult.empty(qo.getPageSize());
		}
		List<PaymentSchedule> result = paymentScheduleMapper.queryPageData(qo);
		PageResult pageResult = new PageResult(result, count, qo.getCurrentPage(), qo.getPageSize());
		return pageResult;
	}
}
