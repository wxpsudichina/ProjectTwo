package com.sudichina.ftwl.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.sudichina.ftwl.R;
import com.sudichina.ftwl.utils.SPUtils;

/**
 * Created by SudiChina-105 on 2016/8/8.
 * 我的页面
 */
public class UserFragment extends BaseFragment implements View.OnClickListener {
    private RelativeLayout rl_id_verification;
    private RelativeLayout rl_vehicle_certification;

    private ImageView iv_id_state;
    private ImageView iv_vehicle_state;

    private ImageView iv_angle_right;

    private String accountDegree;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        accountDegree = (String) SPUtils.get(getActivity(), "accountDegree", "");

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_user, null);
        initView(view);
        initEvent();
        return view;
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initView(View view) {
        rl_id_verification = (RelativeLayout) view.findViewById(R.id.rl_id_verification);
        rl_vehicle_certification = (RelativeLayout) view.findViewById(R.id.rl_vehicle_certification);
        iv_angle_right = (ImageView) view.findViewById(R.id.iv_angle_right);

        iv_id_state = (ImageView) view.findViewById(R.id.iv_id_state);
        iv_vehicle_state = (ImageView) view.findViewById(R.id.iv_vehicle_state);

        if (accountDegree.equals("1")) {
            iv_id_state.setImageResource(R.mipmap.verified);
            iv_vehicle_state.setImageResource(R.mipmap.unverified);
        } else if (accountDegree.equals("2")) {
            iv_id_state.setImageResource(R.mipmap.unverified);
            iv_vehicle_state.setImageResource(R.mipmap.verified);
        } else if (accountDegree.equals("3")) {
            iv_id_state.setImageResource(R.mipmap.verified);
            iv_vehicle_state.setImageResource(R.mipmap.verified);
        }

    }

    @Override
    protected void initEvent() {
        rl_id_verification.setOnClickListener(this);
        rl_vehicle_certification.setOnClickListener(this);
        iv_angle_right.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_vehicle_certification:
                toAty(MyCarsActivity.class);
                break;
            case R.id.rl_id_verification:
                toAty(IdVerificationActivity.class);
                break;
            case R.id.iv_angle_right:
                toAty(MyCarsActivity.class);
                break;
        }
    }
}
