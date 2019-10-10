package com.study.test;

import com.study.entity.Student;
import com.study.mapper.StudentMapper;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;

/**
 * @author ：fei
 * @date ：Created in 2019/10/10 0010 09:55
 *
 * *******************测试时不能有对应的xml文件否则会注入两个相同的mapper导致报错*******************
 *
 * 当使用注解开发时如果有<script></script>标签时mybatis会创建DynamicSqlSource否则创建RawSqlSource
 * XMLScriptBuilder用来解析所有mysql定义的动态sql标签的每个标签都对应一个NodeHandler比如if标签对应IfHandler等
 */
public class SqlSessionFactoryNoXML {
    public static void main(String[] args) {
        //连接数据库必要信息DataSource
        DataSource dataSource = new PooledDataSource("com.mysql.jdbc.Driver","jdbc:mysql:///db1","root","root");
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        //配置多个环境方便切换
        Environment environment = new Environment("dev",transactionFactory,dataSource);
        //核心类解析存储了mybatis所有配置信息
        Configuration configuration = new Configuration(environment);
        //向配置中心中注册并解析所有mapper，注解方式或者xml
        configuration.addMappers("com.study.mapper");
        //连接工厂
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        //获取mapper或者操作增删改查的对外接口
        SqlSession sqlSession = sqlSessionFactory.openSession();

        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        Student stu = mapper.select(1);
        System.out.println(stu);
        System.out.println(stu.getName());
    }
}
