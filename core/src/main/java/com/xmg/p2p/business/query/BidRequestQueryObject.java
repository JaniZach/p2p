package com.xmg.p2p.business.query;

import com.xmg.p2p.base.query.QueryObject;

import lombok.Getter;
import lombok.Setter;

@Setter@Getter
public class BidRequestQueryObject extends QueryObject {
	//根据标的状态(发标前,满标一审,满标二审)
	private int bidRequestState = -1;
	//首页查询相关
	private int[] bidRequestStates;//借款标的状态集
	private String orderBy;//根据哪一列排序
	private String orderType;//排序规则
}
