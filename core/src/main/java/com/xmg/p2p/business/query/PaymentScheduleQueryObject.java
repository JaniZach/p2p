package com.xmg.p2p.business.query;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.xmg.p2p.base.query.QueryObject;
import com.xmg.p2p.base.util.DateUtil;

import lombok.Getter;
import lombok.Setter;

@Setter@Getter
public class PaymentScheduleQueryObject extends QueryObject {
	private Long borrowUserId = -1L;//借款人
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date beginDate;//大于某个还款截至日期
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date endDate;//小于某个还款截至日期
	public Date getBeginDate(){
		return DateUtil.getBeginDate(beginDate);
	}
	public Date getEndDate(){
		return DateUtil.getEndDate(endDate);
	}
	private int state = -1;
}
