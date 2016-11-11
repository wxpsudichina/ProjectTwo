package com.sudichina.ftwl.biz;

import com.google.gson.Gson;
import com.sudichina.ftwl.bean.LoginResponseBean;
import com.sudichina.ftwl.bean.QuickSignInBean;
import com.sudichina.ftwl.bean.RequestICBean;
import com.sudichina.ftwl.bean.VerifyICBean;
import com.sudichina.ftwl.bean.ResponseBean;
import com.sudichina.ftwl.utils.OkHttpUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by mccccccmike on 2016/8/24.
 * 快速登录逻辑功能类
 */
public class QuickLoginBiz implements IQuickLoginBiz {

    @Override
    public void getIdentifyingCode(String phoneNo, final OnRequestIdentifyingCodeListener listener) {
        RequestICBean requestICBean = new RequestICBean(phoneNo, "sms_shortcut_login", "Android");

        OkHttpUtils.jsonPost(IURL.SEND_IC, requestICBean, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("request failure...");
                listener.requestFailed();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("request successful...");
                String json = response.body().string();
                System.out.println(json);
                Gson gson = new Gson();
                ResponseBean responseObj = gson.fromJson(json, ResponseBean.class);

                if (responseObj.isSuccess()) {
                    listener.requestIdentifyingCodeSuccessful(responseObj.getMsg());
                } else {
                    listener.requestIdentifyingCodeFailed(responseObj.getMsg());
                }
            }
        });
    }

    @Override
    public void login(String authTarget, String authCode, final OnLoginListener listener) {
        if (authTarget.equals("")) {
            listener.loginFailed("请输入手机号");
            return;
        }

        if (authCode.equals("")) {
            listener.loginFailed("请输入验证码");
            return;
        }

        QuickSignInBean quickSignInBean = new QuickSignInBean(authTarget, authCode, "sms_shortcut_login");
//        VerifyICBean verifyICBean = new VerifyICBean(authTarget, "sms_reg_verify", authCode);
        OkHttpUtils.jsonPost(IURL.QUICK_SIGN_IN, quickSignInBean, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("request failure...");
                listener.loginFailed("登录失败。。。");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("request successful...");
//                System.out.println(response.body().string());
//                listener.loginSuccessful("登录成功。。。");
                String json = response.body().string();
                Gson gson = new Gson();
                LoginResponseBean loginResponseBean = gson.fromJson(json, LoginResponseBean.class);
                if (loginResponseBean.isSuccess()) {
                    listener.loginSuccessful(loginResponseBean.getData());
                } else {
                    listener.loginFailed(loginResponseBean.getMsg());
                }


            }
        });
    }
}
