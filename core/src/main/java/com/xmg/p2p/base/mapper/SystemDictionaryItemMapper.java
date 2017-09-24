package com.xmg.p2p.base.mapper;

import java.util.List;

import com.xmg.p2p.base.domain.SystemDictionary;
import com.xmg.p2p.base.domain.SystemDictionaryItem;
import com.xmg.p2p.base.query.QueryObject;

public interface SystemDictionaryItemMapper {
    int insert(SystemDictionaryItem record);
    SystemDictionaryItem selectByPrimaryKey(Long id);
    int updateByPrimaryKey(SystemDictionaryItem record);
    /**
     * 根据sn查询词典明细
     * @param sn
     * @return
     */
	List<SystemDictionaryItem> selectBySn(String sn);
	Long queryPageCount(QueryObject qo);
	List<SystemDictionary> queryPageData(QueryObject qo);
}