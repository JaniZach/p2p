package com.xmg.p2p.base.query;

import freemarker.template.utility.StringUtil;
import lombok.Getter;
import lombok.Setter;

@Setter@Getter
public class SystemDictionaryItemQueryObject extends QueryObject {
	private String keyword;//关键字
	private Long parentId = -1L;//根据数据字典目录查询
	public String getKeyword(){
		return StringUtil.emptyToNull(keyword);
	}
}
