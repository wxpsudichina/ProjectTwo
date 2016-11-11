package com.sudichina.ftwl.bean;

/**
 * Created by mccccccmike on 2016/9/14.
 */
public class QuickSignInBean {
    private String authTarget;
    private String authCode;
    private String authType;

    public QuickSignInBean(String authTarget, String authCode, String authType) {
        this.authTarget = authTarget;
        this.authCode = authCode;
        this.authType = authType;
    }

    public String getAuthTarget() {
        return authTarget;
    }

    public void setAuthTarget(String authTarget) {
        this.authTarget = authTarget;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }
}
