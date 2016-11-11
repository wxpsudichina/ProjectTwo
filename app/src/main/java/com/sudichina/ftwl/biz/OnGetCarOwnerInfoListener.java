package com.sudichina.ftwl.biz;

import com.sudichina.ftwl.bean.CarOwnerBean;

/**
 * Created by mccccccmike on 2016/9/7.
 */
public interface OnGetCarOwnerInfoListener extends OnRequestListener {
    void getCarOwnerInfoSuccess(CarOwnerBean carOwnerBean);

    void getCarOwnerInfoFailed(CarOwnerBean carOwnerBean);
}
