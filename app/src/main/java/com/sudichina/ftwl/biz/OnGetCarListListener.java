package com.sudichina.ftwl.biz;

import com.sudichina.ftwl.bean.CarBean;
import com.sudichina.ftwl.bean.UsefulCarBean;

import java.util.List;

/**
 * Created by mccccccmike on 2016/9/2.
 */
public interface OnGetCarListListener extends OnRequestListener {
    void getCarListSuccess(List<CarBean> carBeanList);

    void getCarListFailure(String msg);
}
