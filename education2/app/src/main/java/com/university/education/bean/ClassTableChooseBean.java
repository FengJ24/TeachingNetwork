package com.university.education.bean;

/**
 * Created by jian on 2017/3/25.
 */

public class ClassTableChooseBean {
    private String name;
    private boolean isCheck;

    public ClassTableChooseBean(String name, boolean isCheck) {
        this.name = name;
        this.isCheck = isCheck;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
