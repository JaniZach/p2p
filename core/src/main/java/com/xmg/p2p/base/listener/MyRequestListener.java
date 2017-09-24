package com.xmg.p2p.base.listener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

import com.xmg.p2p.base.util.UserContext;

/**
 * 自定义监听器,获取当前请求
 * @author Jani
 *
 */
public class MyRequestListener implements ServletRequestListener {
	/**
	 * request初始化的方法
	 */
	@Override
	public void requestInitialized(ServletRequestEvent sre) {
		//获取当前请求对象
		HttpServletRequest request = (HttpServletRequest)sre.getServletRequest();
		//将当前请求对象放在ThreadLocal中
		UserContext.lcoal.set(request);
	}
	
	/**
	 * request销毁时的方法
	 */
	@Override
	public void requestDestroyed(ServletRequestEvent sre) {
		
	}
}
