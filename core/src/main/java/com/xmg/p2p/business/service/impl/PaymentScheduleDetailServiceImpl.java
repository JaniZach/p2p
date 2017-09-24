package com.xmg.p2p.business.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xmg.p2p.base.page.PageResult;
import com.xmg.p2p.base.query.QueryObject;
import com.xmg.p2p.business.domain.PaymentScheduleDetail;
import com.xmg.p2p.business.mapper.PaymentScheduleDetailMapper;
import com.xmg.p2p.business.service.IPaymentScheduleDetailService;

@Service
public class PaymentScheduleDetailServiceImpl implements IPaymentScheduleDetailService {
	@Autowired
	private PaymentScheduleDetailMapper paymentScheduleDetailMapper;

	@Override
	public int insert(PaymentScheduleDetail record) {
		return paymentScheduleDetailMapper.insert(record);
	}

	@Override
	public int updateByPrimaryKey(PaymentScheduleDetail record) {
		return paymentScheduleDetailMapper.updateByPrimaryKey(record);
	}
	

	@Override
	public void updateState(Long id,Date date) {
		this.paymentScheduleDetailMapper.updateState(id,date);
	}
	
	@Override
	public PageResult queryPage(QueryObject qo) {
		int count = paymentScheduleDetailMapper.queryPageCount(qo);
		if (count <= 0) {
			return PageResult.empty(qo.getPageSize());
		}
		List<PaymentScheduleDetail> result = paymentScheduleDetailMapper.queryPageData(qo);
		PageResult pageResult = new PageResult(result, count, qo.getCurrentPage(), qo.getPageSize());
		return pageResult;
	}
}
