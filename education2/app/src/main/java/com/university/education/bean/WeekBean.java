package com.university.education.bean;

/**
 * Created by jian on 2017/1/15.
 */
public class WeekBean {
    private String type;
    private String text;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public WeekBean(String type, String text) {
        this.type = type;
        this.text = text;
    }

    @Override
    public String toString() {
        return "WeekBean{" +
                "type='" + type + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
