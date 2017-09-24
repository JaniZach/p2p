package com.xmg.p2p.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xmg.p2p.base.service.IVerifyCodeService;
import com.xmg.p2p.base.util.AjaxResult;

/**
 * 前台验证手机的控制器
 * @author Jani
 */
@Controller
public class VerifyCodeController {
	@Autowired
	private IVerifyCodeService verifyCodeService;
	@RequestMapping("sendVerifyCode")
	@ResponseBody
	public AjaxResult validate(String phoneNumber){
		AjaxResult result = null;
		try{
			verifyCodeService.sendVerifyCode(phoneNumber);
			result = new AjaxResult(true,"验证码发送成功!");
		}catch(Exception e){
			result = new AjaxResult(e.getMessage());
		}
		return result;
	}
	@RequestMapping("sendRealVerifyCode")
	@ResponseBody
	public String sendRealVerifyCode(String phoneNumber,String content){
		String ret = "发送手机号:"+phoneNumber+",内容:"+content;
		return ret;
	}
}