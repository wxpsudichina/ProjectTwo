package com.sudichina.ftwl.biz;

import com.sudichina.ftwl.bean.DicGroupBean;
import com.sudichina.ftwl.bean.DicValueBean;

import java.util.List;

/**
 * Created by mccccccmike on 2016/8/31.
 */
public interface OnRequestCarInfoListener extends OnRequestListener {
    void requestCarInfoSuccess(List<DicGroupBean> dicGroupBeanList, List<DicValueBean> dicValueBeanList);

    void requestCarInfoFailure();
}
