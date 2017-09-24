package com.xmg.p2p.base.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Jani 用户注册信息的实体类
 */
@Setter
@Getter
public class Logininfo  extends BaseDomain{
	public static final int STATE_NORMAL = 0;// 正常状态
	public static final int STATE_LOCK = 1;// 异常状态
	public static final int USERTYPE_USER = 0;// 普通用户
	public static final int USERTYPE_MANAGER = 1;// 管理员

	private String username;// 账号
	private String password;// 密码
	private int state;// 状态
	// 增加登录用户类型的属性
	private int userType;
}
