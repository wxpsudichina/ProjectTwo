package com.sudichina.ftwl.biz;

/**
 * Created by mccccccmike on 2016/9/2.
 */
public interface IMyCarsBiz {
    void getCarList(long accountId, OnGetCarListListener listener);

    void deleteVehicle(long id,OnDeleteVehicleListener listener);
}
