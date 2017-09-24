package com.xmg.p2p.base.query;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.xmg.p2p.base.util.DateUtil;

import freemarker.template.utility.StringUtil;
import lombok.Getter;
import lombok.Setter;

@Setter@Getter
public class IpLogQueryObject extends QueryObject {
	private String username;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date beginDate;//开始时间
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date endDate;//结束时间
	private int state = -1;//默认-1表示全部状态 0表示登陆成功,1表示登陆失败
	private int userType = -1;//默认-1表示全部用户 0表示普通用户,1表示管理员
	//对日期的处理
	public Date getBeginDate(){
		return DateUtil.getBeginDate(this.beginDate);
	}
	public Date getEndDate(){
		return DateUtil.getEndDate(this.endDate);
	}
	public String getUsername(){
		return StringUtil.emptyToNull(username);
	}
}
