package com.study.test;

import com.study.entity.Student;
import com.study.ienum.SCORE;
import com.study.mapper.StudentMapper;
import org.apache.ibatis.session.SqlSession;

import java.util.Date;

/**
 * @author ：fei
 * @date ：Created in 2019/10/11 0011 14:46
 */
public class TypeHandlerTest extends BaseTest {
    public static void main(String[] args) {
        SqlSession sqlSession = getSqlSession();
        StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
        Student stu = new Student();
        stu.setUid(13);
        stu.setCreateTime(new Date());
        stu.setScore(SCORE.GAO);
        studentMapper.insert(stu);
        sqlSession.commit();
        System.out.println("插入成功");
    }
}
