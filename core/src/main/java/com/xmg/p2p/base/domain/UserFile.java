package com.xmg.p2p.base.domain;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import lombok.Getter;
import lombok.Setter;

/**
 * 风控材料的实体类
 * @author Jani
 */
@Setter@Getter
public class UserFile  extends BaseAuditDomain{
	private String remark;//备注
	private int score;//风控分数
	private String img;//图片地址
	private SystemDictionaryItem fileType;//风控材料类型（数据字典）
	
	public String getJsonString(){
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("id", this.id);
		json.put("applier",this.applier.getUsername());
		json.put("fileType",this.fileType.getTitle());
		json.put("img", this.img);
		return JSON.toJSONString(json);
	}
}
