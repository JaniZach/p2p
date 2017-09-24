package com.xmg.p2p.base.domain;

import com.xmg.p2p.base.util.BitStatesUtils;

import lombok.Getter;
import lombok.Setter;

/**
 * 封装用户登录后的相关信息
 * @author Jani
 */
@Setter@Getter
public class Userinfo  extends BaseDomain{
	private int version;//版本号,乐观锁
	private long bitState=0L;//用户状态值
	private String realName;//用户真实名
	private String idNumber;//用户身份证号
	private String phoneNumber;//用户电话
	private SystemDictionaryItem incomeGrade;//收入
	private SystemDictionaryItem marriage;//婚姻情况
	private SystemDictionaryItem kidCount;//子女情况
	private SystemDictionaryItem educationBackground;//学历
	private SystemDictionaryItem houseCondition;//住房条件
	private int score;//材料认证分数
	private Long realAuthId;//关联实名认证的ID
	//为userinfo添加状态的方法
	public void addState(long state){
		this.bitState = BitStatesUtils.addState(this.bitState, state);
	}
	//为userinfo移除状态码的方法
	public void removeState(long state){
		this.bitState = BitStatesUtils.removeState(this.bitState, state);
	}
	//返回用户是否绑定手机
	public boolean getHasBindPhone(){
		return BitStatesUtils.hasState(this.bitState, BitStatesUtils.OP_BIND_PHONE);
	}
	//返回用户是否绑定邮箱
	public boolean getHasBindEmail(){
		return BitStatesUtils.hasState(this.bitState, BitStatesUtils.OP_BIND_EMAIL);
	}
	//返回用户是否填写基本信息
	public boolean getHasBasicInfo(){
		return BitStatesUtils.hasState(this.bitState, BitStatesUtils.OP_BASIC_INFO);
	}
	//返回用户是否进行实名认证
	public boolean getHasRealAuth(){
		return BitStatesUtils.hasState(this.bitState, BitStatesUtils.OP_REAL_AUTH);
	}
	//返回用户是否进行视频认证
	public boolean getHasVedioAuth(){
		return BitStatesUtils.hasState(this.bitState, BitStatesUtils.OP_VEDIO_AUTH);
	}
	//返回用户是否有一个借款流程正在处理流程中
	public boolean getHasBidRequestProcess(){
		return BitStatesUtils.hasState(this.bitState, BitStatesUtils.OP_HAS_BIDREQUEST_PROCESS);
	}
	public boolean getHasBindBank(){
		return BitStatesUtils.hasState(this.bitState, BitStatesUtils.OP_BIND_BANK);
	}
}
