package com.xmg.p2p.business.domain;

import java.math.BigDecimal;

import com.xmg.p2p.base.domain.BaseDomain;
import com.xmg.p2p.base.util.BidConst;

import lombok.Getter;
import lombok.Setter;

/**
 * 平台账户的实体类
 * @author Jani
 */
@Setter@Getter
public class SystemAccount extends BaseDomain {
	private int version;//乐观锁
	private BigDecimal usableAmount = BidConst.ZERO;//平台可用金额
	private BigDecimal freezedAmount = BidConst.ZERO;//平台冻结金额
}
