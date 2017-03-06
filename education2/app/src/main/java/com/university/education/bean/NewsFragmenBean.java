package com.university.education.bean;

/**
 * Created by jian on 2017/3/5.
 * 理工要闻主页Bean类
 */

public class NewsFragmenBean {
    private String url;
    private String href;
    private String desc;
    private boolean isTouch;

    public boolean isTouch() {
        return isTouch;
    }

    public void setTouch(boolean touch) {
        isTouch = touch;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public NewsFragmenBean(String url, String href, String desc) {
        this.url = url;
        this.href = href;
        this.desc = desc;
    }
}
