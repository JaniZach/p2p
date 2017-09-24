package com.xmg.p2p.business.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.xmg.p2p.base.domain.BaseDomain;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户账户流水的实体类
 * @author Jani
 */
@Setter@Getter
public class AccountFlow extends BaseDomain {
	private Long accountId;//相关的账户id
	private Date actionTime;//账户流水发生的时间
	private BigDecimal amount;//发生改变的数量
	private int actionType;//账户流水的类型
	private BigDecimal usableAmount;//流水后账户可用金额
	private BigDecimal freezedAmount;//流水后账户冻结金额
	private String note;//备注
}