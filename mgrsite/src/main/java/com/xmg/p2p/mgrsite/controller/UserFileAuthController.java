package com.xmg.p2p.mgrsite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xmg.p2p.base.query.UserFileQueryObject;
import com.xmg.p2p.base.service.IUserFileService;
import com.xmg.p2p.base.util.AjaxResult;

/**
 * 后台分控材料审核的控制器
 * @author Jani
 */
@Controller
public class UserFileAuthController {
	@Autowired
	private IUserFileService userFileService;
	@RequestMapping("userFileAuth")
	public String userFileAuthList(@ModelAttribute("qo") UserFileQueryObject qo,Model model){
		model.addAttribute("pageResult", this.userFileService.queryPage(qo));
		return "userFileAuth/list";
	}
	@RequestMapping("userFile_audit")
	@ResponseBody
	public AjaxResult userFileAudit(Long id,String remark,int score,int state){
		AjaxResult result = null;
		try {
			this.userFileService.auditUserFile(id,remark,score,state);
			result = new AjaxResult(true, "审核成功!");
		} catch (Exception e) {
			e.printStackTrace();
			result = new AjaxResult(e.getMessage());
		}
		return result;
	}
}
