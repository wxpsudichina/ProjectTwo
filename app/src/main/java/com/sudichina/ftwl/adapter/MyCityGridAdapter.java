package com.sudichina.ftwl.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sudichina.ftwl.R;
import com.sudichina.ftwl.bean.HotCity;
import com.sudichina.ftwl.view.BaseActivity;
import com.sudichina.ftwl.view.SortCityActivity;

import java.util.List;

/**
 * Created by SudiChina-105 on 2016/9/5.
 * 展示热门城市，数据
 */
public class MyCityGridAdapter  extends BaseAdapter{
    SortCityActivity sortCityActivity;
    List<HotCity> list_hot;
    LayoutInflater mInflater;
    public MyCityGridAdapter(SortCityActivity sortCityActivity, List<HotCity> list_hot) {
        this.sortCityActivity = sortCityActivity;
        this.list_hot = list_hot;
    }
    @Override
    public int getCount() {
        return list_hot.size();
    }
    @Override
    public Object getItem(int position) {
        return list_hot.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView=mInflater.inflate(R.layout.sortcity_hotcity_item,null);
            viewHolder.tv_hot= (TextView) convertView.findViewById(R.id.sort_city_hotcity_item_tv);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
            viewHolder.tv_hot.setText(list_hot.get(position).getName());
        }
        return convertView;
    }
    class ViewHolder{
        TextView tv_hot;
    }
}
