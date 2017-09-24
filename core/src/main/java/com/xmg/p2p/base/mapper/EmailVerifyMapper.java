package com.xmg.p2p.base.mapper;

import com.xmg.p2p.base.domain.EmailVerify;

public interface EmailVerifyMapper {
    int insert(EmailVerify record);
    EmailVerify selectByUUID(String uuid);
}