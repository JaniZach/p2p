package com.xmg.p2p.business.query;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.xmg.p2p.base.query.QueryObject;
import com.xmg.p2p.base.util.DateUtil;

import freemarker.template.utility.StringUtil;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RechargeOfflineQueryObject extends QueryObject {
	private int state = -1;// 线下充值审核状态
	private Long bankinfoId = -1L;// 开户行
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date beginDate;//交易起始日期
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date endDate;//交易结束日期
	private String tradeCode;//交易号
	public Date getBeginDate(){
		return DateUtil.getBeginDate(beginDate);
	}
	public Date getEndDate(){
		return DateUtil.getEndDate(endDate);
	}
	public String getTradeCode(){
		return StringUtil.emptyToNull(this.tradeCode);
	}
}
