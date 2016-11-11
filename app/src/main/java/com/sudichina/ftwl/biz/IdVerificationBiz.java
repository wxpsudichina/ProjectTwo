package com.sudichina.ftwl.biz;

import android.content.Context;

import com.google.gson.Gson;
import com.sudichina.ftwl.bean.CarOwnerBean;
import com.sudichina.ftwl.bean.CarOwnerResponseBean;
import com.sudichina.ftwl.bean.ResponseBean;
import com.sudichina.ftwl.utils.OkHttpUtils;
import com.sudichina.ftwl.utils.SPUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by mccccccmike on 2016/8/26.
 * <p/>
 * 车主身份认证逻辑功能类
 */
public class IdVerificationBiz implements IIdVerificationBiz {
    @Override
    public void verify(Context context, String realName, String driverLicence, String driverLicencePath, final OnIdVerifyListener listener) {
        if (realName.equals("")) {
            listener.IdVerifyFailed("请输入您的真实姓名");
            return;
        }

        if (driverLicence.equals("")) {
            listener.IdVerifyFailed("请输入您的驾驶证号码");
            return;
        }

        if (driverLicencePath == null || driverLicencePath.equals("")) {
            listener.IdVerifyFailed("请上传您的驾驶证图片");
            return;
        }

        Map<String, Object> data = new HashMap<>();
        long accountId = (long) SPUtils.get(context, "id", 0L);
        data.put("accountId", accountId);
        data.put("drivingLicense", driverLicence);//驾照号码
        data.put("realName", realName);

        //压缩图片
//        String image_file = (String) SPUtils.get(context, "IMGURL", "");
        Map<String, String> file = new HashMap<>();
        file.put("drivingLicensePath", driverLicencePath);
//        System.out.println(file);
//        file.put("drivingLicensePath", image_file);

        OkHttpUtils.formPost(IURL.ID_VERIFY, data, file, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("请求失败了。。。卧槽");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("成功了哈哈哈");
                String json = response.body().string();

                Gson gson = new Gson();
                ResponseBean responseObj = gson.fromJson(json, ResponseBean.class);

                if (responseObj.isSuccess()) {
                    listener.IdVerifySuccessful(responseObj.getMsg());
                } else {
                    listener.IdVerifyFailed(responseObj.getMsg());
                }
            }
        });
    }

    @Override
    public void getCarOwnerInfo(long id, final OnGetCarOwnerInfoListener listener) {
        OkHttpUtils.get(IURL.GET_CAR_OWNER_INFO + id, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("request failure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("request success");
                String json = response.body().string();
                System.out.println(json);
                Gson gson = new Gson();
                CarOwnerResponseBean bean = gson.fromJson(json, CarOwnerResponseBean.class);
                CarOwnerBean carOwnerBean = bean.getData();
                if (bean.isSuccess()) {
                    listener.getCarOwnerInfoSuccess(carOwnerBean);
                } else {
                    listener.getCarOwnerInfoFailed(carOwnerBean);

                }

            }
        });
    }
}
