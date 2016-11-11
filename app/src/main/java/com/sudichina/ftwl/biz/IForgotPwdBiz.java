package com.sudichina.ftwl.biz;

/**
 * Created by mccccccmike on 2016/8/25.
 */
public interface IForgotPwdBiz {
    void requestIdentifyingCode(String authTarget,OnRequestIdentifyingCodeListener listener);

    void verifyIdentifyingCode(String authTarget,String authCode,OnVerifyICListener listener);
}
