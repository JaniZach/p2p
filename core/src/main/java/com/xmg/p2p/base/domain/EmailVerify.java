package com.xmg.p2p.base.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 封装绑定邮箱相关的数据
 * @author Jani
 */
@Setter@Getter
public class EmailVerify extends BaseDomain{
	private Long userId;//用户ID
	private String email;//邮箱
	private String uuid;//邮箱验证的key
	private Date sendTime;//验证的邮件发送时间
}
