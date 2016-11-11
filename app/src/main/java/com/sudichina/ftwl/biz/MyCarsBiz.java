package com.sudichina.ftwl.biz;

import com.google.gson.Gson;
import com.sudichina.ftwl.bean.AccountIdBean;
import com.sudichina.ftwl.bean.CarBean;
import com.sudichina.ftwl.bean.CarListResponseBean;
import com.sudichina.ftwl.bean.ResponseBean;
import com.sudichina.ftwl.utils.OkHttpUtils;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by mccccccmike on 2016/9/2.
 */
public class MyCarsBiz implements IMyCarsBiz {
    @Override
    public void getCarList(long accountId, final OnGetCarListListener listener) {
        AccountIdBean accountIdBean = new AccountIdBean(accountId);
        OkHttpUtils.jsonPost(IURL.GET_MY_CARS, accountIdBean, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("request failure");
                listener.requestFailed();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("request success");
//                System.out.println(response.body().string());
                String json = response.body().string();
                System.out.println(json);
                Gson gson = new Gson();
                CarListResponseBean carListResponseBean = gson.fromJson(json, CarListResponseBean.class);
                List<CarBean> carBeanList = carListResponseBean.getData();
                if (carListResponseBean.isSuccess()) {
                    listener.getCarListSuccess(carBeanList);
                } else {
                    listener.getCarListFailure(carListResponseBean.getMsg());
                }
            }
        });
    }

    @Override
    public void deleteVehicle(long id, final OnDeleteVehicleListener listener) {

        OkHttpUtils.delete(IURL.DELETE_VEHICLE + id, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("request failure...");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("request success...");

                String json = response.body().string();
                System.out.println(json);

                Gson gson = new Gson();
                ResponseBean responseBean = gson.fromJson(json, ResponseBean.class);

                if (responseBean.isSuccess()) {
                    listener.deleteVehicleSuccess(responseBean.getMsg());
                } else {
                    listener.deleteVehicleFailed(responseBean.getMsg());
                }
            }
        });
    }
}
