package com.xmg.p2p.business.service.impl;

import com.xmg.p2p.base.domain.RealAuth;
import com.xmg.p2p.business.service.ISendMsgService;
import org.springframework.stereotype.Service;

@Service
public class SendMsgServiceImpl implements ISendMsgService {
    @Override
    public void sendRealAuthSuccessMsg(RealAuth realAuth) {
        System.out.println("=========================");
        System.out.println("验证成功,发送给"+realAuth.getApplier().getUsername()+"短信...");
        System.out.println("=========================");
    }
}
