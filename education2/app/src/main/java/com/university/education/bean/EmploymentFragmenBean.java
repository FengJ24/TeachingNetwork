package com.university.education.bean;

/**
 * Created by jian on 2017/3/5.
 * 就业招聘主页Bean类
 */

public class EmploymentFragmenBean {
    private String href;
    private String desc;
    private boolean isTouch;

    public boolean isTouch() {
        return isTouch;
    }

    public void setTouch(boolean touch) {
        isTouch = touch;
    }


    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public EmploymentFragmenBean( String href, String desc) {
        this.href = href;
        this.desc = desc;
    }
}
