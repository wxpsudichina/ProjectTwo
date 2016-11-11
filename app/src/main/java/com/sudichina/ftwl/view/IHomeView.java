package com.sudichina.ftwl.view;

import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sudichina.ftwl.bean.CarBean;
import com.sudichina.ftwl.bean.UsefulCarBean;

import java.util.List;

/**
 * Created by mccccccmike on 2016/9/19.
 */
public interface IHomeView {

    long getAccountId();

    long getCarId();

    long getToZoneCode();

    long getFromZoneCode();

    String getLaLoPosition();

    String getFromAddress();

    String getReleaseTime();

    String getArriveTime();

    String getType();

    double getPriceAll();

    double getPriceKg();

    double getPriceDun();

    double getPriceSquare();

    String getAddService();

    double getPrickDoor();

    double getPrickStation();

    void setUsefulCarBeanList(List<UsefulCarBean> usefulCarBeanList);

    void showProgressDialog();

    void dismissProgressDialog();

    void showContent(int height, final ViewGroup vg, long duration);

    void hideContent(int height, final ViewGroup vg, long duration);

    LinearLayout getLl_container();

    int getLl_bottom_height();

    LinearLayout getLl_release_complete();

    boolean isFlag();

    void setFlag(boolean flag);

    boolean isLl_release_complete_is_show();

    void setLl_release_complete_is_show(boolean ll_release_complete_is_show);
}
