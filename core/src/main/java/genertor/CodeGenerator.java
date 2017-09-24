package genertor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;

import com.xmg.p2p.business.domain.UserBankinfo;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class CodeGenerator {
	private static Configuration conf = new Configuration(Configuration.VERSION_2_3_23);

	static {
		try {
			// 设置文件模板目录在什么地方
			conf.setDirectoryForTemplateLoading(new File("templates"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
//		codeGenerator(UserBankinfo.class);
		System.out.println("执行完毕。。。。。。");
	}

	private static void codeGenerator(Class<?> clazz) throws Exception {
		// 数据模型
		ClassInfo classInfo = new ClassInfo(clazz);
		// 生成Mapper
		createFile("Mapper.java", "src/main/java/{0}/mapper/{1}Mapper.java", classInfo);
		createFile("Mapper.xml", "src/main/resources/{0}/mapper/{1}Mapper.xml", classInfo);
		// 生成Service
		createFile("IService.java", "src/main/java/{0}/service/I{1}Service.java", classInfo);
		createFile("ServiceImpl.java", "src/main/java/{0}/service/impl/{1}ServiceImpl.java", classInfo);
		// 生成QueryObject
		// createFile("QueryObject.java","src/main/java/{0}/query/QueryObject.java",classInfo,true);
		createFile("ObjQueryObject.java", "src/main/java/{0}/query/{1}QueryObject.java", classInfo);
		// 生成PageResult
		// createFile("PageResult.java","src/main/java/{0}/page/PageResult.java",classInfo,true);
	}

	/**
	 *
	 * @param templateName
	 *            模板文件名称
	 * @param outPath
	 *            文件输出路径
	 * @param classInfo
	 *            数据模型
	 */
	private static void createFile(String templateName, String outPath, ClassInfo classInfo) throws Exception {
		createFile(templateName, outPath, classInfo, false);
	}

	/**
	 *
	 * @param templateName
	 * @param outPath
	 * @param classInfo
	 * @throws Exception
	 */
	private static void createFile(String templateName, String outPath, ClassInfo classInfo, boolean notReplace)
			throws Exception {
		Template temp = conf.getTemplate(templateName);
		outPath = MessageFormat.format(outPath, classInfo.getPackageName().replace(".", "/"), classInfo.getClassName(),
				classInfo.getObjectName());
		File targetFile = new File(outPath);
		// 创建目录
		if (!targetFile.getParentFile().exists()) {
			targetFile.getParentFile().mkdirs();
		}
		// 如果已经存在,且标记不覆盖
		if (targetFile.exists() && notReplace) {
			return;
		}
		temp.process(classInfo, new FileWriter(targetFile));
	}
}
