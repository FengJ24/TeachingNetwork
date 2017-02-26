package com.university.education.bean;

/**
 * Created by jian on 2017/2/14.
 */

public class TeachNotificationBean {
    private String title;
    private String date;
    private String url;

    public TeachNotificationBean(String title, String date) {
        this.title = title;
        this.date = date;
    }

    public TeachNotificationBean(String title, String date, String url) {
        this.title = title;
        this.date = date;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
