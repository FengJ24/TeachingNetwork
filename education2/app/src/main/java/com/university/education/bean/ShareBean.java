package com.university.education.bean;

/**
 * Created by jian on 2017/4/15.
 */
public class ShareBean {
    private String shareUrl;
    private String title;
    private String source;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ShareBean(String shareUrl, String title, String source) {
        this.shareUrl = shareUrl;
        this.title = title;
        this.source = source;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
