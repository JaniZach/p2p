package com.xmg.p2p.base.mapper;

import java.util.List;

import com.xmg.p2p.base.domain.SystemDictionary;
import com.xmg.p2p.base.query.QueryObject;

public interface SystemDictionaryMapper {
    int insert(SystemDictionary record);
    SystemDictionary selectByPrimaryKey(Long id);
    int updateByPrimaryKey(SystemDictionary record);
	Long queryPageCount(QueryObject qo);
	List<SystemDictionary> queryPageData(QueryObject qo);
	List<SystemDictionary> selectAll();
}