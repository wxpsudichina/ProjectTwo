package com.sudichina.ftwl.biz;

/**
 * Created by mccccccmike on 2016/9/3.
 */
public interface OnModifyVehicleInfoListener extends OnRequestListener {
    void modifyVehicleInfoSuccess(String msg);

    void modifyVehicleInfoFailed(String msg);
}
