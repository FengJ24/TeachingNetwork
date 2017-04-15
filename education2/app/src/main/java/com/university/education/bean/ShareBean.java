package com.university.education.bean;

/**
 * Created by jian on 2017/4/15.
 */
public class ShareBean {
    private String shareUrl;
    private String title;

    public ShareBean(String shareUrl, String title) {
        this.shareUrl = shareUrl;
        this.title = title;
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
