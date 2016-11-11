package com.sudichina.ftwl.customview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sudichina.ftwl.bean.ItemBean;

import java.util.List;
import java.util.Map;

/**
 * Created by mike on 2016/8/18.
 */
public class ItemView extends LinearLayout {

    public ItemView(Context context) {
        this(context, null);
    }

    public ItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(LinearLayout.VERTICAL);
    }

    public void setItems(List<ItemBean> items) {

        int i = 0;
        for (ItemBean bean : items) {
            String key = bean.getText();
            Integer value = bean.getImg();

            addDivider();

            TextView tv = new TextView(getContext());
            LinearLayout.LayoutParams lp2 = (LayoutParams) tv.getLayoutParams();
            if (lp2 == null) {
                lp2 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            }
            tv.setLayoutParams(lp2);
            tv.setText(key);
            tv.setGravity(Gravity.CENTER_VERTICAL);
            tv.setPadding(90, 0, 90, 0);
            tv.setTextSize(15);
            tv.setTextColor(0xff333333);
            Drawable d = getResources().getDrawable(value);
            d.setBounds(0, 0, d.getMinimumWidth(), d.getMinimumHeight());
            tv.setCompoundDrawables(null, null, d, null);
            addView(tv);

            if (i == items.size() - 1) {
                addDivider();
            }

            i++;
        }

    }

    private void addDivider() {
        TextView tv2 = new TextView(getContext());
        LayoutParams lp = (LayoutParams) tv2.getLayoutParams();
        if (lp == null) {
            lp = new LayoutParams(LayoutParams.MATCH_PARENT, 1);
        }
        tv2.setLayoutParams(lp);
        tv2.setBackgroundColor(0xffbdbdbd);
        addView(tv2);
    }
}
