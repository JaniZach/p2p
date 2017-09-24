package com.xmg.p2p.mgrsite.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.xmg.p2p.business.service.ISystemAccountService;

/**
 * 利用Spring的监听机制,创建平台账户
 */
@Component
public class InitSystemAccountListener implements ApplicationListener<ContextRefreshedEvent>{
	@Autowired
	private ISystemAccountService systemAccountService;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		//容器创建时,初始化系统账户
		this.systemAccountService.initSystemAccount();
	}
}
