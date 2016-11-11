package com.sudichina.ftwl.biz;

/**
 * Created by mccccccmike on 2016/8/25.
 */
public interface IURL {
    String SIGN_UP = "http://192.168.1.66:8888/services/accounts/register";

    String SIGN_IN = "http://192.168.1.66:8888/services/accounts/doLogin";

    String SEND_IC = "http://192.168.1.66:8888/services/sms/send";

    String VERIFY_IC = "http://192.168.1.66:8888/services/sms/verification";

    String RESET_PWD = "http://192.168.1.66:8888/services/accounts/resetPassword";

    String ID_VERIFY = "http://192.168.1.66:8888/services/carOwner/carOwnerAuthentication";

    String VEHICLE_VERIFY = "http://192.168.1.66:8888/services/car/carAuthentication";

    String GET_CAR_INFO = "http://192.168.1.66:8888/services/dic/listDic";

    String GET_MY_CARS = "http://192.168.1.66:8888/services/car/queryList";

    String GET_SQUARE_DUN = "http://192.168.1.66:8888/services/dic/dicGorup";

    String DELETE_VEHICLE = "http://192.168.1.66:8888/services/car/delete/";

    String GET_VERIFIED_VEHICLE_INFO = "http://192.168.1.66:8888/services/car/loadCar/";

    String GET_HOST_CITY = "";

    String GET_CAR_OWNER_INFO = "http://192.168.1.66:8888/services/carOwner/loadCarOwnerByAccountId/";

    String GET_CITY_LIST = "";

    String GET_CAR_ROUND = "";

    String GET_CAR_LIST = "";

    String QUICK_SIGN_IN = "http://192.168.1.66:8888/services/sms/shortcutLogin";

    String GET_USEFUL_CAR_LIST = "http://192.168.1.66:8888/services/car/approveCar/";
}
