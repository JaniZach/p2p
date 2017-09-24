package com.xmg.p2p.base.service;

/**
 * 验证邮箱,绑定邮箱的接口
 * @author Jani
 */
public interface IEmailService {
	/**
	 * 模拟发送邮件的方法
	 * @param email
	 */
	void sendEmail(String email);
	/**
	 * 真实发送邮件的方法
	 * @param toEmail 收件人
	 * @param content 发送内容
	 * @throws Exception 
	 */
	void sendRealEmai(String toEmail,String content) throws Exception;
}
