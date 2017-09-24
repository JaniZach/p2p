package com.xmg.p2p.base.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 基本认证相关的domain
 * @author Jani
 */
@Setter@Getter
public class BaseAuditDomain extends BaseDomain {

	public static final int STATE_NORMAL = 0;//待审核
    public static final int STATE_PASS = 1;//审核通过
    public static final int STATE_REJECT = 2;//审核拒接
    
	protected int state;//审核状态
	protected Logininfo applier;//申请人
	protected Logininfo auditor;//审核人
	protected Date applyTime;//申请时间
	protected Date auditTime;//审核时间
	protected String remark;//备注
	
	public String getStateDisplay(){
    	switch(this.state){
    	case(STATE_NORMAL):return "待审核";
    	case(STATE_PASS):return "<font color='green'>审核通过</font>";
    	case(STATE_REJECT):return "<font color='red'>审核拒绝</font>";
    	default:return "";
    	}
    }
}
