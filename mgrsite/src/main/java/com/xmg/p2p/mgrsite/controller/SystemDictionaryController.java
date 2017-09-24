package com.xmg.p2p.mgrsite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xmg.p2p.base.domain.SystemDictionary;
import com.xmg.p2p.base.domain.SystemDictionaryItem;
import com.xmg.p2p.base.query.SystemDictionaryItemQueryObject;
import com.xmg.p2p.base.query.SystemDictionaryQueryObject;
import com.xmg.p2p.base.service.ISystemDictionaryItemService;
import com.xmg.p2p.base.service.ISystemDictionaryService;
import com.xmg.p2p.base.util.AjaxResult;

/**
 * 后台数据字典相关的控制器
 * @author Jani
 */
@Controller
public class SystemDictionaryController {
	@Autowired
	private ISystemDictionaryService systemDictionaryService;
	@Autowired
	private ISystemDictionaryItemService systemDictionaryItemService;
	
	@RequestMapping("systemDictionary_list")
	public String systemDictionaryList(Model model,@ModelAttribute("qo") SystemDictionaryQueryObject qo){
		model.addAttribute("pageResult", systemDictionaryService.queryPage(qo));
		return "systemdic/systemDictionary_list";
	}
	@RequestMapping("systemDictionary_update")
	@ResponseBody
	public AjaxResult systemDictionaryUpdate(SystemDictionary systemDictionary){
		AjaxResult result = null;
		try {
			systemDictionaryService.saveOrUpdate(systemDictionary);
			result = new AjaxResult(true, "修改成功!");
		} catch (Exception e) {
			e.printStackTrace();
			result = new AjaxResult(e.getMessage());
		}
		return result;
	}
	//----------------------------------------------------------------------
	@RequestMapping("systemDictionaryItem_list")
	public String systemDictionaryItemList(Model model,@ModelAttribute("qo") SystemDictionaryItemQueryObject qo){
		model.addAttribute("pageResult", systemDictionaryItemService.queryPage(qo));
		model.addAttribute("systemDictionaryGroups",systemDictionaryService.selectAll());
		return "systemdic/systemDictionaryitem_list";
	}
	@RequestMapping("systemDictionaryItem_update")
	@ResponseBody
	public AjaxResult systemDictionaryItemUpdate(SystemDictionaryItem systemDictionaryItem){
		AjaxResult result = null;
		try {
			systemDictionaryItemService.saveOrUpdate(systemDictionaryItem);
			result = new AjaxResult(true, "修改成功!");
		} catch (Exception e) {
			e.printStackTrace();
			result = new AjaxResult(e.getMessage());
		}
		return result;
	}
}
