package com.xmg.p2p.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xmg.p2p.base.domain.Logininfo;
import com.xmg.p2p.base.service.ILogininfoService;
import com.xmg.p2p.base.util.AjaxResult;

/**
 * 前台用户的登陆管理
 * @author Jani
 */
@Controller
public class LoginController {
	@Autowired
	private ILogininfoService logininfoService;

	@RequestMapping("/login")
	@ResponseBody
	public AjaxResult login(String username, String password, HttpServletRequest request) {
		AjaxResult result = null;
		//此处登录账号可以确定是普通用户
		Logininfo logininfo = logininfoService.login(username, password, request.getRemoteAddr(),Logininfo.USERTYPE_USER);
		if (logininfo != null) {
			result = new AjaxResult(true, "登陆成功,点击跳转主页面");
		} else {
			result = new AjaxResult(false, "登陆失败,账号或密码错误");
		}
		return result;
	}
}
