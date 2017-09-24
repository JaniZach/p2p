package com.xmg.p2p.base.page;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
@Setter@Getter
public class PageResult {
	private List<?> listData;// 当前页的结果集数据:查询
	private Integer totalCount;// 总数据条数:查询

	private Integer currentPage = 1;
	private Integer pageSize = 10;

	private Integer prevPage;// 上一页
	private Integer nextPage;// 下一页
	private Integer totalPage;// 总页数

	// 如果总数据条数为0,返回一个空集
	public static PageResult empty(Integer pageSize) {
		return new PageResult(new ArrayList<>(), 0, 1, pageSize);
	}
	public PageResult(List<?> listData, Integer totalCount, Integer currentPage,
					  Integer pageSize) {
		this.listData = listData;
		this.totalCount = totalCount;
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		// ----------------------------------------
		this.totalPage = this.totalCount % this.pageSize == 0 ? this.totalCount / this.pageSize : this.totalCount / this.pageSize + 1;
		this.prevPage = this.currentPage - 1 >= 1 ? this.currentPage - 1 : 1;
		this.nextPage = this.currentPage + 1 <= this.totalPage ? this.currentPage + 1 : this.totalPage;
	}
	//处理总页数为0,而当前页为1的问题:总页数至少为1
	public Integer getTotalPage(){
		return this.totalPage==0?1:this.totalPage;
	}
}
