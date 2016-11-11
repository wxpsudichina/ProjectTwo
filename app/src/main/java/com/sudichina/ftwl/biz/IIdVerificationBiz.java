package com.sudichina.ftwl.biz;

import android.content.Context;

/**
 * Created by mccccccmike on 2016/8/26.
 */
public interface IIdVerificationBiz {
    void verify(Context context, String realName, String driverLicence, String driverLicencePath, OnIdVerifyListener listener);

    void getCarOwnerInfo(long id, OnGetCarOwnerInfoListener listener);
}
