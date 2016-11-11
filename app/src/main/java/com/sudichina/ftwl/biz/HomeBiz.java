package com.sudichina.ftwl.biz;

import com.google.gson.Gson;
import com.sudichina.ftwl.bean.AccountIdBean;
import com.sudichina.ftwl.bean.CarListResponseBean;
import com.sudichina.ftwl.bean.UsefulCarListResponseBean;
import com.sudichina.ftwl.utils.OkHttpUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by mccccccmike on 2016/8/30.
 * //发布路线的逻辑层
 */
public class HomeBiz implements IHomeBiz {
    @Override
    public void releaseRoute(long accountId, long carId, long toZoneCode, long fromZoneCode, String laLoPosition, String fromAddress, String releaseTime, String arriveTime, String type, double priceAll, double priceKg, double priceDun, double priceSquare, double prickDoor, double prickStation, String addService,OnReleaseRouteListener listener) {
        System.out.println("accountId = " + accountId);
        System.out.println("carId = " + carId);
        System.out.println("toZoneCode = " + toZoneCode);
        System.out.println("fromZoneCode = " + fromZoneCode);
        System.out.println("laLoPosition = " + laLoPosition);
        System.out.println("fromAddress = " + fromAddress);
        System.out.println("releaseTime = " + releaseTime);
        System.out.println("arriveTime = " + arriveTime);
        System.out.println("type = " + type);
        System.out.println("priceAll = " + priceAll);
        System.out.println("priceKg = " + priceKg);
        System.out.println("priceDun = " + priceDun);
        System.out.println("priceSquare = " + priceSquare);
        System.out.println("prickDoor = " + prickDoor);
        System.out.println("prickStation = " + prickStation);

        System.out.println("addService = " + addService);

        listener.onReleaseRouteSuccess();


    }

    @Override
    public void getUsefulCarList(long accountId, final OnGetUsefulCarListListener listener) {
        OkHttpUtils.get(IURL.GET_USEFUL_CAR_LIST + accountId, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("请求失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("请求成功");
                String json = response.body().string();
                System.out.println("所有车辆信息" + json);
                Gson gson = new Gson();
                UsefulCarListResponseBean res = gson.fromJson(json, UsefulCarListResponseBean.class);

                if (res.isSuccess()) {
                    listener.getCarListSuccess(res.getData());
                } else {
                    listener.getCarListFailure(res.getMsg());
                }


            }
        });
    }
}
