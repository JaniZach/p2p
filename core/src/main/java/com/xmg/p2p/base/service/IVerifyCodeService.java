package com.xmg.p2p.base.service;

public interface IVerifyCodeService {
	/**
	 * 为指定手发送验证码的方法
	 * @param phoneNumber 验证的手机
	 */
	void sendVerifyCode(String phoneNumber);

	/**
	 * 验证手机的方法
	 * @param phoneNumber 待绑定的手机
	 * @param verifyCode 手机验证码
	 */
	boolean verify(String phoneNumber, String verifyCode);

	/**
	 * 真实发送验证码的方法
	 * @param content
	 * @param phoneNumber 
	 */
	void sendRealVerifyCode(String phoneNumber, String content);
}
