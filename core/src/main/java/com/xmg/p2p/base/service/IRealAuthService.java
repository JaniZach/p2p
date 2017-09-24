package com.xmg.p2p.base.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.xmg.p2p.base.domain.RealAuth;
import com.xmg.p2p.base.page.PageResult;
import com.xmg.p2p.base.query.QueryObject;

public interface IRealAuthService {
    int deleteByPrimaryKey(Long id);

    int insert(RealAuth record);

    RealAuth selectByPrimaryKey(Long id);

    List<RealAuth> selectAll();

    int updateByPrimaryKey(RealAuth record);

    PageResult queryPage(QueryObject qo);

    /**
     * 实名认证信息填写提交的方法
     *
     * @param realAuth
     */
    void save(RealAuth realAuth);

    /**
     * 实名认证的审核方法
     *
     * @param remark 审核备注
     * @param state  审核状态
     * @param id     认证对象id
     */
    void audit(Long id, int state, String remark);

    /**
     * 前台实名认证图片上传的处理
     *
     * @param img 认证图片
     * @return 返回图片上传后的相对路径
     */
    String uploadImg(MultipartFile img);
}
