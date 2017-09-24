package ${packageName}.service;
import java.util.List;
import ${packageName}.domain.${className};
import ${packageName}.page.PageResult;
import ${packageName}.query.QueryObject;

public interface I${className}Service {
	int deleteByPrimaryKey(Long id);
    int insert(${className} record);
    ${className} selectByPrimaryKey(Long id);
    List<${className}> selectAll();
    int updateByPrimaryKey(${className} record);
	PageResult queryPage(QueryObject qo);
}
