package com.study.test;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author ：fei
 * @date ：Created in 2019/10/11 0011 10:18
 * properties加载顺序
 * 1.properties标签中的property属性值
 * 2.properties标签resource或url加载的文件
 * 3.传入new SqlSessionFactoryBuilder.build(reader, environment, props);中的属性值
 * 后加载的会覆盖先加载的值
 * 示例中git的值按顺序会加载最后一个
 */
public class PropertiesOrder {
    public static void main(String[] args) throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis.xml");
        Properties props = new Properties();
//        props.setProperty("git","git3");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream,props);
        Properties variables = sqlSessionFactory.getConfiguration().getVariables();
        System.out.println(variables.getProperty("git"));
    }
}
