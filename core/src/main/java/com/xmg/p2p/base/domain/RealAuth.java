package com.xmg.p2p.base.domain;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import lombok.Getter;
import lombok.Setter;

@Setter@Getter
public class RealAuth  extends BaseAuditDomain{
	public static final int SEX_MALE = 0;//性别男
    public static final int SEX_FEMALE = 1;//性别女
    private String realName;//真实姓名
    private int sex;//性别
    private String idNumber;//身份证
    private String bornDate;//出生日期
    private String address;//身份证地址
    private String image1;//身份证正面
    private String image2;//身份证反面
    private String remark;//备注
    public String getSexDisplay(){
    	return this.sex==SEX_MALE?"男":"女";
    }
    //将RealAuth对象相关数据转json格式,绑定到 审核 按钮上,用于数据回显
    public String getJsonString(){
    	Map<String, Object> json = new HashMap<String, Object>();
    	json.put("id", this.id);
    	json.put("username", this.applier.getUsername());
    	json.put("realName", this.realName);
    	json.put("idNumber", this.idNumber);
    	json.put("sex", this.sex==SEX_MALE?"男":"女");
    	json.put("bornDate", this.bornDate);
    	json.put("address", this.address);
    	json.put("image1", this.image1);
    	json.put("image2", this.image2);
    	return JSON.toJSONString(json);
    }
}
