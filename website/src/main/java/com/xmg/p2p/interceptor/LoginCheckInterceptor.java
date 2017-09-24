package com.xmg.p2p.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.xmg.p2p.base.domain.Logininfo;
import com.xmg.p2p.base.util.UserContext;
import com.xmg.p2p.util.RequiredLogin;

/**
 * 控制登录的拦截器
 *
 * @author Jani
 */
public class LoginCheckInterceptor extends HandlerInterceptorAdapter {

	/**
	 * 进入某个方法前的处理
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
//		StringBuffer url = request.getRequestURL();
//		System.out.println(url);
		// 1.获取访问的方法
		if(handler instanceof HandlerMethod){
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			// 2.判断方法上有没有贴标签@RequiredLogin
			RequiredLogin anno = handlerMethod.getMethodAnnotation(RequiredLogin.class);
			if(anno==null){
				// 2.1如果没有贴@RequiredLogin标签,放行
				return true;
			}
			// 2.2贴了@RequiredLogin标签,判断是否登陆,登陆的话进行放行
			Logininfo info = UserContext.getCurrent();
			if(info!=null){
				return true;
			}else{
				//登陆控制,跳转到登陆页面
				response.sendRedirect("/login.html");
				return false;
			}
		}
		return true;
	}
}
