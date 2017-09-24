package com.xmg.p2p.base.service;
import com.xmg.p2p.base.domain.EmailVerify;

public interface IEmailVerifyService {
    int insert(EmailVerify record);
    EmailVerify selectByUUID(String uuid);
}
