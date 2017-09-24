package com.xmg.p2p.mgrsite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xmg.p2p.base.util.AjaxResult;
import com.xmg.p2p.business.domain.PlatformBankinfo;
import com.xmg.p2p.business.query.PlatformBankinfoQueryObject;
import com.xmg.p2p.business.service.IPlatformBankinfoService;

/**
 * 后台的平台账号管理
 * @author Jani
 */
@Controller
public class PlatformController {
	@Autowired
	private IPlatformBankinfoService bankinfoService;
	/**
	 * 前往后台的平台账号管理列表
	 * @return
	 */
	@RequestMapping("companyBank_list")
	public String companyBankList(@ModelAttribute("qo") PlatformBankinfoQueryObject qo,Model model){
		model.addAttribute("pageResult", this.bankinfoService.queryPage(qo));
		return "platformbankinfo/list";
	}
	@RequestMapping("companyBank_update")
	@ResponseBody
	public AjaxResult saveOrUpdate(PlatformBankinfo bankinfo){
		AjaxResult result = null;
		try {
			this.bankinfoService.saveOrUpdate(bankinfo);
			result = new AjaxResult(true, "操作成功!");
		} catch (Exception e) {
			e.printStackTrace();
			result = new AjaxResult(e.getMessage());
		}
		return result;
	}
}
