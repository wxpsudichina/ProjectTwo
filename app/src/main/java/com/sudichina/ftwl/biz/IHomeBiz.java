package com.sudichina.ftwl.biz;

/**
 * Created by mccccccmike on 2016/8/30.
 */
public interface IHomeBiz {
    void releaseRoute(long accountId,
                      long carId,
                      long toZoneCode,
                      long fromZoneCode,
                      String laLoPosition,
                      String fromAddress,
                      String releaseTime,
                      String arriveTime,
                      String type,//all //zero
                      double priceAll,
                      double priceKg,
                      double priceDun,
                      double priceSquare,
                      double prickDoor,
                      double prickStation,
                      String addService,
                      OnReleaseRouteListener listener
    );

    void getUsefulCarList(long accountId, OnGetUsefulCarListListener listener);

}
