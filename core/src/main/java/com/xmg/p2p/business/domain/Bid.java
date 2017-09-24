package com.xmg.p2p.business.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.xmg.p2p.base.domain.BaseDomain;
import com.xmg.p2p.base.domain.Logininfo;

import lombok.Getter;
import lombok.Setter;

/**
 * 投标对象
 * @author Jani
 */
@Setter@Getter
public class Bid extends BaseDomain {
	private BigDecimal actualRate;//实际年利率
	private BigDecimal availableAmount;//投标有效金额(投标金额)
	private Long bidRequestId;//关联借款对象
	private String bidRequestTitle;//标的标题
	private Logininfo bidUser;//投标人
	private Date bidTime;//投标时间
	private int bidRequestState;//标的状态
}
