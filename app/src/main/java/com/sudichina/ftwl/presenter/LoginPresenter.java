package com.sudichina.ftwl.presenter;

import android.os.Handler;

import com.sudichina.ftwl.bean.LoginDataBean;
import com.sudichina.ftwl.bean.LoginResponseBean;
import com.sudichina.ftwl.biz.ILoginBiz;
import com.sudichina.ftwl.biz.LoginBiz;
import com.sudichina.ftwl.biz.OnLoginListener;
import com.sudichina.ftwl.utils.SPUtils;
import com.sudichina.ftwl.view.ILoginView;

/**
 * Created by mike on 2016/8/9.
 */
public class LoginPresenter {
    private ILoginBiz loginBiz;
    private ILoginView loginView;
    private Handler mHandler;

    public LoginPresenter(ILoginView loginView) {
        this.loginView = loginView;
        loginBiz = new LoginBiz();
        mHandler = new Handler();
    }

    public void login() {
        loginView.setLoginBtnDisabled();
//        loginView.showLoading();
        loginBiz.login(loginView.getPhoneNo(), loginView.getPwd(), new OnLoginListener() {
            @Override
            public void loginSuccessful(final LoginDataBean loginDataBean) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        loginView.showToast("登录成功");
                        loginView.setLoginBtnEnabled();
                        SPUtils.put(loginView.getActivity(), "id", loginDataBean.getId());
                        SPUtils.put(loginView.getActivity(), "accountDegree", loginDataBean.getAccountDegree());
                        loginView.toMainActivity(loginDataBean);
                    }
                });
            }

            @Override
            public void loginFailed(final String msg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        loginView.showToast(msg);
                        loginView.setLoginBtnEnabled();
                    }
                });
            }

            @Override
            public void requestFailed() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        loginView.showToast("请求失败");
                        loginView.showToast("request failure...");
                        loginView.setLoginBtnEnabled();
                    }
                });
            }

            @Override
            public void requestSuccess() {

            }
        });


    }
}
