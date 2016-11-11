package com.sudichina.ftwl.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sudichina.ftwl.R;
import com.sudichina.ftwl.bean.CityBean;
import com.sudichina.ftwl.view.BaseActivity;
import com.sudichina.ftwl.view.SortCityActivity;

import java.util.List;

/**
 * Created by SudiChina-105 on 2016/9/5.
 * 展示城市列表，所有城市，
 */
public class MyAllCityAdapter extends BaseAdapter {
    SortCityActivity sortCityActivity;
    List<CityBean> list_citys;
    LayoutInflater mInflater;

    public MyAllCityAdapter(SortCityActivity sortCityActivity, List<CityBean> list_citys) {
        this.sortCityActivity = sortCityActivity;
        this.list_citys = list_citys;
    }
    @Override
    public int getCount() {
        return list_citys.size();
    }
    @Override
    public Object getItem(int position) {
        return list_citys.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView=mInflater.inflate(R.layout.sortcity_lv_item,null);
            viewHolder.tv_city= (TextView) convertView.findViewById(R.id.sort_city_tv);
            convertView.setTag(viewHolder);
        }
        viewHolder= (ViewHolder) convertView.getTag();
        viewHolder.tv_city.setText(list_citys.get(position).getName());
        return convertView;
    }

    static class ViewHolder {
        TextView tv_city;
    }
}
