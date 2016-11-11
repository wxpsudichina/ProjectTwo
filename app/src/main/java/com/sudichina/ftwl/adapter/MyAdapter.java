package com.sudichina.ftwl.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sudichina.ftwl.R;
import com.sudichina.ftwl.bean.CarBean;

import java.util.List;

/**
 * Created by mccccccmike on 2016/9/5.
 */
public class MyAdapter extends BaseAdapter {
    private List<CarBean> data;
    private Context context;

    public MyAdapter(Context context, List<CarBean> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_my_cars,null);
        }

        CarBean carBean = data.get(position);

        TextView tv_car_first_letter = (TextView) convertView.findViewById(R.id.tv_car_first_letter);
//        TextView tv_plate_no = convertView.findViewById(R.id.tv_plate_no);
        TextView tv_state = (TextView) convertView.findViewById(R.id.tv_state);
//        tv.setText(data.get(position).getCardNumber());
//        switch (carBean.getAuditStatus()) {
//                    case 0:
//                        tv_state.setText("审核中");
//                        btn_ope.setText("请耐心等待审核");
//                        btn_ope.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                toast("请耐心等待审核");
//                            }
//                        });
//                        break;
//                    case 1:
//                        tv_state.setText("已通过");
//                        btn_ope.setText("修改");
//                        btn_ope.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                toast("修改");
//                                System.out.println("id === " + carBean.getId());
//                                Bundle bundle = new Bundle();
//                                bundle.putSerializable("carBean", carBean);
//                                toAty(VehicleCertificationActivity.class, bundle);
//                            }
//                        });
//                        break;
//                    case 2:
//                        tv_state.setText("未通过");
//                        btn_ope.setText("删除");
//                        btn_ope.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                toast("删除");
//                                myCarsPresenter.deleteVehicle(carBean.getId());
//                                deletedId = carBean.getId();
//                            }
//                        });
//                        break;
//                }
        tv_state.setText(data.get(position).getAuditStatus()+"");

        return convertView;
    }

}
