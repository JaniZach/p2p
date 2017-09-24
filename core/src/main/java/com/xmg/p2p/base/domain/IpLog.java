package com.xmg.p2p.base.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 登录日志相关的实体
 * @author Jani
 */
@Setter@Getter
public class IpLog  extends BaseDomain{
	public static int LOGIN_SUCCESS = 0;//登陆成功状态
	public static int LOGIN_FAILURE = 1;//登陆失败状态
	public static int USERTYPE_USER = 0;//普通用户
	public static int USERTYPE_MANAGER = 1;//管理员
	private String username;//登陆账号
	private String ip;//IP地址
	private Date loginTime;//登陆时间
	private int state;//登陆状态
	//增加登陆用户的属性
	private int userType;
	//登陆用户登陆成功与否的显示
	public String getDisplayState(){
		return this.state==IpLog.LOGIN_SUCCESS?"<font color='green'>登陆成功</font>":"<font color='red'>登陆失败</font>";
	}
	//登陆用户属性的页面显示
	public String getDisplayUserType(){
		return this.userType==IpLog.USERTYPE_USER?"前台用户":"后台管理员";
	}
}
