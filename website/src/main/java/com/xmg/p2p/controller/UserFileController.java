package com.xmg.p2p.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.service.ISystemDictionaryItemService;
import com.xmg.p2p.base.service.IUserFileService;
import com.xmg.p2p.base.util.AjaxResult;
import com.xmg.p2p.base.util.UserContext;

/**
 * 前台风控资料认证的控制器
 * @author Jani
 */
@Controller
public class UserFileController {
	@Autowired
	private IUserFileService userFileService;
	@Autowired
	private ISystemDictionaryItemService itemService;
	/**
	 * 前台风控资料页面
	 * @param model
	 * @return
	 */
	@RequestMapping("userFile")
	public String userFileList(Model model,HttpSession session){
		//获取申请人所拥有的所有未选择风控材料分类的风控材料
		List<Userinfo> userFiles = this.userFileService.queryUserFiles(UserContext.getCurrent().getId(),false);
		if(userFiles!=null && userFiles.size()!=0){
			model.addAttribute("userFiles", userFiles);
			model.addAttribute("fileTypes", itemService.selectBySn("fileType"));
			return "userFiles_commit";
		}
		//获取申请人所拥有的已经选择风控材料分类的风控材料
		userFiles = this.userFileService.queryUserFiles(UserContext.getCurrent().getId(),true);
		model.addAttribute("userFiles",userFiles);
		model.addAttribute("sessionid",session.getId());
		return "userFiles";
	}
	/**
	 * 前台风控材料提交处理的控制器
	 * @param img
	 * @return
	 */
	@RequestMapping("uploadFiles")
	@ResponseBody
	public String uploadFiles(MultipartFile img){
		String fileName = this.userFileService.uploadFile(img);
		return fileName;
	}
	/**
	 * 前台风控材料提交处理的控制器
	 * @param id 封孔材料对象id
	 * @param fileType 风控材料分类
	 * @return
	 */
	@RequestMapping("userFile_selectType")
	@ResponseBody
	public AjaxResult choiceUserFile(Long[] id,Long[] fileType){
		AjaxResult result = null;
		try {
			this.userFileService.choiceUserFile(id,fileType);
			result = new AjaxResult(true, "提交成功!");
		} catch (Exception e) {
			e.printStackTrace();
			result = new AjaxResult(e.getMessage());
		}
		return result;
	}
}