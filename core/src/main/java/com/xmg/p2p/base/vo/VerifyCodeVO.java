package com.xmg.p2p.base.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 手机验证相关的VO对象
 * @author Jani
 */
@Setter@Getter
public class VerifyCodeVO implements Serializable {
	//放入到session中的对象,必须实现Seriliable接口
	private static final long serialVersionUID = 1L;
	private String phoneNumber;//验证手机号码
	private String verifyCode;//验证码
	private Date sentTime;//发送时间
}
