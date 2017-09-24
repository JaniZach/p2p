package com.xmg.p2p.base.service;
import java.util.List;

import com.xmg.p2p.base.domain.SystemDictionary;
import com.xmg.p2p.base.page.PageResult;
import com.xmg.p2p.base.query.SystemDictionaryQueryObject;

public interface ISystemDictionaryService {
    int insert(SystemDictionary record);
    SystemDictionary selectByPrimaryKey(Long id);
    int updateByPrimaryKey(SystemDictionary record);
	PageResult queryPage(SystemDictionaryQueryObject qo);
	void saveOrUpdate(SystemDictionary systemDictionary);
	List<SystemDictionary> selectAll();
}
