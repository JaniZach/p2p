package com.xmg.p2p.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xmg.p2p.base.domain.EmailVerify;
import com.xmg.p2p.base.mapper.EmailVerifyMapper;
import com.xmg.p2p.base.service.IEmailVerifyService;
@Service
public class EmailVerifyServiceImpl implements IEmailVerifyService {
	@Autowired
	private EmailVerifyMapper emailVerifyMapper;

	public int insert(EmailVerify record) {
		return emailVerifyMapper.insert(record);
	}

	public EmailVerify selectByUUID(String uuid) {
		return emailVerifyMapper.selectByUUID(uuid);
	}
}
