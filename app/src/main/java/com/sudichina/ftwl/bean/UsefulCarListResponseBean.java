package com.sudichina.ftwl.bean;

import java.util.List;

/**
 * Created by mccccccmike on 2016/9/19.
 */
public class UsefulCarListResponseBean {
    private String msg;
    private int code;
    private List<UsefulCarBean> data;
    private boolean success;

    public UsefulCarListResponseBean(String msg, int code, List<UsefulCarBean> data, boolean success) {
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

    public List<UsefulCarBean> getData() {
        return data;
    }

    public void setData(List<UsefulCarBean> data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
