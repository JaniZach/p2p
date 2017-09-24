package com.xmg.p2p.base.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xmg.p2p.base.domain.SystemDictionary;
import com.xmg.p2p.base.domain.SystemDictionaryItem;
import com.xmg.p2p.base.mapper.SystemDictionaryItemMapper;
import com.xmg.p2p.base.page.PageResult;
import com.xmg.p2p.base.query.QueryObject;
import com.xmg.p2p.base.service.ISystemDictionaryItemService;
@Service
public class SystemDictionaryItemServiceImpl implements ISystemDictionaryItemService {
	@Autowired
	private SystemDictionaryItemMapper systemDictionaryItemMapper;

	public int insert(SystemDictionaryItem record) {
		return systemDictionaryItemMapper.insert(record);
	}

	public SystemDictionaryItem selectByPrimaryKey(Long id) {
		return systemDictionaryItemMapper.selectByPrimaryKey(id);
	}

	public int updateByPrimaryKey(SystemDictionaryItem record) {
		return systemDictionaryItemMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<SystemDictionaryItem> selectBySn(String sn) {
		return systemDictionaryItemMapper.selectBySn(sn);
	}

	@Override
	public PageResult queryPage(QueryObject qo) {
		Long count = systemDictionaryItemMapper.queryPageCount(qo);
		if(count ==0){
			return PageResult.empty(qo.getPageSize());
		}
		List<SystemDictionary> listData = systemDictionaryItemMapper.queryPageData(qo);
		return new PageResult(listData, count.intValue(), qo.getCurrentPage(), qo.getPageSize());
	}

	@Override
	public void saveOrUpdate(SystemDictionaryItem systemDictionaryItem) {
		if(systemDictionaryItem.getId()==null){
			systemDictionaryItemMapper.insert(systemDictionaryItem);
		}else{
			systemDictionaryItemMapper.updateByPrimaryKey(systemDictionaryItem);
		}
	}
}
