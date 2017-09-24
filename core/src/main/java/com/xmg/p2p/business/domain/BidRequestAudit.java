package com.xmg.p2p.business.domain;

import com.xmg.p2p.base.domain.BaseAuditDomain;

import lombok.Getter;
import lombok.Setter;

/**
 * 标的审核对象
 * @author Jani
 */
@Setter@Getter
public class BidRequestAudit extends BaseAuditDomain {
	public static final int PUBLISH_AUDIT = 0;
	public static final int FULL_AUDIT_1 = 1;
	public static final int FULL_AUDIT_2 = 2;
	private Long bidRequestId;//对应的标的id
	private int auditType;//审核类型
	public String getAuditTypeDisplay(){
		switch(this.auditType){
		case PUBLISH_AUDIT :return "发标前审核";
		case FULL_AUDIT_1 :return "满标一审";
		case FULL_AUDIT_2 :return "满标二审";
		default:return "";
		}
	}
}
