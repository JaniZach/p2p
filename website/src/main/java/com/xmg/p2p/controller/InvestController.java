package com.xmg.p2p.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xmg.p2p.base.domain.Account;
import com.xmg.p2p.base.domain.Logininfo;
import com.xmg.p2p.base.domain.RealAuth;
import com.xmg.p2p.base.domain.UserFile;
import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.service.IAccountService;
import com.xmg.p2p.base.service.IRealAuthService;
import com.xmg.p2p.base.service.IUserFileService;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.util.AjaxResult;
import com.xmg.p2p.base.util.BidConst;
import com.xmg.p2p.base.util.UserContext;
import com.xmg.p2p.business.domain.BidRequest;
import com.xmg.p2p.business.query.BidRequestQueryObject;
import com.xmg.p2p.business.service.IBidRequestService;

/**
 * 前台投资相关的控制器
 * @author Jani
 */
@Controller
public class InvestController {
	@Autowired
	private IBidRequestService bidRequestService;
	@Autowired
	private IUserinfoService userinfoService;
	@Autowired
	private IAccountService accountService;
	@Autowired
	private IRealAuthService realAuthService;
	@Autowired
	private IUserFileService userFileService;
	/**
	 * 前台投资的外框框
	 */
	@RequestMapping("invest")
	public String invest(@ModelAttribute("qo")BidRequestQueryObject qo){
		return "invest";
	}
	/**
	 * 前台投资的列表信息
	 */
	@RequestMapping("invest_list")
	public String investList(@ModelAttribute("qo")BidRequestQueryObject qo,Model model){
		//只能查看 投标中/还款中/已还清 的借款标
		qo.setBidRequestStates(new int[]{
				BidConst.BIDREQUEST_STATE_BIDDING,
                BidConst.BIDREQUEST_STATE_PAYING_BACK,BidConst.BIDREQUEST_STATE_COMPLETE_PAY_BACK});
		model.addAttribute("pageResult", this.bidRequestService.queryPage(qo));
		return "invest_list";
	}
	/**
	 * 前台投资查看页面的方法
	 * @param id 借款标的id
	 */
	@RequestMapping("borrow_info")
	public String borrowInfo(Long id,Model model){
		BidRequest bidRequest = this.bidRequestService.selectByPrimaryKey(id);
		Logininfo createUser = bidRequest.getCreateUser();
		Userinfo userinfo = this.userinfoService.selectByPrimaryKey(createUser.getId());
		//共享页面借款人信息
		model.addAttribute("userInfo", userinfo);
		RealAuth realAuth = this.realAuthService.selectByPrimaryKey(userinfo.getRealAuthId());
		model.addAttribute("realAuth", realAuth);
		//共享到页面借款人风险投资资料
		List<UserFile> userFiles = this.userFileService.queryUserFileAudit(createUser.getId());
		model.addAttribute("userFiles", userFiles);
		//共享页面借款标信息
		model.addAttribute("bidRequest", bidRequest);
		if(UserContext.getCurrent()!=null&&!userinfo.getId().equals(UserContext.getCurrent().getId())){
			//如果是不是借款人登录查看,共享其账户信息
			model.addAttribute("self", false);
			Account account = this.accountService.selectByPrimaryKey(UserContext.getCurrent().getId());
			model.addAttribute("account", account);
		}else if(UserContext.getCurrent()!=null){
			//借款人查看
			model.addAttribute("self", true);
		}else{
			model.addAttribute("self", false);
		}
		return "borrow_info";
	}
	/**
	 * 前台投标相关的方法
	 * @param bidRequestId 借款标的id
	 * @param amount 投标金额
	 */
	@RequestMapping("borrow_bid")
	@ResponseBody
	public AjaxResult borrowBid(Long bidRequestId,BigDecimal amount){
		AjaxResult result = null;
		try {
			this.bidRequestService.invest(bidRequestId,amount);
			result = new AjaxResult(true, "投标成功!");
		} catch (Exception e) {
			e.printStackTrace();
			result = new AjaxResult(e.getMessage());
		}
		return result;
	}
}
