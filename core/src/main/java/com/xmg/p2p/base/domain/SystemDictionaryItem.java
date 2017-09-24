package com.xmg.p2p.base.domain;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import lombok.Getter;
import lombok.Setter;

/**
 * 数据词典相关明细
 * @author Jani
 */
@Setter@Getter
public class SystemDictionaryItem  extends BaseDomain{
	private Long parentId;//数据字典明细对应的分类
	private String title;//数据字典明细显示名称
	private Integer sequence;//数据字典明细在该分类中的排序
	
	public String getJsonString(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", this.id);
		map.put("parentId", this.parentId);
		map.put("title", this.title);
		map.put("sequence", this.sequence);
		return JSON.toJSONString(map);
	}
}
