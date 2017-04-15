package com.university.education.bean;

/**
 * Created by jian on 2017/3/31.
 * 教学计划Bean
 */

public class TeachPlanBean {
    private String className;
    private String classXueFen;
    private String classXindZhi;
    private String classStartEnd;
    private String isXuewei;

    public TeachPlanBean(String className, String classXueFen, String classXindZhi, String classStartEnd, String isXuewei) {
        this.className = className;
        this.classXueFen = classXueFen;
        this.classXindZhi = classXindZhi;
        this.classStartEnd = classStartEnd;
        this.isXuewei = isXuewei;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassXueFen() {
        return classXueFen;
    }

    public void setClassXueFen(String classXueFen) {
        this.classXueFen = classXueFen;
    }

    public String getClassXindZhi() {
        return classXindZhi;
    }

    public void setClassXindZhi(String classXindZhi) {
        this.classXindZhi = classXindZhi;
    }

    public String getClassStartEnd() {
        return classStartEnd;
    }

    public void setClassStartEnd(String classStartEnd) {
        this.classStartEnd = classStartEnd;
    }

    public String getIsXuewei() {
        return isXuewei;
    }

    public void setIsXuewei(String isXuewei) {
        this.isXuewei = isXuewei;
    }
}
