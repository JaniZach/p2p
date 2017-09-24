package com.xmg.p2p.base.util;

/**
 * 用户状态类，记录用户在平台使用系统中所有的状态。
 */
public class BitStatesUtils {
	public final static long OP_BIND_PHONE = 1L << 0; // 用户绑定手机状态码
	public final static long OP_BIND_EMAIL = 1L << 1; // 用户绑定邮箱状态码
	public static final long OP_BASIC_INFO = 1L << 2; // 用户填写基本信息的状态码
	public static final long OP_REAL_AUTH = 1L << 3; // 用户进行实名认证的状态码
	public static final long OP_VEDIO_AUTH = 1L << 4; // 用户进行视频认证的状态码
	public static final long OP_HAS_BIDREQUEST_PROCESS = 1L << 5; // 用户是否有一个借款正在处理流程中
	public static final long OP_BIND_BANK = 1L << 6; // 用户是否绑定银行卡

	/**
	 * @param states
	 *            所有状态值
	 * @param value
	 *            需要判断状态值
	 * @return 是否存在
	 */
	public static boolean hasState(long states, long value) {
		return (states & value) != 0;
	}

	/**
	 * @param states
	 *            已有状态值
	 * @param value
	 *            需要添加状态值
	 * @return 新的状态值
	 */
	public static long addState(long states, long value) {
		if (hasState(states, value)) {
			return states;
		}
		return (states | value);
	}

	/**
	 * @param states
	 *            已有状态值
	 * @param value
	 *            需要删除状态值
	 * @return 新的状态值
	 */
	public static long removeState(long states, long value) {
		if (!hasState(states, value)) {
			return states;
		}
		return states ^ value;
	}
}
