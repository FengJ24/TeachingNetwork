package com.university.education.bean;

/**
 * Created by jian on 2017/4/15.
 */

public class TeachPlanChooseXueQiBean {
    private String xueqi;
    private boolean isCheck;

    public TeachPlanChooseXueQiBean(String xueqi) {
        this.xueqi = xueqi;
    }

    public String getXueqi() {
        return xueqi;
    }

    public void setXueqi(String xueqi) {
        this.xueqi = xueqi;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
