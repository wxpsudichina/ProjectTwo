package com.sudichina.ftwl.presenter;

import android.os.Handler;

import com.sudichina.ftwl.bean.LoginDataBean;
import com.sudichina.ftwl.bean.LoginResponseBean;
import com.sudichina.ftwl.bean.ResponseBean;
import com.sudichina.ftwl.biz.IQuickLoginBiz;
import com.sudichina.ftwl.biz.OnRequestIdentifyingCodeListener;
import com.sudichina.ftwl.biz.OnLoginListener;
import com.sudichina.ftwl.biz.QuickLoginBiz;
import com.sudichina.ftwl.utils.SPUtils;
import com.sudichina.ftwl.view.IQuickLoginView;

/**
 * Created by mccccccmike on 2016/8/24.
 * 快速登录逻辑控制层
 */
public class QuickLoginPresenter {
    private IQuickLoginView quickLoginView;
    private IQuickLoginBiz quickLoginBiz;
    private Handler mHandler;

    public QuickLoginPresenter(IQuickLoginView quickLoginView) {
        this.quickLoginView = quickLoginView;
        quickLoginBiz = new QuickLoginBiz();
        mHandler = new Handler();
    }

    public void getIdentifyingCode() {
        if (quickLoginView.getPhoneNo().equals("")) {
            quickLoginView.showToast("请输入手机号");
            return;
        }

        if (!quickLoginView.getPhoneNo().matches("^[0-9]{11}$")) {
            return;
        }

        quickLoginView.showGetIdentifyingCodeState();
        quickLoginBiz.getIdentifyingCode(quickLoginView.getPhoneNo(), new OnRequestIdentifyingCodeListener() {
            @Override
            public void requestIdentifyingCodeSuccessful(final String msg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        quickLoginView.showToast(msg);
                    }
                });
            }

            @Override
            public void requestIdentifyingCodeFailed(final String msg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        quickLoginView.showToast(msg);
                    }
                });
            }

            @Override
            public void requestFailed() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }

            @Override
            public void requestSuccess() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        });
    }

    public void login() {
        quickLoginBiz.login(quickLoginView.getPhoneNo(), quickLoginView.getIdentifyingCode(), new OnLoginListener() {
            @Override
            public void loginSuccessful(final LoginDataBean loginDataBean) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        quickLoginView.showToast("登录成功");
                        SPUtils.put(quickLoginView.getActivity(), "id", loginDataBean.getId());
                        SPUtils.put(quickLoginView.getActivity(), "accountDegree", loginDataBean.getAccountDegree());
                        quickLoginView.toMainActivity();
                    }
                });
            }

            @Override
            public void loginFailed(final String msg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        quickLoginView.showToast(msg);
                    }
                });
            }

            @Override
            public void requestFailed() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        quickLoginView.showToast("request failure...");
                    }
                });
            }

            @Override
            public void requestSuccess() {

            }
        });
    }
}
