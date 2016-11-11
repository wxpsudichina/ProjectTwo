package com.sudichina.ftwl.biz;

/**
 * Created by mccccccmike on 2016/9/5.
 */
public interface OnDeleteVehicleListener extends OnRequestListener {
    void deleteVehicleSuccess(String msg);

    void deleteVehicleFailed(String msg);
}
