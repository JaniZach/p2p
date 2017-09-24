package com.xmg.p2p.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xmg.p2p.base.util.AjaxResult;
import com.xmg.p2p.business.domain.RechargeOffline;
import com.xmg.p2p.business.service.IPlatformBankinfoService;
import com.xmg.p2p.business.service.IRechargeOfflineService;

/**
 * 前台充值的控制器
 * @author Jani
 */
@Controller
public class RechargeController {
	@Autowired
	private IPlatformBankinfoService bankinfoService;
	@Autowired
	private IRechargeOfflineService rechargeOfflineService;
	/**
	 * 前台充值列表
	 * @param model
	 * @return
	 */
	@RequestMapping("recharge")
	public String recharge(Model model){
		model.addAttribute("banks", this.bankinfoService.selectAll());
		return "recharge";
	}
	@RequestMapping("recharge_save")
	@ResponseBody
	public AjaxResult recharge_save(RechargeOffline ro){
		AjaxResult result = null;
		try {
			this.rechargeOfflineService.apply(ro);
			result = new AjaxResult(true, "充值成功!");
		} catch (Exception e) {
			e.printStackTrace();
			result = new AjaxResult(e.getMessage());
		}
		return result;
	}
}
