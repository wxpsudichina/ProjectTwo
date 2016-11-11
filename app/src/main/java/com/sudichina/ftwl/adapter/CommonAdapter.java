package com.sudichina.ftwl.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by mike on 2016/8/8.
 */
public abstract class CommonAdapter<T> extends RecyclerView.Adapter<ViewHolder> {

    public interface OnItemTouchListener {
        void onItemTouch(View v, MotionEvent event, int position);
    }

    private OnItemTouchListener listener;

    public void setOnItemTouchListener(OnItemTouchListener listener) {
        this.listener = listener;
    }

    private Context mContext;
    private List<T> mData;
    private int mLayoutId;

    public CommonAdapter(Context context, List<T> data, int layoutId) {
        mContext = context;
        mData = data;
        mLayoutId = layoutId;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ViewHolder.get(mContext, mLayoutId, parent);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (listener != null) {
            holder.itemView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    listener.onItemTouch(v, event, position);
                    return false;
                }
            });

        }

        convert(holder, mData.get(position),position);
    }

    public abstract void convert(ViewHolder viewHolder, T t,int position);

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
