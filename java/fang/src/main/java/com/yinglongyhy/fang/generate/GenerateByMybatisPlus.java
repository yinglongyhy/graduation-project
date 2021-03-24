package com.yinglongyhy.fang.generate;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

public class GenerateByMybatisPlus {
    public static void main(String[] args) {
        String projectPath = System.getProperty("user.dir");

        GlobalConfig gc = new GlobalConfig();
        gc.setAuthor("yinglongyhy")
                .setOutputDir(projectPath + "/src/main/java")
//                .setOutputDir("/tmp")
                .setOpen(false)
                .setSwagger2(true)
                .setControllerName("%sRestController")
                .setBaseResultMap(true);

        DataSourceConfig dc = new DataSourceConfig();
        dc.setDbType(DbType.MYSQL)
                .setDriverName("com.mysql.cj.jdbc.Driver")
                .setUrl("jdbc:mysql://localhost:3307/fang?useUnicode=true&characterEncoding=utf-8")
                .setUsername("root")
                .setPassword("root");

        PackageConfig pc = new PackageConfig();
        pc.setParent("com.yinglongyhy.fang")
                .setController("rest")
                .setMapper("mapper")
                .setXml("mapper.xml");

        StrategyConfig sc = new StrategyConfig();
        sc.setNaming(NamingStrategy.underline_to_camel)
                .setColumnNaming(NamingStrategy.underline_to_camel)
//                .setExclude("label2house_info", "picture2house_info", "video2house_info")
                .setLogicDeleteFieldName("deleted")
                .setEntityLombokModel(true)
//                .setTablePrefix("tbl_")
                .setRestControllerStyle(true);


        new AutoGenerator().setGlobalConfig(gc)
                .setDataSource(dc)
                .setPackageInfo(pc)
                .setStrategy(sc)
                .setTemplateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}
