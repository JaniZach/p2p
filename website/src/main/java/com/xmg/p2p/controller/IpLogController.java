package com.xmg.p2p.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xmg.p2p.base.page.PageResult;
import com.xmg.p2p.base.query.IpLogQueryObject;
import com.xmg.p2p.base.service.IIpLogService;
import com.xmg.p2p.base.util.UserContext;
import com.xmg.p2p.util.RequiredLogin;

/**
 * 前台用户登陆日志的管理
 * @author Jani
 */
@Controller
public class IpLogController {
	@Autowired
	private IIpLogService ipLogService;

	/*@RequiredLogin//需要登陆控制
	@RequestMapping("ipLog")
	public String ipLog_list(Model model, @ModelAttribute("qo") IpLogQueryObject qo) {
		// 普通用户只能看到自己的日志
		qo.setUsername(UserContext.getCurrent().getUsername());
		PageResult result = ipLogService.queryPage(qo);
		// 将分页数据传递到页面
		model.addAttribute("result", result);
		return "iplog_list";
	}*/
	/**
	 * 前台ip日志外框框
	 */
	@RequiredLogin//需要登陆控制
	@RequestMapping("ipLog")
	public String ipLog_list(@ModelAttribute("qo") IpLogQueryObject qo) {
		return "iplog_list";
	}
	/**
	 * 前台ip日志列表内容
	 */
	@RequiredLogin//需要登陆控制
	@RequestMapping("ipLogTbody")
	public String ipLog_ajax_tbody(Model model, @ModelAttribute("qo") IpLogQueryObject qo) {
		// 普通用户只能看到自己的日志
		qo.setUsername(UserContext.getCurrent().getUsername());
		PageResult result = ipLogService.queryPage(qo);
		// 将分页数据传递到页面
		model.addAttribute("result", result);
		return "iplog_ajax_tbody";
	}
}
