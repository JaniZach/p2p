package com.xmg.p2p.base.domain;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import lombok.Getter;
import lombok.Setter;

/**
 * 数据词典相关实体类
 * @author Jani
 *
 */
@Setter@Getter
public class SystemDictionary  extends BaseDomain{
	private String sn;//数据字典分类编码
	private String title;//数据字典分类名称
	
	public String getJsonString(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", this.id);
		map.put("sn", this.sn);
		map.put("title", this.title);
		return JSON.toJSONString(map);
	}
}
