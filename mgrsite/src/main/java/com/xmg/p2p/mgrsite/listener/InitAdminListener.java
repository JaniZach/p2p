package com.xmg.p2p.mgrsite.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.xmg.p2p.base.service.ILogininfoService;

/**
 * 初始化后台管理员账号的监听器
 */
@Component
public class InitAdminListener implements ApplicationListener<ContextRefreshedEvent> {
	@Autowired
	private ILogininfoService logininfoService;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		logininfoService.initAdmin();
	}
}
