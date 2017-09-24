package com.xmg.p2p.base.query;

import freemarker.template.utility.StringUtil;
import lombok.Getter;
import lombok.Setter;

@Setter@Getter
public class SystemDictionaryQueryObject extends QueryObject {
	private String keyword;//关键字
	public String getKeyword(){
		return StringUtil.emptyToNull(keyword);
	}
}
