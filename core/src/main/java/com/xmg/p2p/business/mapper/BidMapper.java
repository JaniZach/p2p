package com.xmg.p2p.business.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xmg.p2p.base.query.QueryObject;
import com.xmg.p2p.business.domain.Bid;

/**
 * 投标相关的mapper
 * @author Jani
 */
public interface BidMapper {
    int insert(Bid record);
    Bid selectByPrimaryKey(Long id);
    int updateByPrimaryKey(Bid record);
	Long queryPageCount(QueryObject qo);
	List<Bid> queryPageData(QueryObject qo);
	/**
	 * 将某一个借款标包含的投资标状态统一修改的方法
	 * @param rId 借款标id
	 * @param state 统一修改后的状态
	 */
	void updateState(@Param("rId")Long rId,@Param("state") int state);
}