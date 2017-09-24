package ${packageName}.mapper;

import ${packageName}.domain.${className};
import ${packageName}.query.QueryObject;
import java.util.List;

public interface ${className}Mapper {
    int deleteByPrimaryKey(Long id);
    int insert(${className} record);
    ${className} selectByPrimaryKey(Long id);
    List<${className}> selectAll();
    int updateByPrimaryKey(${className} record);
	int queryPageCount(QueryObject qo);
	List<${className}> queryPageData(QueryObject qo);
}