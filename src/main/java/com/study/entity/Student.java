package com.study.entity;

import com.study.ienum.SCORE;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * @author ：fei
 * @date ：Created in 2019/10/10 0010 09:32
 */
@Alias("stu")
public class Student {
    private Integer uid;
    private String name;
    private Integer grade;
    private SCORE score;
    private Date createTime;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public SCORE getScore() {
        return score;
    }

    public void setScore(SCORE score) {
        this.score = score;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
