package com.study.test;

import com.study.entity.Student;
import com.study.mapper.StudentMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author ：fei
 * @date ：Created in 2019/10/10 0010 09:29
 *
 * *******************测试时需要有对应的mapper不能有注解否则注入两次报错*****************
 *
 * 通过xml方式构建SqlSessionFactory
 * 先创建SqlSessionFactoryBuilder#build(inputStream|reader);
 *      ==> XMLConfigBuilder -- mybatis配置文件的构建器 创建该对象的时候会创建Configuration
 *          ==> XPathParser -- 配置文件解析器
 *      ==> build(parser.parse()) parser.parse() 返回解析后的Configuration
 *  ==> 最终会调用SqlSessionFactoryBuilder#build(Configuration config);
 *
 *  因为MapperAnnotationBuilder#loadXmlResource方法通过
 *  String xmlResource = type.getName().replace('.', '/') + ".xml";
 *  方式去获取xml文件所以必须吧mapper.xml文件和接口的目录配置成在同一目录下，否则会解析不到xml配置文件
 *  同样在loadXmlResource方法中
 *  //此处构建XMLMapperBuilder时会设置currentNamespace = type.getName();
 *  XMLMapperBuilder xmlParser = new XMLMapperBuilder(inputStream, assistant.getConfiguration(), xmlResource, configuration.getSqlFragments(), type.getName());
 *  //在此处解析时会判断xml中配置的名称空间和上边设置的名称空间是否一样，如果不一样则抛出异常
 *  xmlParser.parse();
 *  因此xml中的名称空间必须和接口的全类路径名一致
 *
 *  mapperStatements中的key是用namespace+methodName作为id（MapperAnnotationBuilder#parseStatement）的注解和xml都配置的话，
 *  会在执行addMapper时（configuration.addMappedStatement(statement);），后解析注解方式的mapper不能放入导致抛出异常，
 *  因此一个mapper只能有一种解析方式
 */
public class SqlSessionFactoryTest {
    public static void main(String[] args) throws IOException {
        String resource = "mybatis.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory =
                new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
        Student stu = studentMapper.select(1);
        System.out.println(stu);
        System.out.println(stu.getName());
    }
}
