package com.sudichina.ftwl.biz;

import com.sudichina.ftwl.bean.UsefulCarBean;

import java.util.List;

/**
 * Created by mccccccmike on 2016/9/2.
 */
public interface OnGetUsefulCarListListener extends OnRequestListener {
    void getCarListSuccess(List<UsefulCarBean> usefulCarBeanList);

    void getCarListFailure(String msg);
}
