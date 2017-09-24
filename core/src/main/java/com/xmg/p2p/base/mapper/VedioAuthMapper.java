package com.xmg.p2p.base.mapper;

import java.util.List;
import java.util.Map;

import com.xmg.p2p.base.domain.VedioAuth;
import com.xmg.p2p.base.query.QueryObject;

/**
 * 视频认证mapper
 * @author Jani
 */
public interface VedioAuthMapper {
    int insert(VedioAuth record);
    VedioAuth selectByPrimaryKey(Long id);
	Long queryPageCount(QueryObject qo);
	List<VedioAuth> queryPageData(QueryObject qo);
}