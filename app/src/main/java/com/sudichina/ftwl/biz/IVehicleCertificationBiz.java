package com.sudichina.ftwl.biz;

/**
 * Created by mccccccmike on 2016/8/30.
 */
public interface IVehicleCertificationBiz {
    String getCarInfo(OnRequestCarInfoListener requestCarInfoListener);

    void verify(long accountId, String carFirstLetter, String cardNumber, String carDicId, String driveLicense, String driveLicensePath, String driveLicensePath2, OnRequestListener listener);

    //    void getCarSquareDun(long dicGroupId, final OnGetVerifiedVehicleInfoListener listener);
    void getVerifiedVehicleInfo(long id, OnGetVerifiedVehicleInfoListener listener);

    void modifyVehicleInfo(long id,
                           long accountId,
                           String carFirstLetter,
                           String cardNumber,
                           String carDicId,
                           String engineNum,
                           String driveLicensePath,
                           String driveLicensePath2,
                           OnModifyVehicleInfoListener listener);
}
