package com.sudichina.ftwl.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by mike on 2016/8/8.
 */
public class ViewHolder extends RecyclerView.ViewHolder {
    private View mConvertView;
    private SparseArray<View> mViews;

    public ViewHolder(View itemView) {
        super(itemView);

        mConvertView = itemView;
        mViews = new SparseArray<>();
    }

    public static ViewHolder get(Context context, int layoutId, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(layoutId,parent,false);
        return new ViewHolder(view);
    }

    public <T extends View> T getView(int id) {
        View view = mViews.get(id);
        if (view == null) {
            view = mConvertView.findViewById(id);
            mViews.put(id,view);
        }

        return (T) view;
    }
}
