package org.deng.many_algorithms.Pojo;

import java.io.Serializable;

/**
 * (Testinfo)实体类
 *
 * @author makejava
 * @since 2024-06-27 23:23:27
 */
public class Testinfo implements Serializable {
    private static final long serialVersionUID = -62191604601360952L;

    private String 考试时间;

    private String 开课学院;

    private String 课程名称;

    private String 教学班名称;

    private String 班级组成;

    private String 任课教师;

    private String 考试方式;

    private String 考场校区;

    private Long 选课人数;

    private Long 已排人数;

    private String 考场;


    public String get考试时间() {
        return 考试时间;
    }

    public void set考试时间(String 考试时间) {
        this.考试时间 = 考试时间;
    }

    public String get开课学院() {
        return 开课学院;
    }

    public void set开课学院(String 开课学院) {
        this.开课学院 = 开课学院;
    }

    public String get课程名称() {
        return 课程名称;
    }

    public void set课程名称(String 课程名称) {
        this.课程名称 = 课程名称;
    }

    public String get教学班名称() {
        return 教学班名称;
    }

    public void set教学班名称(String 教学班名称) {
        this.教学班名称 = 教学班名称;
    }

    public String get班级组成() {
        return 班级组成;
    }

    public void set班级组成(String 班级组成) {
        this.班级组成 = 班级组成;
    }

    public String get任课教师() {
        return 任课教师;
    }

    public void set任课教师(String 任课教师) {
        this.任课教师 = 任课教师;
    }

    public String get考试方式() {
        return 考试方式;
    }

    public void set考试方式(String 考试方式) {
        this.考试方式 = 考试方式;
    }

    public String get考场校区() {
        return 考场校区;
    }

    public void set考场校区(String 考场校区) {
        this.考场校区 = 考场校区;
    }

    public Long get选课人数() {
        return 选课人数;
    }

    public void set选课人数(Long 选课人数) {
        this.选课人数 = 选课人数;
    }

    public Long get已排人数() {
        return 已排人数;
    }

    public void set已排人数(Long 已排人数) {
        this.已排人数 = 已排人数;
    }

    public String get考场() {
        return 考场;
    }

    public void set考场(String 考场) {
        this.考场 = 考场;
    }

}

