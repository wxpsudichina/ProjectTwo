package com.sudichina.ftwl.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.sudichina.ftwl.R;
import com.sudichina.ftwl.biz.IURL;
import com.sudichina.ftwl.presenter.HomePresenter;
import com.sudichina.ftwl.utils.SPUtils;

import java.util.List;

/**
 * Created by SudiChina-105 on 2016/8/13.
 * 零担王页面
 */
public class ZeroDActivity extends BaseActivity implements IZeroDView, LocationSource, AMapLocationListener, View.OnClickListener {
    private AMap aMap;
    private MapView mapView;
    private LocationSource.OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private EditText et_start;//开会地址
    private EditText et_dest;//结束地址
    private EditText et_car;//选择车辆
    private EditText et_date;//选择日期
    private EditText et_price;//价格选择
    private EditText et_value_added_service;//增值服务
    private EditText et_fang, et_dun;
    public Button btn_pub;//提交
    private Button btn_price;
    private CalendarDay calendarDay;
    Dialog dialog;
    static List<String> list_car;
    List<Long> carIdList;
    String results = "";

    long accountId;//用户id
    long carid;//车辆id
    long toZoneCode;//到达地区code
    long fromZoneCode;//开始地区code
    String laLoPosition;//经度，纬度
    String fromAddress;//开始详细地址
    String releaseTime;//发车时间
    String arriveTime;//到达时间
    String type = "less_than_carload";//类型
    double priceDun;//吨的单价
    double priceSquare;//方的单价
    String addService;//增值服务

    public long getAccountId() {
        return (long) SPUtils.get(this, "id", 0L);
    }

    public long getCarid() {
        return carid;
    }

    public long getToZoneCode() {
        return toZoneCode;
    }

    public long getFromZoneCode() {
        return fromZoneCode;
    }

    public String getLaLoPosition() {
        return laLoPosition;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public String getArriveTime() {
        return arriveTime;
    }

    public String getType() {
        return type;
    }

    public double getPriceDun() {
        return priceDun;
    }

    public double getPriceSquare() {
        return priceSquare;
    }

    public String getAddService() {
        return addService;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zero);
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        initMap();//初始化地图
//        getConect();//链接网络，获取车辆列表
        initData();//加载数据
        initView();//获取控件
        initEvent();
    }
    private void initMap() {
        if (aMap == null) {
            aMap = mapView.getMap();
            aMap.setLocationSource(this);// 设置定位监听
            aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
            aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
            /**
             * 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
             * aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);设置定位的类型为定位模式
             * aMap.setMyLocationType(AMap.LOCATION_TYPE_MAP_FOLLOW);设置定位的类型为 跟随模式
             * aMap.setMyLocationType(AMap.LOCATION_TYPE_MAP_ROTATE);设置定位的类型为根据地图面向方向旋转
             */
            aMap.setMyLocationType(AMap.LOCATION_TYPE_MAP_ROTATE);//旋转模式
        }
    }
    @Override
    protected void initData() {
        //开始地址的code
        fromZoneCode = (long) SPUtils.get(ZeroDActivity.this, "fromZoneCode", 0L);//开始地区的code（城市列表）
        fromZoneCode = (long) SPUtils.get(ZeroDActivity.this, "hot_id", 0L);//热门城市的code（热门城市）
        fromZoneCode = (long) SPUtils.get(ZeroDActivity.this, "search_city_code", 0L);//（搜索的城市）
        System.out.println("st_fromZoneCode" + fromZoneCode);
        //结束地址的code
        toZoneCode = (long) SPUtils.get(ZeroDActivity.this, "fromZoneCode", 0L);//结束地区的code
        toZoneCode = (long) SPUtils.get(ZeroDActivity.this, "hot_id", 0L);//结束地区的code
        toZoneCode = (long) SPUtils.get(ZeroDActivity.this, "search_city_code", 0L);//结束地区的code
        System.out.println("en_toZoneCode" + toZoneCode);
        //开始地址和结束地址
        fromAddress = (String) SPUtils.get(ZeroDActivity.this, "search_city_", "");
        fromAddress = (String) SPUtils.get(ZeroDActivity.this, "from_tv", "");
        fromAddress = (String) SPUtils.get(ZeroDActivity.this, "hotcity", "");
        System.out.println("fromAddress" + fromAddress);
    }
//    联网请求
//    private void getConect() {
////        showDialog();
//        accountId = (long) SPUtils.get(ZeroDActivity.this, "id", 0L);
//        System.out.println("=====================" + accountId);
//        String url = IURL.GET_CAR_LIST + accountId;
//        OkHttpUtils.get(url, new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                System.out.println("请求失败");
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                System.out.println("请求成功");
//                String json = response.body().string();
//                System.out.println("所有车辆信息" + json);
//                Gson gson = new Gson();
//                CarListData carListData = gson.fromJson(json, CarListData.class);
//                List<CarList> list = carListData.getData();
////               listcars = carListData.getData();
//                list_car = new ArrayList<String>();
//                carIdList = new ArrayList<>();
//                for (int i = 0; i < list.size(); i++) {
//                    list_car.add(list.get(i).getCardNum());
//                    carIdList.add(list.get(i).getId());
////                    listcars.get(i).getId();
//                }
//
////                dismissDialog();
//                System.out.println("所有的车辆信息" + list_car);
//            }
//        });
//    }
    @Override
    protected void initView() {
        et_start = (EditText) findViewById(R.id.et_start);
        et_dest = (EditText) findViewById(R.id.et_dest);
        et_car = (EditText) findViewById(R.id.et_car);
        et_date = (EditText) findViewById(R.id.et_date);
        et_price = (EditText) findViewById(R.id.et_price);
        et_value_added_service = (EditText) findViewById(R.id.et_value_added_service);
        btn_pub = (Button) findViewById(R.id.btn_pub);
        et_price.setOnClickListener(this);
        et_car.setOnClickListener(this);
        btn_pub.setOnClickListener(this);
        et_value_added_service.setOnClickListener(this);
        et_dest.setOnClickListener(this);
        et_date.setOnClickListener(this);
        et_value_added_service.setOnClickListener(this);
        btn_pub.setOnClickListener(this);
        et_start.setOnClickListener(this);//开始地址，点击，跳转到城市页面
        et_start.setText(fromAddress);
        et_dest.setText(fromAddress);
    }
    @Override
    protected void initEvent() {

    }
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation != null
                    && aMapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
                double latitude = aMapLocation.getLatitude();//获取纬度
                double longitude = aMapLocation.getLongitude();//获取经度
                laLoPosition = String.valueOf(latitude + "," + longitude);
                String city = aMapLocation.getCity();//城市
                SPUtils.put(ZeroDActivity.this, "city", city);
                et_start.setText(city);//将定位的地址赋给开始地址
                System.out.println("我的位置" + city);
                System.out.println("经度" + longitude + "纬度" + latitude);
            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }
    }
    //激活定位
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }
    //停止定位
    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        deactivate();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        if (null != mlocationClient) {
            mlocationClient.onDestroy();
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.et_date:
                showCalendarView_start();
                break;
            case R.id.et_value_added_service://增值服务
                showAddService();
                break;
            case R.id.btn_pub:
                break;
            case R.id.et_start://开始地址
                startActivity(new Intent(ZeroDActivity.this, SortCityActivity.class));
                overridePendingTransition(R.anim.out_dialog, R.anim.out_dialog);//设置城市列表弹出的动画
                break;
            case R.id.et_dest://目的地，结束地址2
                startActivity(new Intent(ZeroDActivity.this, SortCityActivity.class));
                overridePendingTransition(R.anim.out_dialog, R.anim.out_dialog);//设置城市列表弹出的动画
                break;
            case R.id.et_price://价格，展示下拉框
                showDialog_price();
                break;
            case R.id.zero_dialog_btn://价格选择框里面的按钮
                priceSquare = Double.valueOf(et_fang.getText().toString());
                priceDun = Double.valueOf(et_dun.getText().toString());
                et_price.setText(priceSquare + "元/方" + "," + priceDun + "元/吨");
                dialog.dismiss();
                break;
            case R.id.et_car://车辆选择
                showCarWheelview_start();
                break;
        }
    }
    //展示车辆
    private void showCarWheelview_start() {//展示底部弹出框
        dialog = new Dialog(this, R.style.main_menu_animstyle);
        View view = getLayoutInflater().inflate(R.layout.zero_dialog_car, null);
        ListView et_car_lv = (ListView) view.findViewById(R.id.et_car_lv);
        ArrayAdapter adapter = new ArrayAdapter(ZeroDActivity.this, R.layout.car_list, R.id.car_firstname, list_car);
        et_car_lv.setAdapter(adapter);
        et_car_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String car_tv = list_car.get(position);
                et_car.setText(car_tv);
                carid = carIdList.get(position);
                dialog.dismiss();//弹出框消失
            }
        });
        dialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = getWindowManager().getDefaultDisplay().getHeight();
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        // 设置显示位置
        dialog.onWindowAttributesChanged(wl);
        // 设置点击外围解散
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }
    //结束时间
    private void showCalendarView_stop() {//结束运期
        final Dialog dialog = new Dialog(this, R.style.diyDialog);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_date, null);
        final MaterialCalendarView calendarView = (MaterialCalendarView) view.findViewById(R.id.calendarView);
//        Button btn_ok = (Button) view.findViewById(R.id.btn_ok);
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                Log.d("hlm", date.toString());
                Log.d("hlm", "selected = " + selected);
                calendarDay = date;
                arriveTime = date.getYear() + "-" + (date.getMonth() + 1) + "-" + date.getDay();//结束时间
                et_date.setText(releaseTime+"-"+arriveTime);
                dialog.dismiss();
            }
        });
//        btn_ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        if (calendarDay != null) {
//                    calendarView.setDateSelected(calendarDay, true);
            calendarView.setDateSelected(calendarDay, true);
            calendarView.setCurrentDate(calendarDay);
        }
        dialog.setContentView(view);
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        lp.width = outMetrics.widthPixels;
        lp.height = outMetrics.heightPixels;
        dialog.getWindow().setAttributes(lp);
        dialog.show();
    }
    //增值服务
    private void showAddService() {
//        final View view = getLayoutInflater().inflate(R.layout.zero_dialog_addservice, null, false);
//        er_car_popPopupWindow = new PopupWindow(view, 320, 330, true);
//        ListView et_caraddservice_lv = (ListView) view.findViewById(R.id.zero_popaddservice);
//        Button btn_add= (Button) view.findViewById(R.id.zero_addservice_btn);
//        String[] addservices=new String[]{"加油","发票","保险"};
//        adapter=new ArrayAdapter(this,R.layout.car_addservice,R.id.car_addservice_tv,addservices);
//        et_caraddservice_lv.setAdapter(adapter);
//        CheckBox checkBox= (CheckBox) view.findViewById(R.id.addservice_box);
//        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//            }
//        });
        final String[] addservices = new String[]{"加油", "发票", "保险"};
        final boolean[] flags = new boolean[]{false, false, false};//初始复选情况
        Dialog dialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMultiChoiceItems(addservices, flags, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                flags[which] = isChecked;
                for (int i = 0; i < flags.length; i++) {
                    if (flags[i]) {
                        results = results + addservices[i];
                        addService = results;
                    }
                }
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                et_value_added_service.setText(results);
            }
        });
        dialog = builder.create();
        dialog.show();
    }
    //价格弹出框
    private void showDialog_price() {
        dialog = new Dialog(this, R.style.main_menu_animstyle);
        View view = getLayoutInflater().inflate(R.layout.zero_dialog_price, null);
        btn_price = (Button) view.findViewById(R.id.zero_dialog_btn);//确定按钮
        et_fang = (EditText) view.findViewById(R.id.zero_et_price_fang);//方
        et_dun = (EditText) view.findViewById(R.id.zero_et_price_dun);//吨
        btn_price.setOnClickListener(this);
        dialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = getWindowManager().getDefaultDisplay().getHeight();
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        // 设置显示位置
        dialog.onWindowAttributesChanged(wl);
        // 设置点击外围解散
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }
    //展示日历弹出框
    private void showCalendarView_start() {
        final Dialog dialog = new Dialog(this, R.style.diyDialog);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_date, null);
        final MaterialCalendarView calendarView = (MaterialCalendarView) view.findViewById(R.id.calendarView);
//        Button btn_ok = (Button) view.findViewById(R.id.btn_ok);
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                Log.d("hlm", date.toString());
                Log.d("hlm", "selected = " + selected);
                calendarDay = date;
                releaseTime = date.getYear() + "-" + (date.getMonth() + 1) + "-" + date.getDay();//开始时间
                dialog.dismiss();
                showCalendarView_stop();
            }
        });
//        btn_ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//            }
//        });
        if (calendarDay != null) {
//                    calendarView.setDateSelected(calendarDay, true);
            calendarView.setDateSelected(calendarDay, true);
            calendarView.setCurrentDate(calendarDay);
        }
        dialog.setContentView(view);
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        lp.width = outMetrics.widthPixels;
        lp.height = outMetrics.heightPixels;
        dialog.getWindow().setAttributes(lp);
        dialog.show();
    }

}
