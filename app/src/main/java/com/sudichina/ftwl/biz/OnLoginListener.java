package com.sudichina.ftwl.biz;

import com.sudichina.ftwl.bean.LoginDataBean;
import com.sudichina.ftwl.bean.LoginResponseBean;

/**
 * Created by mike on 2016/8/9.
 */
public interface OnLoginListener extends OnRequestListener {
    void loginSuccessful(LoginDataBean loginDataBean);

    void loginFailed(String msg);

}
