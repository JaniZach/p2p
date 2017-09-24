package com.xmg.p2p.mgrsite.listener;

import com.xmg.p2p.business.event.RealAuthSuccessEvent;
import com.xmg.p2p.business.service.ISendMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 发布消息的事件
 */
@Component
public class PublishSmsListener implements ApplicationListener<RealAuthSuccessEvent> {
    @Autowired
    private ISendMsgService sendMsgService;

    @Override
    public void onApplicationEvent(RealAuthSuccessEvent event) {
        this.sendMsgService.sendRealAuthSuccessMsg(event.getRealAuth());
    }
}
