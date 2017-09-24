package com.xmg.p2p.base.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xmg.p2p.base.domain.UserFile;
import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.query.UserFileQueryObject;

/**
 * 风控材料处理mapper
 * @author Jani
 */
public interface UserFileMapper {
    int insert(UserFile record);
    UserFile selectByPrimaryKey(Long id);
    List<UserFile> selectAll();
    int updateByPrimaryKey(UserFile record);
    /**
     * 查询风控材料集合
     * @param id 前台用户id
     * @param b 是否选择封孔材料分类
     * @return
     */
	List<Userinfo> queryUserFiles(@Param("userId") Long id,@Param("choice") boolean b);
	int queryCount(UserFileQueryObject qo);
	List<UserFile> queryListData(UserFileQueryObject qo);
	/**
	 * 根据用户id,查询所拥有的全部经过审核的风控资料
	 * @param userId
	 * @return
	 */
	List<UserFile> queryUserFileAudit(Long userId);
}