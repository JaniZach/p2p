package com.xmg.p2p.mgrsite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xmg.p2p.base.query.IpLogQueryObject;
import com.xmg.p2p.base.service.IIpLogService;

/**
 * 后台用户的登录日志管理
 * @author Jani
 */
@Controller
public class IpLogController {
	@Autowired
	private IIpLogService ipLogService;
	@RequestMapping("ipLog")
	public String ipLog_list(Model model,@ModelAttribute("qo")IpLogQueryObject qo){
		model.addAttribute("result", ipLogService.queryPage(qo));
		return "ipLog/list";
	}
}