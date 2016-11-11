package com.sudichina.ftwl.view;

import android.app.Activity;
import android.content.Context;

import com.sudichina.ftwl.bean.CarOwnerBean;

/**
 * Created by mccccccmike on 2016/8/26.
 */
public interface IIdVerificationView extends ICommonView {
    String getRealName();

    String getDrivingLicence();

    String getDrivingLicencePath();

    Activity getActivity();

    void setVerifyBtnEnabled();

    void setVerifyBtnDisabled();

    Context getContext();

    void showDialogVerifying(String msg);

    void dismissDialogVerifying();

    void setCarOwnerBean(CarOwnerBean carOwnerBean);

}
