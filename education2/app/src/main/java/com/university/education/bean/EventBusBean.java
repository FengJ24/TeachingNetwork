package com.university.education.bean;

/**
 * Created by jian on 2017/1/15.
 */
public class EventBusBean {
    private  String type;
    private String content;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public EventBusBean(String type, String content) {
        this.type = type;
        this.content = content;
    }
}
