package com.sudichina.ftwl.bean;

import java.util.List;

/**
 * Created by mccccccmike on 2016/9/7.
 */
public class CarOwnerResponseBean {
    private String msg;
    private int code;
    private CarOwnerBean data;
    private boolean success;

    public CarOwnerResponseBean(String msg, int code, CarOwnerBean data, boolean success) {
        this.msg = msg;
        this.code = code;
        this.data = data;
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public CarOwnerBean getData() {
        return data;
    }

    public void setData(CarOwnerBean data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
