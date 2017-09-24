package com.xmg.p2p.base.service;
import com.xmg.p2p.base.domain.Userinfo;

public interface IUserinfoService {
    int insert(Userinfo record);
    Userinfo selectByPrimaryKey(Long id);
    int updateByPrimaryKey(Userinfo record);
    /**
     * 获取当前用户id的方法
     * @return
     */
    Userinfo getCurrent();
    /**
     * 为用户绑定手机的方法
     * @param phoneNumber 绑定的手机号码
     * @param verifyCode 手机验证码
     */
	void bindPhone(String phoneNumber, String verifyCode);
	/**
	 * 为用户绑定邮箱的方法
	 * @param key 绑定邮箱传来的key
	 */
	void bindEmail(String key);
	/**
	 * 为用户保存基本信息的方法
	 * @param userinfo
	 */
	void saveBasicInfo(Userinfo userinfo);
}
