package com.sudichina.ftwl.biz;

import com.sudichina.ftwl.bean.DicValueBean;
import com.sudichina.ftwl.bean.MyCarBean;
import com.sudichina.ftwl.bean.MyCarInfoBean;

import java.util.List;

/**
 * Created by mccccccmike on 2016/9/2.
 */
public interface OnGetVerifiedVehicleInfoListener extends OnRequestListener {
    void getVerifiedVehicleInfoSuccess(MyCarBean myCarBean);

    void getVerifiedVehicleInfoFailed();
}
