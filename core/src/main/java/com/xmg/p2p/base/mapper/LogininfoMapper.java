package com.xmg.p2p.base.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.xmg.p2p.base.domain.Logininfo;

public interface LogininfoMapper {
    int insert(Logininfo record);

    Logininfo selectByPrimaryKey(Long id);

    int updateByPrimaryKey(Logininfo record);

	int queryLogininfoByUsername(String username);

	Logininfo login(@Param("username")String username,@Param("password") String password,@Param("userType") int userType);

	int queryManagerCount(int userType);

	/**
	 * 自动补全的方法 typeahead
	 * @param keyword
	 * @return
	 */
	List<Map<String, Object>> queryArray4Autocomplate(String keyword);
}