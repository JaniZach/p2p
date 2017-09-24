package com.xmg.p2p.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xmg.p2p.business.domain.BidRequest;
import com.xmg.p2p.business.service.IBidRequestService;

/**
 * 前台首页的控制器
 * @author Jani
 */
@Controller
public class IndexController {
	@Autowired
	private IBidRequestService bidRequestService;
	/**
	 * 跳转首页的方法
	 * @return
	 */
	@RequestMapping("index")
	public String index(Model model){
		//有用户登录时,查询和这个用户有关的借款标
		List<BidRequest> bidRequests = this.bidRequestService.listIndex(5);
		model.addAttribute("bidRequests", bidRequests);
		return "main";
	}
}
