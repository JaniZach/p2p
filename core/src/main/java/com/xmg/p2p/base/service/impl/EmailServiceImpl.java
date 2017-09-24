package com.xmg.p2p.base.service.impl;

import java.util.Date;
import java.util.Properties;
import java.util.UUID;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.xmg.p2p.base.domain.EmailVerify;
import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.service.IEmailService;
import com.xmg.p2p.base.service.IEmailVerifyService;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.util.UserContext;

@Service
public class EmailServiceImpl implements IEmailService {
	@Value("${email.applicationURL}")
	private String emailApplicationURL;
	@Value("${email.serverhost}")
	private String emailServerHost;
	@Value("${email.localemailaddress}")
	private String localEmailAddress;
	@Value("${email.subject}")
	private String emailSubject;
	@Value("${email.username}")
	private String emailUsername;
	@Value("${email.password}")
	private String emailPassword;
	@Autowired
	private IEmailVerifyService emailVerifyService;
	@Autowired
	private IUserinfoService userinfoService;

	@Override
	public void sendEmail(String email) {
		//1.判断用户是否进行了邮箱认证如果已认证,抛出异常
		Userinfo userinfo = userinfoService.getCurrent();
		if(userinfo.getHasBindEmail()){
			throw new RuntimeException("该用户已完成邮箱认证,请勿重复认证!");
		}
		//1.发送邮件
		String uuid = UUID.randomUUID().toString();
		StringBuilder sb = new StringBuilder(100);
		sb.append("你好!欢迎使用邮箱 ").append(email).append(" 绑定p2p平台,点击超链接<a href='")
		.append(this.emailApplicationURL).append("?key=").append(uuid).append("'>点击绑定</a>进行邮箱认证.有效期3天.");
		//邮件发送-----------------------------------
		System.out.println("***********************************");
		System.out.println(sb);
		try {
			this.sendRealEmai(email, sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("发送邮件失败");
		}
		System.out.println("***********************************");
		//2.创建一个EmailVerify对象,封装用户id,邮箱,uuid
		EmailVerify emailVerify = new EmailVerify();
		emailVerify.setEmail(email);
		emailVerify.setUserId(UserContext.getCurrent().getId());
		emailVerify.setUuid(uuid);
		emailVerify.setSendTime(new Date());
		emailVerifyService.insert(emailVerify);
	}

	@Override
	public void sendRealEmai(String toEmail, String content) throws Exception {
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		//设定mail server
		sender.setHost(this.emailServerHost);
		//创建html邮件
		MimeMessage message = sender.createMimeMessage();
		//创建处理html邮件的工具类(注意设置编码格式为utf-8)
		MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
		//设置收件人,寄件人,邮件主题
		helper.setTo(toEmail);
		helper.setFrom(this.localEmailAddress);
		helper.setSubject(this.emailSubject);
		//启动html邮箱
		helper.setText(content, true);
		//设置用户,密码
		sender.setUsername(this.emailUsername);
		sender.setPassword(this.emailPassword);
		//其他参数设置
		Properties p = new Properties();
		p.put("mail.smtp.auth","true");
		p.put("mail.smtp.timeout","25000");
		sender.setJavaMailProperties(p);
		//发送邮件
		sender.send(message);
	}
}
