package com.xmg.p2p.base.service;

import java.util.List;
import java.util.Map;

import com.xmg.p2p.base.domain.Logininfo;

public interface ILogininfoService {
	/**
	 * 处理注册信息的方法
	 * @param username 用户名
	 * @param password 密码
	 * @return 注册信息
	 */
	public Logininfo register(String username,String password,int userType);

	/**
	 * 检查账号是否已经注册的方法
	 * @param username
	 * @return
	 */
	public boolean checkUsername(String username);

	/**
	 * 处理的登陆'的方法
	 * @param username 登陆账号
	 * @param password 登陆密码
	 * @return 
	 */
	public Logininfo login(String username, String password, String remoteAddr,int userType);

	/**
	 * 初始化管理员账号的方法
	 */
	public void initAdmin();

	/**
	 * 自动补全的方法
	 * @param keyword
	 * @return
	 */
	public List<Map<String, Object>> queryArray4Autocomplate(String keyword);
}
