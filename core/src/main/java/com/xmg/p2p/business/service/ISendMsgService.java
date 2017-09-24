package com.xmg.p2p.business.service;

import com.xmg.p2p.base.domain.RealAuth;

/**
 * 发送短信的服务
 */
public interface ISendMsgService {

    /**
     * 实名认证成功,发送短信的方法
     */
    void sendRealAuthSuccessMsg(RealAuth realAuth);
}
