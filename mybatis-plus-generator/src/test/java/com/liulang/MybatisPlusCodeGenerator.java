package com.liulang;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;

/**
 * @author wangshiyang
 * @since 2022/3/31
 * 这里的类名是可以随意定制的
 **/
public class MybatisPlusCodeGenerator {
    public static void main(String[] args) {
        // 构建一个代码自动生成器对象  万物皆对象
        AutoGenerator autoGenerator = new AutoGenerator();

        // 配置策略

        // 1、全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        // 获取当前系统目录  即项目路径
        String projectPath = System.getProperty("user.dir");
        // 设置输出路径
        globalConfig.setOutputDir(projectPath + "/mybatis-plus-generator/src/main/java");
        globalConfig.setAuthor("wangshiyang");
        // 设置是否打开资源管理器  即生成代码后弹出对应的文件夹  没啥用
        globalConfig.setOpen(false);
        globalConfig.setFileOverride(false);
        // 通常我们的服务名称前边会有一个I前缀，这里去掉这个前缀
        globalConfig.setServiceName("%sService");
        globalConfig.setServiceImplName("%sServiceImpl");
        globalConfig.setIdType(IdType.ID_WORKER);
        globalConfig.setDateType(DateType.ONLY_DATE);
        // 自动配置Swagger文档
        globalConfig.setSwagger2(false);
        // 将上边配置好的全局配置，放入到自动生成器中
        autoGenerator.setGlobalConfig(globalConfig);

        // 2、设置数据源
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl("jdbc:mysql://localhost:3306/mybatis-plus?useSSL=false&useUnicode=ture&charaterEncoding=utf-8&serverTimezone=Asia/Shanghai");
        dataSourceConfig.setDriverName("com.mysql.cj.jdbc.Driver");
        dataSourceConfig.setUsername("root");
        dataSourceConfig.setPassword("Asd19941215");
        dataSourceConfig.setDbType(DbType.MYSQL);
        // 将数据源的配置放入到自动生成器中
        autoGenerator.setDataSource(dataSourceConfig);

        // 3、设置包配置  有哪些包，分别放在什么位置
        PackageConfig packageConfig = new PackageConfig();
        // 这里就是项目模块的名称，具体可以看探花项目中的模块分类
        packageConfig.setModuleName("blog");
        // 设置类放在哪个包下  这里如：com.liulang
        packageConfig.setParent("com.liulang");
        // 上边两个配置的效果：com.liulang.blog
        packageConfig.setEntity("entity");
        packageConfig.setMapper("mapper");
        packageConfig.setService("service");
        packageConfig.setServiceImpl("service.impl");
        packageConfig.setController("controller");
        autoGenerator.setPackageInfo(packageConfig);

        // 4、策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        // 设置表名映射  配置需要映射数据库中的哪些表  可以同时生成多个表，用 "，"隔开
        // TODO 选择要映射的表
        strategyConfig.setInclude("user");
        // 支持数据库表名  下划线转驼峰命名
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        // 列名也支持下划线转驼峰命名
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);
        // 自动使用Lombok的方式创建实体类
        strategyConfig.setEntityLombokModel(true);
        // 自动使用Rest风格创建Controller(驼峰命名格式)
        strategyConfig .setRestControllerStyle(true);
        // 配置逻辑删除
        strategyConfig.setLogicDeleteFieldName("deleted");

        // 自动填充策略配置
        TableFill gmtCreate = new TableFill("gmt_create", FieldFill.INSERT);
        TableFill gmtModified = new TableFill("gmt_modified", FieldFill.INSERT_UPDATE);
        ArrayList<TableFill> tableFills = new ArrayList<>();
        tableFills.add(gmtCreate);
        tableFills.add(gmtModified);
        strategyConfig.setTableFillList(tableFills);

        // 乐观锁策略配置
        strategyConfig.setVersionFieldName("version");
        // localhost:8080/hello_id_2
        strategyConfig.setControllerMappingHyphenStyle(true);
        autoGenerator.setStrategy(strategyConfig);
        // 执行代码生成器 生成代码
        autoGenerator.execute();

    }
}
