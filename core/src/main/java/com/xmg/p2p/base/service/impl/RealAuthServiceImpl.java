package com.xmg.p2p.base.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;

import com.xmg.p2p.business.event.RealAuthSuccessEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.xmg.p2p.base.domain.RealAuth;
import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.mapper.RealAuthMapper;
import com.xmg.p2p.base.page.PageResult;
import com.xmg.p2p.base.query.QueryObject;
import com.xmg.p2p.base.service.IRealAuthService;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.util.BitStatesUtils;
import com.xmg.p2p.base.util.UploadUtil;
import com.xmg.p2p.base.util.UserContext;
@Service
public class RealAuthServiceImpl implements IRealAuthService {

	@Autowired
	private RealAuthMapper realAuthMapper;
	@Autowired
	private IUserinfoService userinfoService;
	@Autowired
	private ServletContext ctx;
	@Autowired
	private ApplicationContext applicationContext;
	@Value("${db.timeout}")
	private String key;
	
	@Override
	public String uploadImg(MultipartFile img) {
		String basePath = ctx.getRealPath("/upload");
		String fileName = UploadUtil.upload(img, basePath);
		return "/upload/" + fileName;
	}
	
	public int deleteByPrimaryKey(Long id) {
		return realAuthMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void audit(Long id, int state, String remark) {
		//1.判断用户是否已经实名认证/判断认证信息是否是待审核状态
		RealAuth realAuth = this.realAuthMapper.selectByPrimaryKey(id,key);
		if(realAuth==null){
			throw new RuntimeException("认证信息不存在");
		}
		Userinfo userinfo = this.userinfoService.selectByPrimaryKey(realAuth.getApplier().getId());
		if(userinfo.getHasRealAuth()){
			throw new RuntimeException("该用户已经实名认证");
		}
		if(realAuth.getState()!=RealAuth.STATE_NORMAL){
			throw new RuntimeException("该认证信息处于不可审核状态");
		}
		//2.进行审核操作
		realAuth.setRemark(remark);
		realAuth.setState(state);
		realAuth.setAuditor(UserContext.getCurrent());
		realAuth.setAuditTime(new Date());
		this.updateByPrimaryKey(realAuth);
		if(state==RealAuth.STATE_PASS){
			//用户添加实名认证审核通过状态码
			userinfo.addState(BitStatesUtils.OP_REAL_AUTH);
			//设置真实姓名，身份证号（两个冗余字段）
			userinfo.setRealName(realAuth.getRealName());
			userinfo.setIdNumber(realAuth.getIdNumber());
		}else{
			//设置用户实名认证id为null
			userinfo.setRealAuthId(null);
		}
		//发布实名认证的事件
		this.applicationContext.publishEvent(new RealAuthSuccessEvent(this,realAuth));
		this.userinfoService.updateByPrimaryKey(userinfo);
	}

	public int insert(RealAuth record) {
		return realAuthMapper.insert(record,key);
	}

	public RealAuth selectByPrimaryKey(Long id) {
		return realAuthMapper.selectByPrimaryKey(id,key);
	}

	public List<RealAuth> selectAll() {
		return realAuthMapper.selectAll();
	}

	public int updateByPrimaryKey(RealAuth record) {
		return realAuthMapper.updateByPrimaryKey(record,key);
	}

	@Override
	public PageResult queryPage(QueryObject qo) {
		Long count = realAuthMapper.queryPageCount(qo,key);
		if(count<=0){
			return PageResult.empty(qo.getPageSize());
		}
		List<RealAuth> result = realAuthMapper.queryPageData(qo,key);
		PageResult pageResult = new PageResult(result,count.intValue(),qo.getCurrentPage(),qo.getPageSize());
		return pageResult;
	}

	@Override
	public void save(RealAuth realAuth) {
		//只有用户没有认证的情况下才进行提交认证信息
		Userinfo userinfo = userinfoService.getCurrent();
		if(userinfo.getHasRealAuth()){
			throw new RuntimeException("该用户已完成实名认证,请勿重复操作");
		}
		if(userinfo.getRealAuthId()!=null){
			throw new RuntimeException("该用户实名认证申请正在审核中,请耐心等待");
		}
		//实名认证,只需要提交 性别,真实姓名,证件号码,出生日期,证件地址,身份证照片
		RealAuth r = new RealAuth();
		r.setSex(realAuth.getSex());
		r.setIdNumber(realAuth.getIdNumber());
		r.setRealName(realAuth.getRealName());
		r.setBornDate(realAuth.getBornDate());
		r.setAddress(realAuth.getAddress());
		r.setImage1(realAuth.getImage1());
		r.setImage2(realAuth.getImage2());
		//设置认证状态
		r.setState(RealAuth.STATE_NORMAL);
		//设置申请人
		r.setApplier(UserContext.getCurrent());
		//设置申请时间
		r.setApplyTime(new Date());
		//保存实名认证信息
		realAuthMapper.insert(r,key);
		//关联到申请用户,设置realAuthId
		userinfo.setRealAuthId(r.getId());
		userinfoService.updateByPrimaryKey(userinfo);
	}
}
