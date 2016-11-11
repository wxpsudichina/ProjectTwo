package com.sudichina.ftwl.view;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sudichina.ftwl.R;
import com.sudichina.ftwl.presenter.QuickLoginPresenter;

public class QuickLoginActivity extends BaseActivity implements IQuickLoginView, View.OnClickListener {
    private EditText et_phone_no;
    private EditText et_identifying_code;
    private Button btn_get_identifying_code;
    private Button btn_login;

    private TextView tv_register_now;

    private QuickLoginPresenter quickLoginPresenter = new QuickLoginPresenter(this);

    private static final int TIME_INTERVAL = 60;//设置倒计时最大值60
    private int one_minute = TIME_INTERVAL;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_login);

        initView();
        initEvent();
    }

    @Override
    protected void initView() {
        et_phone_no = (EditText) findViewById(R.id.et_phone_no);
        et_identifying_code = (EditText) findViewById(R.id.et_identifying_code);
        btn_get_identifying_code = (Button) findViewById(R.id.btn_get_identifying_code);
        btn_login = (Button) findViewById(R.id.btn_login);

        tv_register_now = (TextView) findViewById(R.id.tv_register_now);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        btn_get_identifying_code.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        tv_register_now.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get_identifying_code:
                quickLoginPresenter.getIdentifyingCode();
                break;
            case R.id.btn_login:
                quickLoginPresenter.login();
                break;
            case R.id.tv_register_now:
                toAty(RegisterActivity.class);
                break;
        }
    }

    @Override
    public String getPhoneNo() {
        return et_phone_no.getText().toString().trim();
    }

    @Override
    public String getIdentifyingCode() {
        return et_identifying_code.getText().toString().trim();
    }

    @Override
    public void showToast(String msg) {
        toast(msg);
    }

    private Runnable r = new Runnable() {
        @Override
        public void run() {
            btn_get_identifying_code.setText(--one_minute < 0 ? "获取验证码" : String.valueOf(one_minute));
            if (one_minute < 0) {
                one_minute = TIME_INTERVAL;
                btn_get_identifying_code.setEnabled(true);
                return;
            }
            mHandler.postDelayed(this, 1000);
        }
    };

    @Override
    public void showGetIdentifyingCodeState() {
        btn_get_identifying_code.setEnabled(false);

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                btn_get_identifying_code.setText(String.valueOf(--one_minute));
                mHandler.postDelayed(r, 1000);
            }
        });
    }

    @Override
    public Context getActivity() {
        return this;
    }

    @Override
    public void toMainActivity() {
        toAty(MainActivity.class);
    }
}
