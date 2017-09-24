package com.xmg.p2p.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.xmg.p2p.base.domain.RealAuth;
import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.service.IRealAuthService;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.util.AjaxResult;

/**
 * 前台实名认证相关的控制器
 * @author Jani
 */
@Controller
public class RealAuthController {
	@Autowired
	private IUserinfoService userinfoService;
	@Autowired
	private IRealAuthService realAuthService;
	/**
	 * 前台实名认证-进行认证/认证状态页面
	 * @param model
	 * @return
	 */
	@RequestMapping("realAuth")
	public String realAuth(Model model){
		Userinfo userinfo = userinfoService.getCurrent();
		//1.userinfo已经实名认证-->已经认证页面
		if(userinfo.getHasRealAuth()){
			model.addAttribute("auditing", false);
			//共享实名认证信息
			model.addAttribute("realAuth", realAuthService.selectByPrimaryKey(userinfo.getRealAuthId()));
			return "realAuth_result";
		}
		if(userinfo.getRealAuthId()!=null){
			//2.userinfo中未实名认证,但realAuthId不为null-->实名认证待审核页面
			model.addAttribute("auditing", true);
			return "realAuth_result";
		}
		//3.userinfo中未实名认证,且realAuthId为null-->填写实名认证信息页面
		return "realAuth";
	}
	/**
	 * 前台实名认证信息填写后保存页面
	 * @param realAuth
	 * @return
	 */
	@RequestMapping("realAuth_save")
	@ResponseBody
	public AjaxResult realAuthSave(RealAuth realAuth){
		AjaxResult result = null;
		try {
			realAuthService.save(realAuth);
			result = new AjaxResult(true, "修改成功!");
		} catch (Exception e) {
			e.printStackTrace();
			result = new AjaxResult(e.getMessage());
		}
		return result;
	}
	/**
	 * 实名认证照片上传的处理
	 * @param img
	 * @return	返回文件上传后相对路径
	 */
	@RequestMapping("uploadImg")
	@ResponseBody
	public String uploadImg(MultipartFile img){
		String fileName = this.realAuthService.uploadImg(img);
		return fileName;
	}
}
