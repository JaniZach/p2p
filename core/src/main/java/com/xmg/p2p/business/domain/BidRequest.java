package com.xmg.p2p.business.domain;

import static com.xmg.p2p.base.util.BidConst.BIDREQUEST_STATE_APPROVE_PENDING_1;
import static com.xmg.p2p.base.util.BidConst.BIDREQUEST_STATE_APPROVE_PENDING_2;
import static com.xmg.p2p.base.util.BidConst.BIDREQUEST_STATE_BIDDING;
import static com.xmg.p2p.base.util.BidConst.BIDREQUEST_STATE_BIDDING_OVERDUE;
import static com.xmg.p2p.base.util.BidConst.BIDREQUEST_STATE_COMPLETE_PAY_BACK;
import static com.xmg.p2p.base.util.BidConst.BIDREQUEST_STATE_PAYING_BACK;
import static com.xmg.p2p.base.util.BidConst.BIDREQUEST_STATE_PAY_BACK_OVERDUE;
import static com.xmg.p2p.base.util.BidConst.BIDREQUEST_STATE_PUBLISH_PENDING;
import static com.xmg.p2p.base.util.BidConst.BIDREQUEST_STATE_PUBLISH_REFUSE;
import static com.xmg.p2p.base.util.BidConst.BIDREQUEST_STATE_REJECTED;
import static com.xmg.p2p.base.util.BidConst.BIDREQUEST_STATE_UNDO;
import static com.xmg.p2p.base.util.BidConst.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.xmg.p2p.base.domain.BaseDomain;
import com.xmg.p2p.base.domain.Logininfo;
import com.xmg.p2p.base.util.BidConst;

import lombok.Getter;
import lombok.Setter;

/**
 * 借款对象的实体类
 * @author Jani
 */
@Setter@Getter
public class BidRequest extends BaseDomain{
	private int version;//对象版本信息(用作乐观锁)
	private int returnType;//还款方式
	private int bidRequestType;//借款类型
	private int bidRequestState;//标的状态
	private BigDecimal bidRequestAmount;//借款的金额
	private BigDecimal currentRate;//借款利率
	private BigDecimal minBidAmount;//这个借款允许的最小投标金额
	private int monthes2Return;//借款期限,也就是这个借款的还款时间
	private int bidCount;//这个借款已经有多少次投标
	private BigDecimal totalRewardAmount;//总回报金额(这个标总利息的金额)
	private BigDecimal currentSum = ZERO;//目前已经投了多少钱
	private String title;//借款标题
	private String description;//借款描述
	private String note;//风控评审意见
	private Date disableDate;//招标到期时间
	private int disableDays;//招标天数
	private Logininfo createUser;//借款人
	private List<Bid> bids;//这个标的投标记录
	private Date applyTime;//申请时间
	private Date publishTime;//发布时间
	public String getBidRequestStateDisplay(){
		switch(this.bidRequestState){
		case BIDREQUEST_STATE_PUBLISH_PENDING :return "待发布";
		case BIDREQUEST_STATE_BIDDING :return "招标中";
		case BIDREQUEST_STATE_UNDO :return "已撤销";
		case BIDREQUEST_STATE_BIDDING_OVERDUE :return "流标";
		case BIDREQUEST_STATE_APPROVE_PENDING_1 :return "满标一审";
		case BIDREQUEST_STATE_APPROVE_PENDING_2 :return "满标二审";
		case BIDREQUEST_STATE_REJECTED :return "满标审核被拒绝";
		case BIDREQUEST_STATE_PAYING_BACK :return "还款中";
		case BIDREQUEST_STATE_COMPLETE_PAY_BACK :return "已还清";
		case BIDREQUEST_STATE_PAY_BACK_OVERDUE :return "逾期";
		case BIDREQUEST_STATE_PUBLISH_REFUSE :return "发标审核拒绝状态";
		default : return "";
		}
	}
	public String getJsonString(){
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("id", id);
		json.put("username", this.createUser.getUsername());
		json.put("title",this.title);
		json.put("bidRequestAmount", this.bidRequestAmount);
		json.put("currentRate", this.currentRate);
		json.put("monthes2Return", this.monthes2Return);
		json.put("returnType", this.returnType==RETURN_TYPE_MONTH_INTEREST_PRINCIPAL?"等额本息":"先息后本");
		json.put("totalRewardAmount", this.totalRewardAmount);
		return JSON.toJSONString(json);
	}
	public BigDecimal getRemainAmount(){
		//还需金额
		return this.bidRequestAmount.subtract(this.currentSum);
	}
	public int getPersent(){
		//投标进度
		return this.currentSum.divide(this.bidRequestAmount, 
				BidConst.DISPLAY_SCALE,RoundingMode.HALF_UP).multiply(new BigDecimal("100.00")).intValue();
	}
	public String getReturnTypeDisplay(){
		return this.returnType==RETURN_TYPE_MONTH_INTEREST_PRINCIPAL?"等额本息":"先息后本";
	}
}
