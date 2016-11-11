package com.sudichina.ftwl.view;

import android.animation.ValueAnimator;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.MyLocationStyle;
import com.andexert.calendarlistview.library.DatePickerController;
import com.andexert.calendarlistview.library.DayPickerView;
import com.andexert.calendarlistview.library.SimpleMonthAdapter;
import com.sudichina.ftwl.R;
import com.sudichina.ftwl.bean.UsefulCarBean;
import com.sudichina.ftwl.customview.ScalePageTransformer;
import com.sudichina.ftwl.customview.WheelView;
import com.sudichina.ftwl.presenter.HomePresenter;
import com.sudichina.ftwl.utils.CommonUtils;
import com.sudichina.ftwl.utils.SPUtils;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by SudiChina-105 on 2016/9/9.
 * 首页，加载地图
 */
public class HomeFragment extends BaseFragment implements IHomeView, LocationSource, AMapLocationListener, View.OnClickListener {
    private AMap aMap;
    private MapView mapView;
    private LocationSource.OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;

    private RelativeLayout rl_offer;

    private EditText et_start;//开始地址
    private EditText et_stop;//结束地址
    private EditText et_offer;//整车报价
    private EditText et_per_ton;//每吨报价
    private EditText et_per_kilogram;//每公斤报价
    private EditText et_per_cubic;//每立方报价
    private EditText et_door_to_door;
    private EditText et_door_to_cfs;

    private Button btn_car_offer;
    private Button btn_zero_offer;
    private Button btn_release_route;
    private Button btn_single_vehicle;//发布车辆选择
    private Button btn_date;//运期
    private ImageView iv_release_route;//发布路线的图片
    private ImageView image_head;//头像
    private ImageView iv_reservation;
    private ImageView iv_menu;
    private ImageView iv_test;
    private LinearLayout ll_zero;
    private LinearLayout ll_top;
    private LinearLayout ll_bottom;
    private LinearLayout ll_container;
    private LinearLayout ll_invoice;
    private LinearLayout ll_release_complete;
    private RelativeLayout title_bar;
    private FrameLayout fl_view_pager;

    private ViewPager viewPager;
    private List<View> views;
    private PagerAdapter pagerAdapter;

    private ImageView iv_invoice;
    private int ll_bottom_height;

    private DayPickerView dayPickerView;
    Dialog dialog;
    DatePickerController mController;
    SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd");
    ParsePosition pos = new ParsePosition(6);

    private TextView tv_ok, tv_cancel;
    String tv_car_item;//wheelview选中的item项，这里指的是选中的派单车辆
    private long currentId;
    private int currentIndex = 1;

    private boolean flag;

    @Override
    public boolean isFlag() {
        return flag;
    }

    @Override
    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    private boolean ll_release_complete_is_show;

    @Override
    public boolean isLl_release_complete_is_show() {
        return ll_release_complete_is_show;
    }

    @Override
    public void setLl_release_complete_is_show(boolean ll_release_complete_is_show) {
        this.ll_release_complete_is_show = ll_release_complete_is_show;
    }

    private HomePresenter homePresenter = new HomePresenter(this);

    private List<String> carNoList = new ArrayList<>();
    private List<Long> carIdList = new ArrayList<>();

    private ProgressDialog progressDialog;

    private String time1;
    private String time2;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取车辆列表
        homePresenter.getUsefulCarList();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);
        mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        initMap();//初始化地图
        initData();
        initView(view);

        viewPager.setAdapter(pagerAdapter);

        initEvent();
        return view;
    }

    //初始化地图
    private void initMap() {
        if (aMap == null) {
            aMap = mapView.getMap();
            aMap.setLocationSource(this);
            aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
            aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
            MyLocationStyle myLocationStyle = new MyLocationStyle();
            myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.mipmap.iv_home_fragment_maplog));
            aMap.setMyLocationStyle(myLocationStyle);
            // 自定义定位图标
            /**
             * 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
             * aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);设置定位的类型为定位模式
             * aMap.setMyLocationType(AMap.LOCATION_TYPE_MAP_FOLLOW);设置定位的类型为 跟随模式
             * aMap.setMyLocationType(AMap.LOCATION_TYPE_MAP_ROTATE);设置定位的类型为根据地图面向方向旋转
             */
            aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);//旋转模式
        }
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {//定位监听
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation != null
                    && aMapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
                double latitude = aMapLocation.getLatitude();//获取纬度
                double longitude = aMapLocation.getLongitude();//获取经度
                String city = aMapLocation.getAddress();//城市
                et_start.setText(city);//将定位的地址赋给开始地址
                System.out.println("我的位置" + city);
                System.out.println("经度" + longitude + "纬度" + latitude);
            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {//激活定位
        mListener = onLocationChangedListener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(getActivity());
            mLocationOption = new AMapLocationClientOption();
            mLocationOption.setInterval(1000);
            mLocationOption.setOnceLocation(true);
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
            mlocationClient.startLocation();//启动定位
        }
    }

    @Override
    public void deactivate() {//取消定位
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    @Override
    public void onClick(View view) {//控件监听
        switch (view.getId()) {
            case R.id.btn_single_vehicle:
                showSelectCar();//展示派单车辆
                break;
            case R.id.btn_car_offer:
                resetOffer();
                btn_car_offer.setBackgroundResource(R.drawable.btn_offer_bg);
                et_offer.setVisibility(View.VISIBLE);
                ll_zero.setVisibility(View.GONE);
                break;
            case R.id.btn_zero_offer:
                resetOffer();
                btn_zero_offer.setBackgroundResource(R.drawable.btn_offer_bg);
                et_offer.setVisibility(View.GONE);
                ll_zero.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_release_route://点击按钮，，，划出隐藏的布局
                //首先判断发布路线完成是否正在显示，显示则隐藏
                if (ll_release_complete_is_show) {
                    hideContent(ll_release_complete.getMeasuredHeight(), ll_release_complete, 300);
                    ll_release_complete_is_show = false;
                }

                System.out.println("click me la");
                flag = !flag;
                if (flag) {
                    showContent(ll_bottom_height, ll_container, 600);
                } else {
                    hideContent(ll_bottom_height, ll_container, 600);
                }
                break;
            case R.id.ll_invoice: {
                toggleState(view, ll_invoice, iv_invoice);
                break;
            }
            case R.id.btn_release_route://确认发布路线
                homePresenter.releaseRoute();
                //
                break;
            case R.id.btn_date://选择运期
                showDataPicker();//展示日历控件
                break;
            case R.id.home_fragment_tv_ok://确定派单车辆
                btn_single_vehicle.setText(tv_car_item);
                dialog.dismiss();
                break;
            case R.id.home_fragment_tv_cancel://取消车辆选择
                dialog.dismiss();
                break;
            case R.id.iv_menu:
                System.out.println("点击了我");
                MainActivity ma = (MainActivity) getActivity();
                ma.getDrawer_layout().openDrawer(Gravity.LEFT);
                break;
            case R.id.iv_test:
//                Dialog dialog = new Dialog(getContext(), R.style.diyDialog);
//                View dialog_test = getActivity().getLayoutInflater().inflate(R.layout.dialog_test, null);
//                dialog.setContentView(dialog_test);
//
//                Window window = dialog.getWindow();
//                WindowManager.LayoutParams lp = window.getAttributes();
//                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//                window.setAttributes(lp);
//
//                dialog.show();
                f = !f;
                if (f) fl_view_pager.setVisibility(View.VISIBLE);
                else fl_view_pager.setVisibility(View.GONE);
                break;
        }
    }

    private boolean f;

    @Override
    public void showContent(int height, final ViewGroup vg, long duration) {
        ValueAnimator animator = ValueAnimator.ofInt(-height, 0);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                vg.setPadding(vg.getPaddingLeft(), vg.getPaddingTop(), vg.getPaddingRight(), (Integer) valueAnimator.getAnimatedValue());
            }
        });
        animator.setDuration(duration);
        animator.start();
    }

    private void showDataPicker() { //展示日历控件，选择运期
        dialog = new Dialog(getActivity(), R.style.diyDialog);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.pick_view, null);
        dayPickerView = (DayPickerView) view.findViewById(R.id.pickerView);
        mController = new DatePickerController() {
            @Override
            public int getMaxYear() {
                return 2050;
            }

            @Override
            public void onDayOfMonthSelected(int year, int month, int day) {
                Log.e("Day Selected", day + " / " + month + " / " + year);
            }

            @Override
            public void onDateRangeSelected(SimpleMonthAdapter.SelectedDays<SimpleMonthAdapter.CalendarDay> selectedDays) {
                Date firsttime = selectedDays.getFirst().getDate();
                Date lasttie = selectedDays.getLast().getDate();
                time1 = format.format(firsttime);
                time2 = format.format(lasttie);
                format.format(lasttie);
                System.out.println("所选日期" + time1 + time2);
                btn_date.setText(time1 + "" + time2);
                dialog.dismiss();
            }
        };
        dayPickerView.setController(mController);
        dialog.setContentView(view);
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        lp.width = outMetrics.widthPixels;
        lp.height = outMetrics.heightPixels;
        dialog.getWindow().setAttributes(lp);
        dialog.show();
    }

    //展示车辆
    private void showSelectCar() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.wheel_view, null);
//        String[] str = new String[]{"晋 A7490", "京969Q8", "京26266", "京96PO0", "沪 WXP5896"};
        WheelView wheelView = (WheelView) view.findViewById(R.id.wheel_view_wv);
//        wheelView.setOffset(3);
        wheelView.setItems(carNoList);//定义数据
        wheelView.setSeletion(currentIndex);
        wheelView.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                super.onSelected(selectedIndex, item);
//                System.out.println("selctedIndex = " + selectedIndex);
                tv_car_item = item;
                currentId = carIdList.get(selectedIndex - 1);
                currentIndex = selectedIndex;
////                btn_single_vehicle.setText(item);//将选中的值赋给控件
                System.out.println("currentId" + currentId);
                System.out.println("currentIndex" + currentIndex);
            }
        });
        dialog = new Dialog(getActivity(), R.style.main_menu_animstyle);
        dialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        TextView tv_ok = (TextView) view.findViewById(R.id.home_fragment_tv_ok);//确定
        TextView tv_cancel = (TextView) view.findViewById(R.id.home_fragment_tv_cancel);//取消
        tv_ok.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        Window window = dialog.getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = getActivity().getWindowManager().getDefaultDisplay().getHeight();
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = 400;
        // 设置显示位置
        dialog.onWindowAttributesChanged(wl);
        // 设置点击外围解散
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

    }

    private void toggleState(View view, LinearLayout ll, ImageView iv) {
        String tag = (String) view.getTag();
        if (tag.equals("unchecked")) {
            iv.setVisibility(View.VISIBLE);
            ll_container.setBackgroundResource(R.drawable.btn_selected);
            view.setTag("checked");
        } else if (tag.equals("checked")) {
            iv.setVisibility(View.GONE);
            ll_container.setBackgroundResource(R.drawable.btn_normal);
            view.setTag("unchecked");
        }
    }

    private void resetOffer() {
        //重置
        btn_car_offer.setBackgroundColor(0xffffffff);
        btn_zero_offer.setBackgroundColor(0xffffff);
    }

    @Override
    protected void initData() {
        //初始化发布完成遮罩层页面数据
        views = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            View view = getActivity().getLayoutInflater().inflate(R.layout.item_test, null);
            views.add(view);
        }

        pagerAdapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return views.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view = views.get(position);
                ImageView iv_close = (ImageView) view.findViewById(R.id.iv_close);
                iv_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fl_view_pager.setVisibility(View.GONE);
                        f = !f;
                    }
                });

                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
//                super.destroyItem(container, position, object);
                container.removeView(views.get(position));
            }
        };
    }

    @Override
    protected void initView(View view) {
        et_start = (EditText) view.findViewById(R.id.et_start);
        et_stop = (EditText) view.findViewById(R.id.et_stop);
//        image_head = (ImageView) view.findViewById(R.id.iv_home_fragment_own);
        iv_reservation = (ImageView) view.findViewById(R.id.iv_reservation);
        ll_container = (LinearLayout) view.findViewById(R.id.ll_container);
        ll_bottom = (LinearLayout) view.findViewById(R.id.ll_bottom);
        ll_bottom.post(new Runnable() {
            @Override
            public void run() {
                ll_bottom_height = ll_bottom.getMeasuredHeight();
                System.out.println("h = " + ll_bottom_height);
                ll_container.setPadding(ll_container.getPaddingLeft(), ll_container.getPaddingTop(), ll_container.getPaddingRight(), -ll_bottom_height);
            }
        });
        rl_offer = (RelativeLayout) view.findViewById(R.id.rl_offer);

        btn_single_vehicle = (Button) view.findViewById(R.id.btn_single_vehicle);//拍担车辆
        btn_date = (Button) view.findViewById(R.id.btn_date);//运期
        btn_car_offer = (Button) view.findViewById(R.id.btn_car_offer);
        btn_car_offer.setBackgroundResource(R.drawable.btn_offer_bg);
        btn_zero_offer = (Button) view.findViewById(R.id.btn_zero_offer);
        btn_release_route = (Button) view.findViewById(R.id.btn_release_route);

        fl_view_pager = (FrameLayout) view.findViewById(R.id.fl_view_pager);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        viewPager.setPageMargin(36);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setPageTransformer(true, new ScalePageTransformer());

        ll_zero = (LinearLayout) view.findViewById(R.id.ll_zero);
        ll_invoice = (LinearLayout) view.findViewById(R.id.ll_invoice);
        ll_release_complete = (LinearLayout) view.findViewById(R.id.ll_release_complete);
        ll_release_complete.post(new Runnable() {
            @Override
            public void run() {
                RelativeLayout.LayoutParams re_lp = (RelativeLayout.LayoutParams) iv_reservation.getLayoutParams();
                re_lp.bottomMargin = ll_release_complete.getMeasuredHeight();
                iv_reservation.setLayoutParams(re_lp);


                ll_release_complete.setPadding(
                        ll_release_complete.getPaddingLeft(),
                        ll_release_complete.getPaddingTop(),
                        ll_release_complete.getPaddingRight(),
                        -ll_release_complete.getMeasuredHeight()
                );

                Rect frame = new Rect();
                getActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
                int statusBarHeight = frame.top;

                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) fl_view_pager.getLayoutParams();
                lp.height = CommonUtils.getScreenHeight(getContext()) - ll_release_complete.getMeasuredHeight() - statusBarHeight;
                fl_view_pager.setLayoutParams(lp);

            }
        });

        iv_release_route = (ImageView) view.findViewById(R.id.iv_release_route);
        iv_invoice = (ImageView) view.findViewById(R.id.iv_invoice);
        iv_menu = (ImageView) view.findViewById(R.id.iv_menu);
        iv_test = (ImageView) view.findViewById(R.id.iv_test);

        et_offer = (EditText) view.findViewById(R.id.et_offer);
        et_door_to_door = (EditText) view.findViewById(R.id.et_door_to_door);
        et_door_to_cfs = (EditText) view.findViewById(R.id.et_door_to_cfs);
        et_per_ton = (EditText) view.findViewById(R.id.et_per_ton);
        et_per_kilogram = (EditText) view.findViewById(R.id.et_per_kilogram);
        et_per_cubic = (EditText) view.findViewById(R.id.et_per_cubic);

        title_bar = (RelativeLayout) view.findViewById(R.id.title_bar);
    }

    @Override
    protected void initEvent() {
//        image_head.setOnClickListener(this);
        btn_single_vehicle.setOnClickListener(this);
        btn_date.setOnClickListener(this);
        ll_container.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int y = 0;
                int endY = 0;
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        y = (int) event.getY();
                        System.out.println("y = " + y);
                        break;
                    case MotionEvent.ACTION_MOVE:
//                        endY = (int) event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        endY = (int) event.getY();
                        System.out.println("endY = " + endY);
                        System.out.println("y - endY = " + (y - endY));
                        if (y - endY > 100) {
                            showContent(ll_bottom_height, ll_container, 600);
                            flag = true;
                        } else if (y - endY < -100) {
                            hideContent(ll_bottom_height, ll_container, 600);
                            flag = false;
                        }
                        break;
                }

                return true;
            }
        });
        btn_car_offer.setOnClickListener(this);
        btn_zero_offer.setOnClickListener(this);
        btn_release_route.setOnClickListener(this);
        iv_release_route.setOnClickListener(this);
        ll_invoice.setOnClickListener(this);

        et_door_to_door.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    et_door_to_door.setHint("请输入报价");
                } else {
                    et_door_to_door.setHint("门到门");

                    if (!et_door_to_door.getText().toString().trim().equals("")) {
                        et_door_to_door.setBackgroundResource(R.drawable.btn_selected);
                        et_door_to_door.setTextColor(Color.parseColor("#439198"));
                    } else {
                        et_door_to_door.setBackgroundResource(R.drawable.btn_normal);
                        et_door_to_door.setTextColor(Color.parseColor("#666666"));
                    }
                }
            }
        });

        et_door_to_cfs.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    et_door_to_cfs.setHint("请输入报价");
                } else {
                    et_door_to_cfs.setHint("门到站（场）");

                    if (!et_door_to_cfs.getText().toString().trim().equals("")) {
                        et_door_to_cfs.setBackgroundResource(R.drawable.btn_selected);
                        et_door_to_cfs.setTextColor(Color.parseColor("#439198"));
                    } else {
                        et_door_to_cfs.setBackgroundResource(R.drawable.btn_normal);
                        et_door_to_cfs.setTextColor(Color.parseColor("#666666"));
                    }
                }
            }
        });

        iv_menu.setOnClickListener(this);
        iv_test.setOnClickListener(this);
    }

    @Override
    public void hideContent(int height, final ViewGroup vg, long duration) {
        ValueAnimator animator = ValueAnimator.ofInt(0, -height);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                vg.setPadding(vg.getPaddingLeft(), vg.getPaddingTop(), vg.getPaddingRight(), (Integer) valueAnimator.getAnimatedValue());
            }
        });
        animator.setDuration(duration);
        animator.start();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
        deactivate();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        if (null != mlocationClient) {
            mlocationClient.onDestroy();
        }
    }

    @Override
    public long getAccountId() {
        return (long) SPUtils.get(getActivity(), "id", 0L);
    }

    @Override
    public long getCarId() {
        return currentId;
    }

    @Override
    public long getToZoneCode() {
        return 0;
    }

    @Override
    public long getFromZoneCode() {
        return 0;
    }

    @Override
    public String getLaLoPosition() {
        return null;
    }

    @Override
    public String getFromAddress() {
        return null;
    }

    @Override
    public String getReleaseTime() {
        return time1;
    }

    @Override
    public String getArriveTime() {
        return time2;
    }

    @Override
    public String getType() {
        return "all";
    }

    @Override
    public double getPriceAll() {
        try {
            return Double.valueOf(et_offer.getText().toString().trim());
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public double getPriceKg() {
        try {
            return Double.valueOf(et_per_kilogram.getText().toString().trim());
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public double getPriceDun() {
        try {
            return Double.valueOf(et_per_ton.getText().toString().trim());
        } catch (Exception e) {
            return 0;
        }

    }

    @Override
    public double getPriceSquare() {
        try {
            return Double.valueOf(et_per_cubic.getText().toString().trim());
        } catch (Exception e) {
            return 0;
        }

    }

//    private String addService = "door_door,door_station,line_invoice";

    @Override
    public String getAddService() {
        String a = "";
        if (ll_invoice.getTag().equals("checked")) {
            a += "line_invoice";
        }

        if (getPrickDoor() != 0) {
            a += ",door_door";
        }

        if (getPrickStation() != 0) {
            a += ",door_station";
        }

        if (!a.equals("")) {
            String q = a.substring(0, 1);
            if (q.equals(",")) {
                return a.substring(1, a.length());
            }
        }

        return a;
    }

    @Override
    public double getPrickDoor() {
        try {
            return Double.valueOf(et_door_to_door.getText().toString().trim());
        } catch (Exception e) {
            return 0;
        }

    }

    @Override
    public double getPrickStation() {
        try {
            return Double.valueOf(et_door_to_cfs.getText().toString().trim());
        } catch (Exception e) {
            return 0;
        }
    }

    public void setUsefulCarBeanList(List<UsefulCarBean> usefulCarBeanList) {
        for (int i = 0; i < usefulCarBeanList.size(); i++) {

            UsefulCarBean usefulCarBean = usefulCarBeanList.get(i);
            if (i == 0) {
                currentId = usefulCarBean.getId();
                System.out.println("first id = " + currentId);
            }
            carNoList.add(usefulCarBean.getCardNum());
            carIdList.add(usefulCarBean.getId());
        }

        dismissProgressDialog();
    }

    @Override
    public void showProgressDialog() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("获取车辆列表中...");
        progressDialog.show();
    }

    @Override
    public void dismissProgressDialog() {
        progressDialog.dismiss();
    }

    @Override
    public LinearLayout getLl_container() {
        return ll_container;
    }

    @Override
    public int getLl_bottom_height() {
        return ll_bottom_height;
    }

    @Override
    public LinearLayout getLl_release_complete() {
        return ll_release_complete;
    }

}
