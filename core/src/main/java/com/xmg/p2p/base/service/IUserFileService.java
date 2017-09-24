package com.xmg.p2p.base.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.xmg.p2p.base.domain.UserFile;
import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.page.PageResult;
import com.xmg.p2p.base.query.UserFileQueryObject;

public interface IUserFileService {

	/**
	 * 风控材料上传，保存的方法
	 * @param img 风控材料
	 * @return 保存到服务器的相对路径
	 */
	String uploadFile(MultipartFile img);

	/**
	 * 查询前台用户所拥有的风控材料
	 * @param id 前台用户id
	 * @param b	是否选择风控材料分类
	 * @return	风控材料集合
	 */
	List<Userinfo> queryUserFiles(Long id, boolean b);

	/**
	 * 处理提交分类的风控材料
	 * @param id
	 * @param fileType
	 */
	void choiceUserFile(Long[] ids, Long[] fileTypes);

	PageResult queryPage(UserFileQueryObject qo);

	/**
	 * 后台风控资料审核的方法
	 * @param id 风控文件id
	 * @param remark	审核备注
	 * @param score		分控资料份数
	 * @param state		风控资料审核状态
	 */
	void auditUserFile(Long id, String remark, int score, int state);

	/**
	 * 后台根据用户id查询申请用户所拥有的全部通过审核的风控资料
	 * @param userId 用户id
	 * @return
	 */
	List<UserFile> queryUserFileAudit(Long userId);
}
