package com.xmg.p2p.base.service;
import java.util.List;
import java.util.Map;

import com.xmg.p2p.base.domain.VedioAuth;
import com.xmg.p2p.base.page.PageResult;
import com.xmg.p2p.base.query.QueryObject;

/**
 * 视频认证的接口
 * @author Jani
 */
public interface IVedioAuthService {
    int insert(VedioAuth record);
    VedioAuth selectByPrimaryKey(Long id);
	PageResult queryPage(QueryObject qo);
	/**
	 * 视频认证的方法
	 * @param loginInfoValue 视频认证的申请用户id
	 * @param remark	视频认证审核备注
	 * @param state		视频认证审核状态
	 */
	void audit(Long loginInfoValue, String remark, int state);
}
