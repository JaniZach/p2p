package com.xmg.p2p.base.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xmg.p2p.base.domain.SystemDictionary;
import com.xmg.p2p.base.mapper.SystemDictionaryMapper;
import com.xmg.p2p.base.page.PageResult;
import com.xmg.p2p.base.query.SystemDictionaryQueryObject;
import com.xmg.p2p.base.service.ISystemDictionaryService;
@Service
public class SystemDictionaryServiceImpl implements ISystemDictionaryService {
	@Autowired
	private SystemDictionaryMapper systemDictionaryMapper;

	public int insert(SystemDictionary record) {
		return systemDictionaryMapper.insert(record);
	}

	public SystemDictionary selectByPrimaryKey(Long id) {
		return systemDictionaryMapper.selectByPrimaryKey(id);
	}

	public int updateByPrimaryKey(SystemDictionary record) {
		return systemDictionaryMapper.updateByPrimaryKey(record);
	}

	@Override
	public PageResult queryPage(SystemDictionaryQueryObject qo) {
		Long count = systemDictionaryMapper.queryPageCount(qo);
		if(count ==0){
			return PageResult.empty(qo.getPageSize());
		}
		List<SystemDictionary> listData = systemDictionaryMapper.queryPageData(qo);
		return new PageResult(listData, count.intValue(), qo.getCurrentPage(), qo.getPageSize());
	}

	@Override
	public void saveOrUpdate(SystemDictionary systemDictionary) {
		if(systemDictionary.getId()==null){
			systemDictionaryMapper.insert(systemDictionary);
		}else{
			systemDictionaryMapper.updateByPrimaryKey(systemDictionary);
		}
	}

	@Override
	public List<SystemDictionary> selectAll() {
		return systemDictionaryMapper.selectAll();
	}
}
