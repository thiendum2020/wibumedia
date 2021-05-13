package com.example.wibumedia.Models;

public class PostModel {
    int img_profile, img_post;
    String tv_displayName, tv_address, tv_caption, tv_like, tv_comment;

    public PostModel() {
    }

    public PostModel(int img_profile, int img_post, String tv_displayName, String tv_address, String tv_caption, String tv_like, String tv_comment) {
        this.img_profile = img_profile;
        this.img_post = img_post;
        this.tv_displayName = tv_displayName;
        this.tv_address = tv_address;
        this.tv_caption = tv_caption;
        this.tv_like = tv_like;
        this.tv_comment = tv_comment;
    }

    public int getImg_profile() {
        return img_profile;
    }

    public void setImg_profile(int img_profile) {
        this.img_profile = img_profile;
    }

    public int getImg_post() {
        return img_post;
    }

    public void setImg_post(int img_post) {
        this.img_post = img_post;
    }

    public String getTv_displayName() {
        return tv_displayName;
    }

    public void setTv_displayName(String tv_displayName) {
        this.tv_displayName = tv_displayName;
    }

    public String getTv_address() {
        return tv_address;
    }

    public void setTv_address(String tv_address) {
        this.tv_address = tv_address;
    }

    public String getTv_caption() {
        return tv_caption;
    }

    public void setTv_caption(String tv_caption) {
        this.tv_caption = tv_caption;
    }

    public String getTv_like() {
        return tv_like;
    }

    public void setTv_like(String tv_like) {
        this.tv_like = tv_like;
    }

    public String getTv_comment() {
        return tv_comment;
    }

    public void setTv_comment(String tv_comment) {
        this.tv_comment = tv_comment;
    }
}
