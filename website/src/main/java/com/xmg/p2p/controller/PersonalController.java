package com.xmg.p2p.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xmg.p2p.base.service.IAccountService;
import com.xmg.p2p.base.service.IEmailService;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.util.AjaxResult;
import com.xmg.p2p.util.RequiredLogin;

/**
 * 前台用户的个人页面管理
 * @author Jani
 */
@Controller
public class PersonalController {
	@Autowired
	private IAccountService accountService;
	@Autowired
	private IUserinfoService userinfoService;
	@Autowired
	private IEmailService emailService;
	@RequestMapping("personal")
	@RequiredLogin//需要登陆控制
	public String personal(Model model){
		//logininfo的id和account中的id是相同的
		model.addAttribute("account",accountService.getCurrent());
		model.addAttribute("userinfo",userinfoService.getCurrent());
		return "personal";
	}
	@RequestMapping("bindPhone")
	@ResponseBody
	public AjaxResult bindPhone(String phoneNumber,String verifyCode){
		AjaxResult result = null;
		try{
			userinfoService.bindPhone(phoneNumber,verifyCode);
			result = new AjaxResult(true,"手机绑定成功");
		}catch(Exception e){
			e.printStackTrace();
			result = new AjaxResult(e.getMessage());
		}
		return result;
	}
	@RequestMapping("sendEmail")
	@ResponseBody
	public AjaxResult sendEmail(String email){
		AjaxResult result = null;
		try{
			emailService.sendEmail(email);
			result = new AjaxResult(true,"邮箱发送成功!");
		}catch(Exception e){
			result = new AjaxResult(e.getMessage());
		}
		return result;
	}
	@RequestMapping("bindEmail")
	public String bindEmail(String key,Model model){
		try{
			userinfoService.bindEmail(key);
			model.addAttribute("success", true);
		}catch(Exception e){
			model.addAttribute("success", false);
			model.addAttribute("msg",e.getMessage());
		}
		return "checkmail_result";
	}
}
