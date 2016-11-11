package com.sudichina.ftwl.customview;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by mike on 2016/8/8.
 */
public class MyItemDecoration extends RecyclerView.ItemDecoration {

    @Override
    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
        super.getItemOffsets(outRect, itemPosition, parent);
        switch (itemPosition) {
            case 0:
                outRect.set(0,16,25,14);
                break;
            case 1:
                outRect.set(25,16,0,14);
                break;
            case 2:
                outRect.set(0,14,25,16);
                break;
            case 3:
                outRect.set(25,14,0,16);
                break;
        }
    }
}
