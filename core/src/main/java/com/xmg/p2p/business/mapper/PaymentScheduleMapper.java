package com.xmg.p2p.business.mapper;

import java.util.List;

import com.xmg.p2p.base.query.QueryObject;
import com.xmg.p2p.business.domain.PaymentSchedule;

public interface PaymentScheduleMapper {
    int insert(PaymentSchedule record);
    PaymentSchedule selectByPrimaryKey(Long id);
    int updateByPrimaryKey(PaymentSchedule record);
	int queryPageCount(QueryObject qo);
	List<PaymentSchedule> queryPageData(QueryObject qo);
}