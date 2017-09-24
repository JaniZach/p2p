package com.xmg.p2p.base.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.xmg.p2p.base.domain.SystemDictionaryItem;
import com.xmg.p2p.base.domain.UserFile;
import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.mapper.UserFileMapper;
import com.xmg.p2p.base.page.PageResult;
import com.xmg.p2p.base.query.UserFileQueryObject;
import com.xmg.p2p.base.service.IUserFileService;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.util.UploadUtil;
import com.xmg.p2p.base.util.UserContext;
@Service
public class UserFileServiceImpl implements IUserFileService {

	@Autowired
	private UserFileMapper userFileMapper;
	@Autowired
	private IUserinfoService userinfoService;
	@Autowired
	private ServletContext ctx;
	@Override
	public String uploadFile(MultipartFile img) {
		String basePath = ctx.getRealPath("/upload");
		String fileName = UploadUtil.upload(img, basePath );
		UserFile file = new UserFile();
		file.setApplier(UserContext.getCurrent());
		file.setApplyTime(new Date());
		file.setImg("/upload/"+fileName);
		//保存风控材料对象
		this.userFileMapper.insert(file);
		//返回上传风控材料的相对路径
		return "/upload/"+fileName;
	}
	@Override
	public void choiceUserFile(Long[] ids, Long[] fileTypes) {
		//风控材料对象id,选择分类id符合要求时,再进入判断
		if(ids!=null && fileTypes!=null && ids.length==fileTypes.length){
			for (int i = 0; i < ids.length; i++) {
				UserFile userFile = this.userFileMapper.selectByPrimaryKey(ids[i]);
				if(userFile!=null && userFile.getState()==UserFile.STATE_NORMAL){
					SystemDictionaryItem item = new SystemDictionaryItem();
					item.setId(fileTypes[i]);
					userFile.setFileType(item);//设置风控材料分类
					this.userFileMapper.updateByPrimaryKey(userFile);
				}
			}
		}
	}
	@Override
	public PageResult queryPage(UserFileQueryObject qo) {
		int count = this.userFileMapper.queryCount(qo);
		if(count == 0){
			return PageResult.empty(qo.getPageSize());
		}
		List<UserFile> listData = this.userFileMapper.queryListData(qo);
		return new PageResult(listData, count, qo.getCurrentPage(), qo.getPageSize());
	}
	@Override
	public void auditUserFile(Long id, String remark, int score, int state) {
		//当前风控资料处于normal状态,才可以审核
		UserFile userFile = this.userFileMapper.selectByPrimaryKey(id);
		if(userFile!=null && userFile.getState()==UserFile.STATE_NORMAL){
			userFile.setRemark(remark);
			userFile.setState(state);
			userFile.setAuditor(UserContext.getCurrent());
			userFile.setAuditTime(new Date());
			if(state==UserFile.STATE_PASS){
				//审核通过的话,设置审核对象审核份数,累加用户风控份数
				userFile.setScore(score);
				Userinfo userinfo = this.userinfoService.selectByPrimaryKey(userFile.getApplier().getId());
				userinfo.setScore(userinfo.getScore() + score);
				this.userinfoService.updateByPrimaryKey(userinfo);//更新用户风控分数
			}
			this.userFileMapper.updateByPrimaryKey(userFile);
		}
	}
	@Override
	public List<Userinfo> queryUserFiles(Long id, boolean b) {
		return this.userFileMapper.queryUserFiles(id,b);
	}
	@Override
	public List<UserFile> queryUserFileAudit(Long userId) {
		return this.userFileMapper.queryUserFileAudit(userId);
	}
}
