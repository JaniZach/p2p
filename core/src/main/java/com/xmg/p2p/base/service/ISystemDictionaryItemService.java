package com.xmg.p2p.base.service;
import java.util.List;

import com.xmg.p2p.base.domain.SystemDictionaryItem;
import com.xmg.p2p.base.page.PageResult;
import com.xmg.p2p.base.query.QueryObject;

public interface ISystemDictionaryItemService {
    int insert(SystemDictionaryItem record);
    SystemDictionaryItem selectByPrimaryKey(Long id);
    int updateByPrimaryKey(SystemDictionaryItem record);
    /**
     * 根据词典目录sn查询词典明细
     * @param sn
     * @return
     */
	List<SystemDictionaryItem> selectBySn(String sn);
	PageResult queryPage(QueryObject qo);
	void saveOrUpdate(SystemDictionaryItem systemDictionaryItem);
}
