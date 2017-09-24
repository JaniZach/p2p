package com.xmg.p2p.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.service.ISystemDictionaryItemService;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.util.AjaxResult;

/**
 * 前台填写个人信息的控制器
 * @author Jani
 */
@Controller
public class BasicInfoController {
	@Autowired
	private IUserinfoService userinfoService;
	@Autowired
	private ISystemDictionaryItemService systemDictionaryItem;
	@RequestMapping("basicInfo")
	public String basicInfo(Model model){
		model.addAttribute("userinfo",userinfoService.getCurrent());
		model.addAttribute("educationBackgrounds", systemDictionaryItem.selectBySn("educationBackground"));//个人学历
		model.addAttribute("incomeGrades", systemDictionaryItem.selectBySn("incomeGrade"));//月收入
		model.addAttribute("marriages", systemDictionaryItem.selectBySn("marriage"));//婚姻情况
		model.addAttribute("kidCounts",systemDictionaryItem.selectBySn("kidCount"));//子女情况
		model.addAttribute("houseConditions", systemDictionaryItem.selectBySn("houseCondition"));//住房条件
		return "userinfo";
	}
	@RequestMapping("basicInfo_save")
	@ResponseBody
	public AjaxResult basicInfoSave(Userinfo userinfo){
		AjaxResult result = null;
		try {
			//保存基本信息的方法
			userinfoService.saveBasicInfo(userinfo);
			result = new AjaxResult(true, "xxx成功!");
		} catch (Exception e) {
			e.printStackTrace();
			result = new AjaxResult(e.getMessage());
		}
		return result;
	}
}
