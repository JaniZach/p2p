package com.xmg.p2p.base.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.xmg.p2p.base.domain.Account;
import com.xmg.p2p.base.domain.IpLog;
import com.xmg.p2p.base.domain.Logininfo;
import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.mapper.AccountMapper;
import com.xmg.p2p.base.mapper.IpLogMapper;
import com.xmg.p2p.base.mapper.LogininfoMapper;
import com.xmg.p2p.base.mapper.UserinfoMapper;
import com.xmg.p2p.base.service.ILogininfoService;
import com.xmg.p2p.base.util.BidConst;
import com.xmg.p2p.base.util.MD5;
import com.xmg.p2p.base.util.UserContext;

@Controller
public class LogininfoServiceImpl implements ILogininfoService {
	@Autowired
	private LogininfoMapper logininfoMapper;
	@Autowired
	private AccountMapper accountMapper;
	@Autowired
	private UserinfoMapper userinfoMapper;
	@Autowired
	private IpLogMapper ipLogMapper;

	@Override
	public Logininfo register(String username, String password,int userType) {
		int ret = logininfoMapper.queryLogininfoByUsername(username);
		if(ret >0){
			//账号已注册
			throw new RuntimeException("账号已注册!");
		}else{
			Logininfo logininfo = new Logininfo();
			logininfo.setUsername(username);
			//密码加密处理
			logininfo.setPassword(MD5.encode(password));
			//保存账号状态
			logininfo.setState(Logininfo.STATE_NORMAL);
			//保存该账号是普通用户还是管理员
			logininfo.setUserType(userType);
			//保存注册信息
			logininfoMapper.insert(logininfo);
			//创建Account信息(设置id)
			Account account = new Account();
			account.setId(logininfo.getId());
			accountMapper.insert(account);
			//创建Userinfo信息(设置id)
			Userinfo userinfo = new Userinfo();
			userinfo.setId(logininfo.getId());
			userinfoMapper.insert(userinfo);
			return logininfo;
		}
	}

	@Override
	public boolean checkUsername(String username) {
		//数据库信息查询到,返回false;
		//数据库查询不到信息,返回true;
		return logininfoMapper.queryLogininfoByUsername(username)<=0;
	}

	@Override
	public Logininfo login(String username, String password,String ip,int userType) {
		Logininfo info = logininfoMapper.login(username,MD5.encode(password),userType);
		//进行登陆日志的记录
		IpLog log = new IpLog();
		log.setLoginTime(new Date());
		//注意此处使用username
		log.setUsername(username);
		log.setIp(ip);
		//设置用户类型
		log.setUserType(userType);
		if(info==null){
			log.setState(IpLog.LOGIN_FAILURE);
			//账号或密码错误
			//避免事务的影响登陆失败日志的保存,此处不抛出异常
			//throw new RuntimeException("账号或密码错误,请重新输入");
		}else{
			log.setState(IpLog.LOGIN_SUCCESS);
			//账号密码正确,将用户信息共享到session中
			UserContext.setCurrent(info);
//			测试
//			Logininfo current = UserContext.getCurrent();
//			System.out.println("当前登陆信息"+current);
		}
		//进行登陆日志的保存
		ipLogMapper.insert(log);
		return info;
	}

	@Override
	public void initAdmin() {
		//查询数据库中管理员账号的数量
		int count = logininfoMapper.queryManagerCount(Logininfo.USERTYPE_MANAGER);
		if(count==0){
			//初始化管理员账号信息
			Logininfo info = new Logininfo();
			info.setUsername(BidConst.DEFAULT_ADMIN_USERNAME);
			info.setPassword(MD5.encode(BidConst.DEFAULT_ADMIN_PASSWORD));
			info.setUserType(Logininfo.USERTYPE_MANAGER);
			info.setState(Logininfo.STATE_NORMAL);
			//保存初始化的管理员账号信息
			logininfoMapper.insert(info);
		}
	}

	@Override
	public List<Map<String, Object>> queryArray4Autocomplate(String keyword) {
		return this.logininfoMapper.queryArray4Autocomplate(keyword);
	}
}
