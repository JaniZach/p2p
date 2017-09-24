package com.xmg.p2p.base.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.xmg.p2p.base.domain.Logininfo;
import com.xmg.p2p.base.vo.VerifyCodeVO;

/**
 * 将信息共享到session中,取值放值的工具类
 * 
 * @author Jani
 *
 */
public class UserContext {

	private static final String USER_IN_SESSION = "logininfo";
	private static final String VERIFYCODEVO_IN_SESSION = "verifyCodeVO";
	public static ThreadLocal<HttpServletRequest> lcoal = new ThreadLocal<>();
	public static HttpSession getSession(){
		//获取request
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();
		HttpServletRequest request = requestAttributes.getRequest();
		//获取session
		return request.getSession();
	}

	/**
	 * 向session中放当前登录对象的方法
	 * 
	 * @param info
	 *            当前登录用户登录信息
	 */
	public static void setCurrent(Logininfo info) {
		// 利用自定监听器获取当前线程中的request
		// HttpServletRequest request = UserContext.lcoal.get();
		UserContext.getSession().setAttribute(USER_IN_SESSION, info);
	}

	/**
	 * 从session中取出当前登陆对象的方法
	 * @return 当前登陆对象
	 */
	public static Logininfo getCurrent(){
		//获取session中的登陆对象
		Logininfo info = (Logininfo) UserContext.getSession().getAttribute(USER_IN_SESSION);
		return info;
	}
	/**
	 * 将验证码VerifyCodeVO对象放入session中的方法
	 * @param vo
	 */
	public static void setCurrentVerifyCodeVO(VerifyCodeVO vo){
		UserContext.getSession().setAttribute(VERIFYCODEVO_IN_SESSION, vo);
	}
	/**
	 * 从session中获取验证码对象VerifyCodeVO的方法
	 * @return
	 */
	public static VerifyCodeVO getCurrentVerifyCodeVO(){
		VerifyCodeVO vo = (VerifyCodeVO) UserContext.getSession().getAttribute(VERIFYCODEVO_IN_SESSION);
		return vo;
	}
}
