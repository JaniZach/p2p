package com.xmg.p2p.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xmg.p2p.base.domain.Logininfo;
import com.xmg.p2p.base.service.ILogininfoService;
import com.xmg.p2p.base.util.AjaxResult;

/**
 * 前台用户的注册管理
 * @author Jani
 */
@Controller
public class RegisterController {
	@Autowired
	private ILogininfoService logininfoService;

	@RequestMapping("/register")
	@ResponseBody
	public AjaxResult register(String username, String password) {
		AjaxResult result = null;
		try {
			//能进入此方法,说明是普通用户,设置账号为普通用户
			logininfoService.register(username, password,Logininfo.USERTYPE_USER);
			result = new AjaxResult(true, "该账户可用!,点击确定进入登录页面");
		} catch (Exception e) {
			e.printStackTrace();
			result = new AjaxResult(false, "该账户已经存在!");
		}
		return result;
	}

	@RequestMapping("/checkUsername")
	@ResponseBody
	public boolean checkUsername(String username) {
		return logininfoService.checkUsername(username);
	}
}
