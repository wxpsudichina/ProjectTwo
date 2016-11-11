package com.sudichina.ftwl.view;

import android.app.Activity;

import com.sudichina.ftwl.bean.LoginDataBean;
import com.sudichina.ftwl.bean.LoginResponseBean;
import com.sudichina.ftwl.bean.ResponseBean;

/**
 * Created by mike on 2016/8/9.
 * every activity needs to has a interface which to communicate with a presenter.
 */
public interface ILoginView {
    String getPhoneNo();

    String getPwd();

    void toMainActivity(LoginDataBean loginDataBean);

    void showToast(String msg);

    void setLoginBtnEnabled();

    void setLoginBtnDisabled();

    Activity getActivity();
}
