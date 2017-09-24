package com.xmg.p2p.base.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xmg.p2p.base.domain.Logininfo;
import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.domain.VedioAuth;
import com.xmg.p2p.base.mapper.VedioAuthMapper;
import com.xmg.p2p.base.page.PageResult;
import com.xmg.p2p.base.query.QueryObject;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.service.IVedioAuthService;
import com.xmg.p2p.base.util.BitStatesUtils;
import com.xmg.p2p.base.util.UserContext;
@Service
public class VedioAuthServiceImpl implements IVedioAuthService {
	@Autowired
	private VedioAuthMapper vedioAuthMapper;
	@Autowired
	private IUserinfoService userinfoService;
	
	public int insert(VedioAuth record) {
		return vedioAuthMapper.insert(record);
	}

	public VedioAuth selectByPrimaryKey(Long id) {
		return vedioAuthMapper.selectByPrimaryKey(id);
	}

	@Override
	public PageResult queryPage(QueryObject qo) {
		Long count = vedioAuthMapper.queryPageCount(qo);
		if(count<=0){
			return PageResult.empty(qo.getPageSize());
		}
		List<VedioAuth> result = vedioAuthMapper.queryPageData(qo);
		PageResult pageResult = new PageResult(result,count.intValue(),qo.getCurrentPage(),qo.getPageSize());
		return pageResult;
	}

	@Override
	public void audit(Long userId, String remark, int state) {
		//进行视频的条件:申请用户没有进行视频认证
		Userinfo userinfo = this.userinfoService.selectByPrimaryKey(userId);
		if(userinfo!=null && !userinfo.getHasVedioAuth()){
			VedioAuth vedioAuth = new VedioAuth();
			Logininfo logininfo = new Logininfo();
			logininfo.setId(userId);
			vedioAuth.setApplier(logininfo);
			vedioAuth.setApplyTime(new Date());
			vedioAuth.setAuditor(UserContext.getCurrent());
			vedioAuth.setAuditTime(new Date());
			vedioAuth.setState(state);
			vedioAuth.setRemark(remark);
			//保存视频认证对象信息
			this.vedioAuthMapper.insert(vedioAuth);
			//更新用户视频认证状态码
			userinfo.addState(BitStatesUtils.OP_VEDIO_AUTH);
			this.userinfoService.updateByPrimaryKey(userinfo);
		}
	}
}
