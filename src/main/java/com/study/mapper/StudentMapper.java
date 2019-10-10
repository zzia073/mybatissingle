package com.study.mapper;

import com.study.entity.Student;
import org.apache.ibatis.annotations.Select;

/**
 * @author ：fei
 * @date ：Created in 2019/10/10 0010 09:30
 * 注解和xml只能用一种方式
 * MapperAnnotationBuilder#parse方法中同时执行了两种解析
 * xml是通过loadXmlResource();解析
 * annotation是通过parseStatement(method);==>getSqlSourceFromAnnotations(method, parameterTypeClass, languageDriver);解析
 */
public interface StudentMapper {
    /**
     * 注解方式用动态sql时需要script标签
     * @param uid
     * @return student
     */
    @Select({"<script>",
            "select * from student",
            "<where> ",
            "uid = #{uid} ",
            "</where>",
            "</script>"})
    Student select(Integer uid);
}
