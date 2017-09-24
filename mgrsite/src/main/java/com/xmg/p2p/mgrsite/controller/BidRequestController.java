package com.xmg.p2p.mgrsite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xmg.p2p.base.domain.RealAuth;
import com.xmg.p2p.base.domain.UserFile;
import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.service.IRealAuthService;
import com.xmg.p2p.base.service.IUserFileService;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.util.AjaxResult;
import com.xmg.p2p.base.util.BidConst;
import com.xmg.p2p.business.domain.BidRequest;
import com.xmg.p2p.business.domain.BidRequestAudit;
import com.xmg.p2p.business.query.BidRequestQueryObject;
import com.xmg.p2p.business.service.IBidRequestAuditService;
import com.xmg.p2p.business.service.IBidRequestService;

/**
 * 后台借款处理的控制器
 * 
 * @author Jani
 */
@Controller
public class BidRequestController {
	@Autowired
	private IBidRequestService bidRequestService;
	@Autowired
	private IBidRequestAuditService bidRequestAuditService;
	@Autowired
	private IRealAuthService realAuthService;
	@Autowired
	private IUserinfoService userinfoService;
	@Autowired
	private IUserFileService userFileService;

	/**
	 * 后台标的审核列表
	 * 
	 * @param qo
	 * @param model
	 * @return
	 */
	@RequestMapping("bidrequest_publishaudit_list")
	public String publishAudit(@ModelAttribute("qo") BidRequestQueryObject qo, Model model) {
		// 发标前审核的是:用户提交的所有未审核待发布的借款单
		qo.setBidRequestState(BidConst.BIDREQUEST_STATE_PUBLISH_PENDING);
		model.addAttribute("pageResult", this.bidRequestService.queryPage(qo));
		return "bidrequest/publish_audit";
	}

	/**
	 * 后台发标前的审核操作
	 * 
	 * @param id
	 *            审核对象id
	 * @param remark
	 *            审核备注
	 * @param state
	 *            审核状态
	 * @return
	 */
	@RequestMapping("bidrequest_publishaudit")
	@ResponseBody
	public AjaxResult publishAudit(Long id, String remark, int state) {
		AjaxResult result = null;
		try {
			this.bidRequestAuditService.publishAudit(id, remark, state);
			result = new AjaxResult(true, "审核成功!");
		} catch (Exception e) {
			e.printStackTrace();
			result = new AjaxResult(e.getMessage());
		}
		return result;
	}

	/**
	 * 后台发标前审核借款标信息查看
	 * 
	 * @param id
	 *            借款标的id
	 * @return
	 */
	@RequestMapping("borrow_info")
	public String borrowInfo(Long id, Model model) {
		BidRequest bidRequest = this.bidRequestService.selectByPrimaryKey(id);
		model.addAttribute("bidRequest", bidRequest);
		// 借款标相关的审核
		List<BidRequestAudit> audits = this.bidRequestAuditService.listByBidRequestId(id);
		model.addAttribute("audits", audits);
		// 申请借款的用户信息及实名认证信息
		Userinfo userinfo = userinfoService.selectByPrimaryKey(bidRequest.getCreateUser().getId());
		model.addAttribute("userInfo", userinfo);
		RealAuth realAuth = realAuthService.selectByPrimaryKey(userinfo.getRealAuthId());
		model.addAttribute("realAuth", realAuth);
		// 用户相关的全部风控资料
		List<UserFile> userFiles = this.userFileService.queryUserFileAudit(userinfo.getId());
		model.addAttribute("userFiles", userFiles);
		return "bidrequest/borrow_info";
	}
	/**
	 * 后台满标一审审核列表
	 * @param qo
	 * @param model
	 * @return
	 */
	@RequestMapping("bidrequest_audit1_list")
	public String brAuditList1(@ModelAttribute("qo") BidRequestQueryObject qo, Model model){
		qo.setBidRequestState(BidConst.BIDREQUEST_STATE_APPROVE_PENDING_1);//只查看处于满标一审中的借款标
		model.addAttribute("pageResult", this.bidRequestService.queryPage(qo));
		return "bidrequest/audit1";
	}
	/**
	 * 后台满标二审列表
	 * @param qo
	 * @param model
	 * @return
	 */
	@RequestMapping("bidrequest_audit2_list")
	public String brAuditList2(@ModelAttribute("qo") BidRequestQueryObject qo, Model model){
		qo.setBidRequestState(BidConst.BIDREQUEST_STATE_APPROVE_PENDING_2);//只查看处于满标二审中的借款标
		model.addAttribute("pageResult", this.bidRequestService.queryPage(qo));
		return "bidrequest/audit2";
	}
	@RequestMapping("bidrequest_audit1")
	@ResponseBody
	public AjaxResult fullAudit1(Long id, String remark, int state){
		AjaxResult result = null;
		try {
			this.bidRequestAuditService.fullAudit1(id, remark, state);
			result = new AjaxResult(true, "审核成功!");
		} catch (Exception e) {
			e.printStackTrace();
			result = new AjaxResult(e.getMessage());
		}
		return result;
	}
	@RequestMapping("bidrequest_audit2")
	@ResponseBody
	public AjaxResult fullAudit2(Long id, String remark, int state){
		AjaxResult result = null;
		try {
			this.bidRequestAuditService.fullAudit2(id, remark, state);
			result = new AjaxResult(true, "审核成功!");
		} catch (Exception e) {
			e.printStackTrace();
			result = new AjaxResult(e.getMessage());
		}
		return result;
	}
}
