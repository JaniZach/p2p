package com.xmg.p2p.base.service.impl;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import com.xmg.p2p.base.service.IVerifyCodeService;
import com.xmg.p2p.base.util.BidConst;
import com.xmg.p2p.base.util.DateUtil;
import com.xmg.p2p.base.util.UserContext;
import com.xmg.p2p.base.vo.VerifyCodeVO;

@Service
public class VerifyCodeServiceImpl implements IVerifyCodeService {
	@Value("${sms.verifyCodeURL}")
	private String verifyCodeURL;

	@Override
	public void sendVerifyCode(String phoneNumber) {
		// 1.如果没有发送过验证码,或验证时间在规定时间内,则保存验证信息到session中
		VerifyCodeVO vo = UserContext.getCurrentVerifyCodeVO();
		if (vo == null || DateUtil.getSecondsWith(new Date(), vo.getSentTime()) < BidConst.VERIFYCODE_VALIDATE_TIME) {
			// 1.1创建verifyCodeVO对象
			vo = new VerifyCodeVO();
			vo.setPhoneNumber(phoneNumber);
			vo.setSentTime(new Date());
			String verifyCode = UUID.randomUUID().toString().substring(0, 4);
			//=========================================================== 
			//模拟发送验证码
			StringBuilder content = new StringBuilder(100);
			content.append("你好!欢迎使用手机号码 ").append(phoneNumber).append(" 绑定p2p平台,验证码 ").append(verifyCode)
					.append(" ,90秒后失效,请及时输入.");
			System.out.println("**************************");
			System.out.println(content);
			System.out.println("**************************");
			//=========================================================== 
			vo.setVerifyCode(verifyCode);
			// 1.2将verifyCodeVO对象保存到session中
			UserContext.setCurrentVerifyCodeVO(vo);
		} else {
			// 2.否则,提示: 发送过于频繁,请稍后再试
			throw new RuntimeException("发送过于频繁,请稍后再试!");
		}

	}
	@Override
	public void sendRealVerifyCode(String phoneNumber,String content){
		try {
			// 定义需要访问的地址
			URL url = new URL(verifyCodeURL);
			// 获取HttpURLConnection
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// 设置需要接受相应回来的内容
			conn.setDoOutput(true);
			// 设置请求格式
			conn.setRequestMethod("POST");
			// 请求输出
			conn.getOutputStream().write(content.getBytes());
			// 按下回车发送请求出去
			conn.connect();
			// 获取相应页面结果
			String ret = StreamUtils.copyToString(conn.getInputStream(), Charset.forName("utf-8"));
			System.out.println("**************************");
			System.out.println(ret);
			System.out.println("**************************");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public boolean verify(String phoneNumber, String verifyCode) {
		// 1.如果没有发送验证码,验证失败
		VerifyCodeVO vo = UserContext.getCurrentVerifyCodeVO();
		if (vo == null) {
			return false;
		}
		// 2.手机号不符合,验证失败
		if (!vo.getPhoneNumber().equals(phoneNumber)) {
			return false;
		}
		// 3.验证码不符合,验证失败
		if (!vo.getVerifyCode().equals(verifyCode)) {
			return false;
		}
		// 4.验证超时,验证失败
		if (DateUtil.getSecondsWith(new Date(), vo.getSentTime()) > BidConst.VERIFYCODE_VALIDATE_TIME) {
			return false;
		}
		return true;
	}
}