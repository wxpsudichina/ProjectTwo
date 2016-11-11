package com.sudichina.ftwl.biz;

/**
 * Created by mccccccmike on 2016/8/25.
 */
public interface IResetPwdBiz {
    void resetPwd(String phoneNo, String newPwd, String cfmPwd,OnResetPwdListener listener);
}
