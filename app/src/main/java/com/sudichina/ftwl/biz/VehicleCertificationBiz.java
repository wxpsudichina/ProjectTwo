package com.sudichina.ftwl.biz;

import com.google.gson.Gson;
import com.sudichina.ftwl.bean.CarInfoBean;
import com.sudichina.ftwl.bean.CarInfoResponseBean;
import com.sudichina.ftwl.bean.DicGroupBean;
import com.sudichina.ftwl.bean.DicGroupIdBean;
import com.sudichina.ftwl.bean.DicValueBean;
import com.sudichina.ftwl.bean.MyCarBean;
import com.sudichina.ftwl.bean.MyCarInfoBean;
import com.sudichina.ftwl.bean.MyCarInfoResponseBean;
import com.sudichina.ftwl.bean.ResponseBean;
import com.sudichina.ftwl.bean.VerifiedVehicleInfoResponseBean;
import com.sudichina.ftwl.utils.OkHttpUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by mccccccmike on 2016/8/30.
 */
public class VehicleCertificationBiz implements IVehicleCertificationBiz {
    @Override
    public String getCarInfo(final OnRequestCarInfoListener listener) {

        OkHttpUtils.get(IURL.GET_CAR_INFO, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("request failure...");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("request success...");

                Gson gson = new Gson();
                CarInfoResponseBean responseObj = gson.fromJson(response.body().string(), CarInfoResponseBean.class);

                if (!responseObj.isSuccess()) {
                    listener.requestCarInfoFailure();
                    return;
                }

                System.out.println(responseObj);
                CarInfoBean carInfoBean = responseObj.getData();
                List<DicGroupBean> dicGroupBeen = carInfoBean.getDicGroup();
                List<DicValueBean> dicValueBeen = carInfoBean.getDicValue();
                for (int i = 0; i < dicGroupBeen.size(); i++) {
                    DicGroupBean d = dicGroupBeen.get(i);
                    System.out.println("id === " + d.getId());
                }

                listener.requestCarInfoSuccess(dicGroupBeen, dicValueBeen);

            }
        });
        return null;
    }

    @Override
    public void verify(long accountId, String carFirstLetter, String cardNumber, String carDicId, String driveLicense, String driveLicensePath, String driveLicensePath2, final OnRequestListener listener) {
//        System.out.println("认证的逻辑");
//
//
//        if (carFirstLetter.equals("")) {
//
//            return;
//        }
//
//        if (driveLicensePath == null) {
//
//            return;
//        }
//
//        if (driveLicensePath2 == null) {
//
//            return;
//        }


        System.out.println("accountId = " + accountId);
        System.out.println("carFirstLetter = " + carFirstLetter);
        System.out.println("cardNumber = " + cardNumber);
        System.out.println("carDicId = " + carDicId);
        System.out.println("driveLicense = " + driveLicense);
        System.out.println("driveLicensePath = " + driveLicensePath);
        System.out.println("driveLicensePath2 = " + driveLicensePath2);

        Map<String, Object> data = new HashMap<>();
        data.put("accountId", accountId);
        data.put("carFirstLetter", carFirstLetter);
        data.put("cardNumber", cardNumber);
        data.put("carDicId", carDicId);
        data.put("engineNum", driveLicense);

        Map<String, String> data1 = new HashMap<>();
        data1.put("driveLicensePath", driveLicensePath);
        data1.put("driveLicensePath2", driveLicensePath2);
        OkHttpUtils.formPost(IURL.VEHICLE_VERIFY, data, data1, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("请求失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("请求成功");
//                System.out.println(response.body().toString());
                String json = response.body().string();

                Gson gson = new Gson();
                ResponseBean responseBean = gson.fromJson(json, ResponseBean.class);
                if (responseBean.isSuccess()) {
                    listener.requestSuccess();
                } else {
                    listener.requestFailed();
                }

            }
        });
    }

//    public void getCarSquareDun(long dicGroupId, final OnGetVerifiedVehicleInfoListener listener) {
//        DicGroupIdBean dicGroupIdBean = new DicGroupIdBean(dicGroupId);
//        System.out.println(dicGroupIdBean);
//        OkHttpUtils.jsonPost(IURL.GET_SQUARE_DUN, dicGroupIdBean, new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                System.out.println("request failure");
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                System.out.println("request success");
//                String json = response.body().string();
//                System.out.println(json);
//
//                Gson gson = new Gson();
//                MyCarInfoResponseBean myCarInfoResponseBean = gson.fromJson(json, MyCarInfoResponseBean.class);
//                MyCarInfoBean myCarInfoBean = myCarInfoResponseBean.getData();
//                if (myCarInfoResponseBean.isSuccess()) {
//                    listener.getCarSquareDunSuccess(myCarInfoBean);
//                } else {
//                    listener.getCarSquareDunFailed();
//                }
//            }
//        });
//    }


    @Override
    public void getVerifiedVehicleInfo(long id, final OnGetVerifiedVehicleInfoListener listener) {
        OkHttpUtils.get(IURL.GET_VERIFIED_VEHICLE_INFO + id, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("request failure.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("request success..");
                String json = response.body().string();
                System.out.println("bbbbbbbbbbbbbbbbbbbbbbb");
                System.out.println(json);

                Gson gson = new Gson();
                VerifiedVehicleInfoResponseBean bean = gson.fromJson(json, VerifiedVehicleInfoResponseBean.class);
                MyCarBean myCarBean = bean.getData();
                if (bean.isSuccess()) {
                    listener.getVerifiedVehicleInfoSuccess(myCarBean);
                } else {
                    listener.getVerifiedVehicleInfoFailed();
                }


            }
        });
    }

    @Override
    public void modifyVehicleInfo(long id, long accountId, String carFirstLetter, String cardNumber, String carDicId, String engineNum, String driveLicensePath, String driveLicensePath2, final OnModifyVehicleInfoListener listener) {
        System.out.println("修改车辆信息的逻辑");
        System.out.println("id === " + id);
        System.out.println("accountId === " + accountId);
        System.out.println("carFirstLetter === " + carFirstLetter);
        System.out.println("cardNumber === " + cardNumber);
        System.out.println("carDicId === " + carDicId);
        System.out.println("engineNum === " + engineNum);
        System.out.println("driveLicensePath === " + driveLicensePath);
        System.out.println("driveLicensePath2 === " + driveLicensePath2);

        Map<String, Object> data = new HashMap<>();
        data.put("id", id);
        data.put("accountId", accountId);
        data.put("carFirstLetter", carFirstLetter);
        data.put("cardNumber", cardNumber);
        data.put("carDicId", carDicId);
        data.put("engineNum", engineNum);

        Map<String, String> files = new HashMap<>();
        files.put("driveLicensePath", driveLicensePath);
        files.put("driveLicensePath2", driveLicensePath2);



        OkHttpUtils.formPost(IURL.VEHICLE_VERIFY, data, files, new Callback() {
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
                ResponseBean res = gson.fromJson(json, ResponseBean.class);
                if (res.isSuccess()) {
                    listener.modifyVehicleInfoSuccess(res.getMsg());
                } else {
                    listener.modifyVehicleInfoFailed(res.getMsg());
                }

            }
        });
    }
}
