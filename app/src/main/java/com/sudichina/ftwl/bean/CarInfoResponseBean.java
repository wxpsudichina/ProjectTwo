package com.sudichina.ftwl.bean;

/**
 * Created by mccccccmike on 2016/8/30.
 */
public class CarInfoResponseBean {
    private String msg;
    private int code;
        private CarInfoBean data;
        private boolean success;

        public CarInfoResponseBean(String msg, int code, CarInfoBean data, boolean success) {
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

    public CarInfoBean getData() {
        return data;
    }

    public void setData(CarInfoBean data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
