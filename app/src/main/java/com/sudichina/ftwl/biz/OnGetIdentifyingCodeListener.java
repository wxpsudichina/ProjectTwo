package com.sudichina.ftwl.biz;

/**
 * Created by mccccccmike on 2016/9/7.
 */
public interface OnGetIdentifyingCodeListener extends OnRequestListener {
    void getIdentifyingCodeSuccess(String msg);

    void getIdentifyingCodeFailed(String msg);
}
