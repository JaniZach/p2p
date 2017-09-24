package com.xmg.p2p.business.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.xmg.p2p.base.domain.BaseDomain;

import lombok.Getter;
import lombok.Setter;

/**
 * 平台账户流水的实体类
 * @author Jani
 */
@Setter@Getter
public class SystemAccountFlow extends BaseDomain {
	private Date actionTime;//流水操作时间
	private int actionType;//流水类型
	private BigDecimal amount;//流水数量
	private BigDecimal usableAmount;//流水后可用金额
	private BigDecimal freezedAmount;//流水后冻结金额
	private String note;//说明
}
