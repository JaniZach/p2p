package com.xmg.p2p.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xmg.p2p.base.page.PageResult;
import com.xmg.p2p.base.query.QueryObject;
import com.xmg.p2p.business.domain.PlatformBankinfo;
import com.xmg.p2p.business.mapper.PlatformBankinfoMapper;
import com.xmg.p2p.business.service.IPlatformBankinfoService;
@Service
public class PlatformBankinfoServiceImpl implements IPlatformBankinfoService {
	@Autowired
	private PlatformBankinfoMapper platformBankinfoMapper;
	
	public int deleteByPrimaryKey(Long id) {
		return platformBankinfoMapper.deleteByPrimaryKey(id);
	}

	public PlatformBankinfo selectByPrimaryKey(Long id) {
		return platformBankinfoMapper.selectByPrimaryKey(id);
	}

	@Override
	public PageResult queryPage(QueryObject qo) {
		int count = platformBankinfoMapper.queryPageCount(qo);
		if(count<=0){
			return PageResult.empty(qo.getPageSize());
		}
		List<PlatformBankinfo> result = platformBankinfoMapper.queryPageData(qo);
		PageResult pageResult = new PageResult(result,count,qo.getCurrentPage(),qo.getPageSize());
		return pageResult;
	}

	@Override
	public void saveOrUpdate(PlatformBankinfo bankinfo) {
		PlatformBankinfo b = new PlatformBankinfo();
		b.setAccountName(bankinfo.getAccountName());
		b.setAccountNumber(bankinfo.getAccountNumber());
		b.setBankName(bankinfo.getBankName());
		b.setForkName(bankinfo.getForkName());
		if(bankinfo.getId()!=null){
			//执行更新操作
			b.setId(bankinfo.getId());
			this.platformBankinfoMapper.updateByPrimaryKey(b);
		}else{
			//执行保存操作
			this.platformBankinfoMapper.insert(b);
		}
	}

	@Override
	public List<PlatformBankinfo> selectAll() {
		return this.platformBankinfoMapper.selectAll();
	}
	
}
