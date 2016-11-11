package com.sudichina.ftwl.view;

import android.content.Context;

import com.sudichina.ftwl.bean.CarBean;

import java.util.List;

/**
 * Created by mccccccmike on 2016/9/2.
 */
public interface IMyCarsView {
    void showProgressDialog();

    void dismissProgressDialog();

    Context getContext();

    void setCarBeanList(List<CarBean> carBeanList);

    void showToast(String msg);

    void refreshData();
}
