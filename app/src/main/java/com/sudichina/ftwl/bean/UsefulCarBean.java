package com.sudichina.ftwl.bean;

/**
 * Created by mccccccmike on 2016/9/19.
 */
public class UsefulCarBean {
    private long id;
    private String cardNum;

    public UsefulCarBean(long id, String cardNum) {
        this.id = id;
        this.cardNum = cardNum;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }
}
