package com.sudichina.ftwl.bean;

import java.util.List;

/**
 * Created by mccccccmike on 2016/9/2.
 */
public class MyCarInfoResponseBean {
    private String msg;
    private MyCarInfoBean data;
    private int code;
    private boolean success;

    public MyCarInfoResponseBean(String msg, MyCarInfoBean data, int code, boolean success) {
        this.msg = msg;
        this.data = data;
        this.code = code;
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public MyCarInfoBean getData() {
        return data;
    }

    public void setData(MyCarInfoBean data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
