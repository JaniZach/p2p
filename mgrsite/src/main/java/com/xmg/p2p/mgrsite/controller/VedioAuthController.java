package com.xmg.p2p.mgrsite.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xmg.p2p.base.query.VedioAuthQueryObject;
import com.xmg.p2p.base.service.ILogininfoService;
import com.xmg.p2p.base.service.IVedioAuthService;
import com.xmg.p2p.base.util.AjaxResult;

/**
 * 后台视频认证的控制器
 * @author Jani
 */
@Controller
public class VedioAuthController {
	@Autowired
	private IVedioAuthService vedioAuthService;
	@Autowired
	private ILogininfoService logininfoService;
	@RequestMapping("vedioAuth")
	public String videoAuthList(@ModelAttribute("qo")VedioAuthQueryObject qo,Model model){
		model.addAttribute("pageResult", this.vedioAuthService.queryPage(qo));
		return "vedioAuth/list";
	}
	/**
	 * 后台视频认证审核控制器
	 * @param loginInfoValue
	 * @param remark
	 * @param state
	 * @return
	 */
	@RequestMapping("vedioAuth_audit")
	@ResponseBody
	public AjaxResult vedioAuthAudit(Long loginInfoValue,String remark,int state){
		AjaxResult result = null;
		try {
			this.vedioAuthService.audit(loginInfoValue,remark,state);
			result = new AjaxResult(true, "视频认证成功!");
		} catch (Exception e) {
			e.printStackTrace();
			result = new AjaxResult(e.getMessage());
		}
		return result;
	}
	/**
	 * 自动补全相关
	 */
	@RequestMapping("vedioAuth_autocomplate")
	@ResponseBody
	public List<Map<String, Object>> autocomplate(String keyword){
		List<Map<String, Object>> list = this.logininfoService.queryArray4Autocomplate(keyword);
		return list;
	}
}