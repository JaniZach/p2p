package com.xmg.p2p.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xmg.p2p.base.domain.Account;
import com.xmg.p2p.base.domain.Logininfo;
import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.page.PageResult;
import com.xmg.p2p.base.service.IAccountService;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.util.AjaxResult;
import com.xmg.p2p.base.util.BidConst;
import com.xmg.p2p.base.util.UserContext;
import com.xmg.p2p.business.domain.BidRequest;
import com.xmg.p2p.business.domain.PaymentSchedule;
import com.xmg.p2p.business.query.PaymentScheduleQueryObject;
import com.xmg.p2p.business.service.IBidRequestService;
import com.xmg.p2p.business.service.IPaymentScheduleService;

/**
 * 前台借款的控制器
 * @author Jani
 */
@Controller
public class BorrowController {
	@Autowired
	private IAccountService accountService;
	@Autowired
	private IUserinfoService userinfoService;
	@Autowired
	private IBidRequestService bidRequestService;
	@Autowired
	private IPaymentScheduleService psService;
	/**
	 * 前台借款页面
	 */
	@RequestMapping("borrow")
	public String borrowList(Model model){
		Logininfo current = UserContext.getCurrent();
		if(current!=null){
			//1.判断是否登陆,已经登陆跳转到borrow.ftl中
			model.addAttribute("account", accountService.getCurrent());
			model.addAttribute("userinfo", userinfoService.getCurrent());
			model.addAttribute("creditBorrowScore",BidConst.BORROW_LIMIT_SCORE);
			return "borrow";
		}else{
			//2.没有登陆,跳转到borrow.html
			return "redirect:borrow.html";
		}
	}
	/**
	 * 前台借钱申请的控制器
	 */
	@RequestMapping("borrowInfo")
	public String borrowInfoList(Model model){
		//判断用户是否满足借款条件，满足条件跳转借款申请填写页面，否则跳转到空页面
		Account account = this.accountService.getCurrent();
		Userinfo userinfo = this.userinfoService.getCurrent();
		if(userinfo.getHasBasicInfo()//基本信息完成
				&& userinfo.getHasRealAuth()//真实身份认证
				&& userinfo.getHasVedioAuth()//视频认证
				&& userinfo.getScore()>=BidConst.BORROW_LIMIT_SCORE//材料认证达到分数要求
				){
			if(!userinfo.getHasBidRequestProcess()){//没有待处理的借款流程
				model.addAttribute("userInfo", userinfo);
				model.addAttribute("account", account);
				model.addAttribute("minBidRequestAmount", BidConst.SMALLEST_BIDREQUEST_AMOUNT);
				model.addAttribute("minBidAmount", BidConst.SMALLEST_BID_AMOUNT);
				return "borrow_apply";
			}else{
				//有待处理的借款流程,跳转到结果页面
				return "borrow_apply_result";
			}
		}else{
			//没有借款条件,跳转到申请借款页面
			return "redirect:/borrow.do";
		}
	}
	/**
	 * 前台借款信息提交处理的方法
	 */
	@RequestMapping("borrow_apply")
	@ResponseBody
	public AjaxResult apply(BidRequest bidRequest){
		AjaxResult result = null;
		try {
			this.bidRequestService.apply(bidRequest);
			result = new AjaxResult(true, "借款申请提交成功!");
		} catch (Exception e) {
			e.printStackTrace();
			result = new AjaxResult(e.getMessage());
		}
		return result;
	}
	/**
	 * 前台还款列表
	 */
	@RequestMapping("borrowBidReturn_list")
	public String borrowBidReturnList(@ModelAttribute("qo")PaymentScheduleQueryObject qo,Model model){
		qo.setBorrowUserId(UserContext.getCurrent().getId());
		PageResult pageResult = this.psService.queryPage(qo);
		model.addAttribute("pageResult", pageResult);
		Account account = this.accountService.getCurrent();
		model.addAttribute("account", account);
		return "returnmoney_list";
	}
	/**
	 * 前台用户还款的方法
	 */
	@RequestMapping("returnMoney")
	@ResponseBody
	public AjaxResult returnMoney(Long id){
		AjaxResult result = null;
		try {
			this.bidRequestService.returnMoney(id);
			result = new AjaxResult(true, "还款成功!");
		} catch (Exception e) {
			e.printStackTrace();
			result = new AjaxResult(e.getMessage());
		}
		return result;
	}
}
