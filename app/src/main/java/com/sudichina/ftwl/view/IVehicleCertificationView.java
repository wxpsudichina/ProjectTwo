package com.sudichina.ftwl.view;

import android.content.Context;

import com.sudichina.ftwl.bean.CarBean;
import com.sudichina.ftwl.bean.DicGroupBean;
import com.sudichina.ftwl.bean.DicValueBean;
import com.sudichina.ftwl.bean.MyCarBean;
import com.sudichina.ftwl.bean.MyCarInfoBean;

import java.util.List;

/**
 * Created by mccccccmike on 2016/8/30.
 */
public interface IVehicleCertificationView {
    void showProgressDialog(String msg);

    void dismissProgressDialog();

    String getCarFirstLetter();

    String getCardNumber();

    String getCarDicId();

    String getEngineNum();

    String getDriveLicensePath();

    String getDriveLicensePath2();

    Context getContext();

    void setCarInfoData(List<DicGroupBean> dicGroupBeanList, List<DicValueBean> dicValueBeanList);

    CarBean getCarBean();

    void setMyCarBean(MyCarBean myCarBean);

    long getId();

    long getAccountId();

    void showToast(String msg);
}
