package com.sudichina.ftwl.bean;

/**
 * Created by mccccccmike on 2016/9/6.
 */
public class VerifiedVehicleInfoResponseBean {
    private String msg;
    private MyCarBean data;
    private int code;
    private boolean success;

    public VerifiedVehicleInfoResponseBean(String msg, MyCarBean data, int code, boolean success) {
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

    public MyCarBean getData() {
        return data;
    }

    public void setData(MyCarBean data) {
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
