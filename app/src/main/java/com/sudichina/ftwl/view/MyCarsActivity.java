package com.sudichina.ftwl.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sudichina.ftwl.R;
import com.sudichina.ftwl.adapter.CommonAdapter;
import com.sudichina.ftwl.adapter.ViewHolder;
import com.sudichina.ftwl.bean.CarBean;
import com.sudichina.ftwl.presenter.MyCarsPresenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyCarsActivity extends BaseActivity implements IMyCarsView, View.OnClickListener {
    private RecyclerView recyclerView;
    private CommonAdapter mAdapter;
    private List<CarBean> carBeanList;

    private ProgressDialog mProgressDialog;
    private ImageView iv_add_vehicle;
    private TextView tv_edit;
    private ImageView iv_go_back;

    private MyCarsPresenter myCarsPresenter = new MyCarsPresenter(this);

    private long deletedId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cars);

        initData();
        initView();
        initEvent();

    }

    @Override
    protected void initData() {
        myCarsPresenter.getCarList();
    }

    @Override
    protected void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        iv_add_vehicle = (ImageView) findViewById(R.id.iv_add_vehicle);
        tv_edit = (TextView) findViewById(R.id.tv_edit);
        iv_go_back = (ImageView) findViewById(R.id.iv_go_back);
    }

    @Override
    protected void initEvent() {
        iv_add_vehicle.setOnClickListener(this);
        tv_edit.setOnClickListener(this);
        iv_go_back.setOnClickListener(this);
    }

    @Override
    public void showProgressDialog() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("正在加载。。。");
        mProgressDialog.show();
    }

    @Override
    public void dismissProgressDialog() {
        mProgressDialog.dismiss();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void setCarBeanList(List<CarBean> carBeanList) {
        this.carBeanList = carBeanList;
        System.out.println("carBeanList.size = " + carBeanList.size());

        mAdapter = new CommonAdapter<CarBean>(this, carBeanList, R.layout.item_my_cars) {

            @Override
            public void convert(ViewHolder viewHolder, final CarBean carBean, int position) {

                if (viewHolder.itemView.getScrollX() == 288) {
                    viewHolder.itemView.setScrollX(0);
                }

                for (Map.Entry<Integer, Integer> entry : data.entrySet()) {
                    if (position == entry.getKey()) {
                        viewHolder.itemView.setScrollX(entry.getValue());
                    }
                }

                TextView tv_car_first_letter = viewHolder.getView(R.id.tv_car_first_letter);
                tv_car_first_letter.setText(carBean.getCarFirstLetter());

                TextView tv_plate_no = viewHolder.getView(R.id.tv_plate_no);
                tv_plate_no.setText(carBean.getCardNumber());

                TextView tv_state = viewHolder.getView(R.id.tv_state);
                final Button btn_modify = viewHolder.getView(R.id.btn_modify);
                final Button btn_del = viewHolder.getView(R.id.btn_del);
                switch (carBean.getAuditStatus()) {
                    case 0:
                        tv_state.setText("审核中");
                        btn_del.setText("审核中");
                        btn_del.setOnClickListener(null);
                        btn_modify.setVisibility(View.INVISIBLE);
                        btn_modify.setOnClickListener(null);
                        break;
                    case 1:
                        tv_state.setText("已通过");
                        btn_del.setText("已通过");
                        btn_del.setOnClickListener(null);
                        btn_modify.setVisibility(View.INVISIBLE);
                        btn_modify.setOnClickListener(null);
                        break;
                    case 2:
                        btn_modify.setVisibility(View.VISIBLE);
                        tv_state.setText("未通过");
                        btn_del.setText("删除");
                        btn_del.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                toast("删除");
                                myCarsPresenter.deleteVehicle(carBean.getId());
                                deletedId = carBean.getId();
                            }
                        });

                        btn_modify.setText("修改");
                        btn_modify.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                toast("修改");
                                System.out.println("id === " + carBean.getId());
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("carBean", carBean);
                                toAty(VehicleCertificationActivity.class, bundle);
                            }
                        });
                        break;
                }

            }
        };

        mAdapter.setOnItemTouchListener(new CommonAdapter.OnItemTouchListener() {
            @Override
            public void onItemTouch(View v, MotionEvent event, int position) {
                toast("aaa");
                data.put(position, v.getScrollX());
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
        recyclerView.setAdapter(mAdapter);

//        recyclerView.
    }

    private Map<Integer, Integer> data = new HashMap<>();

    @Override
    public void showToast(String msg) {
        toast(msg);
    }

    @Override
    public void refreshData() {
        int pos = 0;
        for (int i = 0; i < carBeanList.size(); i++) {
            CarBean carBean = carBeanList.get(i);
            if (carBean.getId() == deletedId) {
                carBeanList.remove(i);
                pos = i;
            }
        }

        mAdapter.notifyItemRemoved(pos);
    }

    private boolean flag;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_add_vehicle:
                toAty(VehicleCertificationActivity.class);
                break;
            case R.id.tv_edit:
                toast("sss");
                if (!flag) {
                    int count = recyclerView.getChildCount();
                    int first = recyclerView.getChildLayoutPosition(recyclerView.getChildAt(0));
                    System.out.println("first = " + first);
                    for (int i = 0; i < count; i++) {
                        View view = recyclerView.getChildAt(i);
                        view.setScrollX(288);
                        data.put(first + i, 288);
                    }
                    tv_edit.setText("取消");
                    flag = !flag;
                } else {
                    int count = recyclerView.getChildCount();
                    int first = recyclerView.getChildLayoutPosition(recyclerView.getChildAt(0));
                    System.out.println("first = " + first);
                    for (int i = 0; i < count; i++) {
                        View view = recyclerView.getChildAt(i);
                        view.setScrollX(0);
                        data.put(first + i, 0);
                    }
                    tv_edit.setText("编辑");
                    flag = !flag;
                }

                break;
            case R.id.iv_go_back:
                finish();
                break;
        }
    }
}
