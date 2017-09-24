package com.xmg.p2p.mgrsite.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xmg.p2p.base.util.AjaxResult;
import com.xmg.p2p.business.query.RechargeOfflineQueryObject;
import com.xmg.p2p.business.service.IPlatformBankinfoService;
import com.xmg.p2p.business.service.IRechargeOfflineService;

/**
 * 后台线下充值审核相关的控制器
 * @author Jani
 */
@Controller
public class RechargeOfflineController {
	@Autowired
	private IRechargeOfflineService rechargeOfflineService;
	@Autowired
	private IPlatformBankinfoService bankinfoService;
	/**
	 * 后台线下充值审核列表
	 * @param model
	 * @param qo
	 * @return
	 */
	@RequestMapping("rechargeOffline")
	public String rechargeOfflineList(Model model,@Param("qo")RechargeOfflineQueryObject qo){
		model.addAttribute("pageResult", this.rechargeOfflineService.queryPage(qo));
		model.addAttribute("banks", this.bankinfoService.selectAll());
		return "rechargeoffline/list";
	}
	/**
	 * 后台线下充值审核
	 * @param id 充值单id
	 * @param remark 审核备注
	 * @param state 充值单状态
	 * @return
	 */
	@RequestMapping("rechargeOffline_audit")
	@ResponseBody
	public AjaxResult rechargeOfflineAudit(Long id,String remark,int state){
		AjaxResult result = null;
		try {
			this.rechargeOfflineService.audit(id,remark,state);
			result = new AjaxResult(true, "xxx成功!");
		} catch (Exception e) {
			e.printStackTrace();
			result = new AjaxResult(e.getMessage());
		}
		return result;
	}
}
