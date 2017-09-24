package com.xmg.p2p.mgrsite.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xmg.p2p.base.domain.Logininfo;
import com.xmg.p2p.base.service.ILogininfoService;
import com.xmg.p2p.base.util.AjaxResult;

@Controller
public class LoginController {
	@Autowired
	private ILogininfoService logininfoService;
	@RequestMapping("login")
	@ResponseBody
	public AjaxResult login(String username,String password,HttpServletRequest request){
		AjaxResult result = null;
		Logininfo info = logininfoService.login(username, password, request.getRemoteAddr(), Logininfo.USERTYPE_MANAGER);
		if(info==null){
			result = new AjaxResult(false,"账户或密码错误!");
		}else{
			result = new AjaxResult(true,"登录成功,点击确定进入主页面!");
		}
		return result;
	}
	@RequestMapping("main")
	public String main(){
		return "main";
	}
}
