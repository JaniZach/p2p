package com.xmg.p2p.business.domain;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.JSON;
import com.xmg.p2p.base.domain.BaseAuditDomain;

import lombok.Getter;
import lombok.Setter;

/**
 * 线下充值审核的实体类
 * @author Jani
 */
@Setter@Getter
public class RechargeOffline extends BaseAuditDomain {
	private String tradeCode;//交易号
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date tradeTime;//充值时间
	private BigDecimal amount;//充值金额
	private String note;//说明
	private PlatformBankinfo bankinfo;//关联的平台账户信息
	public String getJsonString(){
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("id",this.id);
		json.put("username",this.getApplier().getUsername());
		json.put("tradeCode",this.tradeCode);
		json.put("amount",this.amount);
		//注意将时间转化为json类型,需要先格式化
		json.put("tradeTime", DateFormat.getDateInstance().format(this.tradeTime));
		return JSON.toJSONString(json);
	}
}
