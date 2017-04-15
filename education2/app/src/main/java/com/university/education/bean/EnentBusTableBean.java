package com.university.education.bean;

/**
 * Created by jian on 2017/3/25.
 */

public class EnentBusTableBean {
    private String xuenian;
    private String xueqi;

    public EnentBusTableBean(String xuenian, String xueqi) {
        this.xuenian = xuenian;
        this.xueqi = xueqi;
    }

    public String getXuenian() {
        return xuenian;
    }

    public void setXuenian(String xuenian) {
        this.xuenian = xuenian;
    }

    public String getXueqi() {
        return xueqi;
    }

    public void setXueqi(String xueqi) {
        this.xueqi = xueqi;
    }
}
