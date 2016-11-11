package com.sudichina.ftwl.biz;

/**
 * Created by mccccccmike on 2016/8/26.
 */
public interface OnIdVerifyListener extends OnRequestListener {
    void IdVerifySuccessful(String msg);

    void IdVerifyFailed(String msg);
}
