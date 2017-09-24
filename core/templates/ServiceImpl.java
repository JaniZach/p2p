package ${packageName}.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ${packageName}.domain.${className};
import ${packageName}.mapper.${className}Mapper;
import ${packageName}.page.PageResult;
import ${packageName}.query.QueryObject;
import ${packageName}.service.I${className}Service;
@Service
public class ${className}ServiceImpl implements I${className}Service {
	@Autowired
	private ${className}Mapper ${objectName}Mapper;
	
	public int deleteByPrimaryKey(Long id) {
		return ${objectName}Mapper.deleteByPrimaryKey(id);
	}

	public int insert(${className} record) {
		return ${objectName}Mapper.insert(record);
	}

	public ${className} selectByPrimaryKey(Long id) {
		return ${objectName}Mapper.selectByPrimaryKey(id);
	}

	public List<${className}> selectAll() {
		return ${objectName}Mapper.selectAll();
	}

	public int updateByPrimaryKey(${className} record) {
		return ${objectName}Mapper.updateByPrimaryKey(record);
	}

	@Override
	public PageResult queryPage(QueryObject qo) {
		int count = ${objectName}Mapper.queryPageCount(qo);
		if(count<=0){
			return PageResult.empty(qo.getPageSize());
		}
		List<${className}> result = ${objectName}Mapper.queryPageData(qo);
		PageResult pageResult = new PageResult(result,count,qo.getCurrentPage(),qo.getPageSize());
		return pageResult;
	}
}
