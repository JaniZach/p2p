package com.xmg.p2p.business.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xmg.p2p.base.query.QueryObject;
import com.xmg.p2p.business.domain.PaymentScheduleDetail;

public interface PaymentScheduleDetailMapper {
    int insert(PaymentScheduleDetail record);
    PaymentScheduleDetail selectByPrimaryKey(Long id);
    int updateByPrimaryKey(PaymentScheduleDetail record);
	int queryPageCount(QueryObject qo);
	List<PaymentScheduleDetail> queryPageData(QueryObject qo);
	/**
	 * 根据还款对象id,修改相应的还款明细状态
	 * @param id
	 * @param date
	 */
	void updateState(@Param("id")Long id,@Param("date") Date date);
}