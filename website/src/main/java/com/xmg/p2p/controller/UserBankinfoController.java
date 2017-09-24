package com.xmg.p2p.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.business.domain.UserBankinfo;
import com.xmg.p2p.business.service.IUserBankinfoService;

import lombok.Getter;
import lombok.Setter;

/*
 * 前台绑定银行卡相关的控制器
 */
@Setter@Getter
@Controller
public class UserBankinfoController {
	@Autowired
	private IUserinfoService userinfoService;
	@Autowired
	private IUserBankinfoService userBankInfoService;
	@RequestMapping("bankInfo")
	public String bankInfo(Model model){
		Userinfo userinfo = this.userinfoService.getCurrent();
		if(userinfo.getHasBindBank()){
		//如果已经绑定银行卡
			UserBankinfo bankinfo = this.userBankInfoService.getCurrent();
			model.addAttribute("bankinfo", bankinfo);
			return "bankInfo_result";
		}
		//如果没有绑定银行卡
		model.addAttribute("userinfo",userinfo);
		return "bankInfo";
	}
	@RequestMapping("bankInfo_save")
	public String bankInfoSave(String accountNumber,String bankName,String forkName){
		//成功后重定向到 /bankIndo.do页面
		this.userBankInfoService.bindBank(accountNumber,bankName,forkName);
//		return "redirect:/bankInfo.do";
		return "";
	}
}
