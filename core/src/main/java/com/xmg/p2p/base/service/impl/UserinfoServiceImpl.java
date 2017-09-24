package com.xmg.p2p.base.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xmg.p2p.base.domain.EmailVerify;
import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.mapper.UserinfoMapper;
import com.xmg.p2p.base.service.IEmailVerifyService;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.service.IVerifyCodeService;
import com.xmg.p2p.base.util.BidConst;
import com.xmg.p2p.base.util.BitStatesUtils;
import com.xmg.p2p.base.util.DateUtil;
import com.xmg.p2p.base.util.UserContext;

@Service
public class UserinfoServiceImpl implements IUserinfoService {
	@Autowired
	private UserinfoMapper userinfoMapper;
	@Autowired
	private IVerifyCodeService verifyCodeService;
	@Autowired
	private IEmailVerifyService emailVerifyService;

	public int insert(Userinfo record) {
		return userinfoMapper.insert(record);
	}

	public Userinfo selectByPrimaryKey(Long id) {
		return userinfoMapper.selectByPrimaryKey(id);
	}

	public int updateByPrimaryKey(Userinfo record) {
		int ret = userinfoMapper.updateByPrimaryKey(record);
		// 乐观锁的处理,版本不是最新版本,则抛出异常,事务回滚
		if (ret <= 0) {
			throw new RuntimeException("乐观锁,userinfoId=" + record.getId());
		}
		return ret;
	}

	@Override
	public Userinfo getCurrent() {
		Long id = UserContext.getCurrent().getId();
		Userinfo info = userinfoMapper.selectByPrimaryKey(id);
		return info;
	}

	@Override
	public void bindPhone(String phoneNumber, String verifyCode) {
		//1.判断绑定手机号是否正确,验证码是否输入正确
		boolean verify = verifyCodeService.verify(phoneNumber, verifyCode);
		if(verify){
			//1.1获取当前userinfo对象
			Userinfo userinfo = this.getCurrent();
			//1.2为userinfo对象添加状态
			userinfo.addState(BitStatesUtils.OP_BIND_PHONE);
			userinfo.setPhoneNumber(phoneNumber);
			//1.3更新userinfo
			userinfoMapper.updateByPrimaryKey(userinfo);
		}else{
			throw new RuntimeException("手机绑定失败!");
		}
		//2.获取当前用户userinfo对象,修改该对象绑定手机状态
	}

	@Override
	public void bindEmail(String key) {
		//1.根据key值,查询EmailVerify对象
		EmailVerify emailVerify = emailVerifyService.selectByUUID(key);
		if(emailVerify==null){
			throw new RuntimeException("验证失败:密钥错误");
		}
		//2.判断用户是否绑定邮箱(注意,不要使用getCurrent()方法,因为此次只是验证邮箱,没有登录)
		Userinfo userinfo = userinfoMapper.selectByPrimaryKey(emailVerify.getUserId());
		if(userinfo.getHasBindEmail()){
			throw new RuntimeException("该用户已绑定邮箱,请勿重复绑定!");
		}
		//3.判断是否在有效期内
		if(DateUtil.getSecondsWith(emailVerify.getSendTime(), new Date())>BidConst.verifyEmail_validate_day*24*60*60){
			throw new RuntimeException("验证失败:验证连接已过期");
		}
		//4.绑定邮箱操作
		userinfo.addState(BitStatesUtils.OP_BIND_EMAIL);
		userinfoMapper.updateByPrimaryKey(userinfo);
	}

	@Override
	public void saveBasicInfo(Userinfo userinfo) {
		//基本信息,只需要保存个人学历,月收入,婚姻情况,子女情况,住房条件.其他如手机号等,不进行保存
		Userinfo current = this.getCurrent();
		current.setEducationBackground(userinfo.getEducationBackground());
		current.setIncomeGrade(userinfo.getIncomeGrade());
		current.setMarriage(userinfo.getMarriage());
		current.setKidCount(userinfo.getKidCount());
		current.setHouseCondition(userinfo.getHouseCondition());
		if(!current.getHasBasicInfo()){
			current.addState(BitStatesUtils.OP_BASIC_INFO);
		}
		//更新userinfo中基本信息
		userinfoMapper.updateByPrimaryKey(current);
	}
}
