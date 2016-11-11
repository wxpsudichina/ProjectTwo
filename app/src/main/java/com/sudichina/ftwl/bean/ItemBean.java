package com.sudichina.ftwl.bean;

/**
 * Created by mike on 2016/8/18.
 */

public class ItemBean {
    private String text;
    private int img;

    public ItemBean(String text, int img) {
        this.text = text;
        this.img = img;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
