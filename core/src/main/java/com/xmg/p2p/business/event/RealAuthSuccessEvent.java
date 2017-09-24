package com.xmg.p2p.business.event;

import com.xmg.p2p.base.domain.RealAuth;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;

/**
 * 实名认证成功的事件
 */
@Getter
public class RealAuthSuccessEvent extends ApplicationEvent {
    //这里只提供getter方法即可,realAuth的值通过构造器注入
    private RealAuth realAuth;
    public RealAuthSuccessEvent(Object source, RealAuth realAuth) {
        super(source);
        this.realAuth = realAuth;
    }
}
