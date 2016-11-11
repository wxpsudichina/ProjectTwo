package com.sudichina.ftwl.view;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.sudichina.ftwl.R;
import com.sudichina.ftwl.presenter.ForgotPwdPresenter;

public class ForgotPwdActivity extends BaseActivity implements IForgotPwdView, View.OnClickListener {
    public static final String PHONE_NO = "phone_no";

    private EditText et_phone_no;
    private EditText et_identifying_code;
    private Button btn_request_identifying_code;
    private Button btn_continue;
    private ImageView iv_go_back;

    private ForgotPwdPresenter forgotPwdPresenter = new ForgotPwdPresenter(this);

    private static final int TIME_INTERVAL = 60;//设置倒计时最大值60
    private int one_minute = TIME_INTERVAL;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pwd);

        initView();
        initEvent();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        et_phone_no = (EditText) findViewById(R.id.et_phone_no);
        et_identifying_code = (EditText) findViewById(R.id.et_identifying_code);
        btn_request_identifying_code = (Button) findViewById(R.id.btn_request_identifying_code);
        btn_continue = (Button) findViewById(R.id.btn_continue);
        iv_go_back = (ImageView) findViewById(R.id.iv_go_back);
    }

    @Override
    protected void initEvent() {
        btn_request_identifying_code.setOnClickListener(this);
        btn_continue.setOnClickListener(this);
        iv_go_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_request_identifying_code:
                System.out.println("click me...");
                forgotPwdPresenter.requestIdentifyingCode();
                break;
            case R.id.btn_continue:
                forgotPwdPresenter.verifyIdentifyingCode();
                break;
            case R.id.iv_go_back:
                finish();
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

    @Override
    public void toResetPwdAty() {
        Bundle bundle = new Bundle();
        bundle.putString(PHONE_NO, getPhoneNo());
        toAty(ResetPwdActivity.class, bundle);
    }

    private Runnable r = new Runnable() {
        @Override
        public void run() {
            btn_request_identifying_code.setText(--one_minute < 0 ? "获取验证码" : String.valueOf(one_minute));
            if (one_minute < 0) {
                one_minute = TIME_INTERVAL;
                btn_request_identifying_code.setEnabled(true);
                return;
            }
            mHandler.postDelayed(this, 1000);
        }
    };

    @Override
    public void showGetIdentifyingCodeState() {
        btn_request_identifying_code.setEnabled(false);

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                btn_request_identifying_code.setText(String.valueOf(--one_minute));
                mHandler.postDelayed(r, 1000);
            }
        });
    }

    @Override
    public void dismissGetIdentifyingCodeState() {
        mHandler.removeCallbacks(r);
        btn_request_identifying_code.setText("获取验证码");
        btn_request_identifying_code.setEnabled(true);
    }
}
