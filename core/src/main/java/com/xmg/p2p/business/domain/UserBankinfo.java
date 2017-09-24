package com.xmg.p2p.business.domain;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.xmg.p2p.base.domain.BaseDomain;

import lombok.Getter;
import lombok.Setter;

/*
 * 用户银行卡的实体类
 */
@Setter@Getter
public class UserBankinfo extends BaseDomain {
	private Long logininfoId;//用户id
	private String bankName;//银行名称
	private String accountName;//开户人
	private String accountNumber;//开户账号
	private String forkName;//支行
	public String getJsonString(){
		Map<String, Object> json = new HashMap<>();
		json.put("id", id);
		json.put("bankName",this.bankName);
		json.put("accountName", this.accountName);
		json.put("accountNumber",this.accountNumber);
		json.put("forkName", this.forkName);
		return JSON.toJSONString(json);
	}
}
