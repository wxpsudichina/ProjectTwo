package com.sudichina.ftwl.customview;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by mccccccmike on 2016/10/12.
 */

public class ScalePageTransformer implements ViewPager.PageTransformer {
    private static final float DEFAULT_SCALE = .9f;
    private float scale = DEFAULT_SCALE;

    public ScalePageTransformer() {

    }

    public ScalePageTransformer(float scale) {
        this.scale = scale;
    }

    @Override
    public void transformPage(View page, float position) {
        if (position < -1) {
            page.setPivotX(page.getMeasuredWidth());
            page.setPivotY(page.getMeasuredHeight() / 2);

            page.setScaleX(scale);
            page.setScaleY(scale);
        } else if (position <= 1) {
            if (position < 0) {
                page.setPivotX(page.getMeasuredWidth());
                page.setPivotY(page.getMeasuredHeight() / 2);

                //result 1 - 0.8f
                //position 0 - -1
                //1 + position 1 - 0
                //(1 - 0.8f) * (1 + position) 0.2f - 0
                //(1 - 08f) * (1 + position) + 0.8f;1- 0.8f

                page.setScaleX((1 - scale) * (1 + position) + scale);
                page.setScaleY((1 - scale) * (1 + position) + scale);
            } else {
                page.setPivotX(0);
                page.setPivotY(page.getMeasuredHeight() / 2);

                //result 0.8f - 1
                //position 1 - 0
                //1 - position 0 - 1
                //(1 - 0.8f) * (1 - position) 0 - 0.2f
                //(1 - 0.8f) * (1 - position) + 0.8f 0.8f - 1

                page.setScaleX((1 - scale) * (1 - position) + scale);
                page.setScaleY((1 - scale) * (1 - position) + scale);
            }
        } else {
            page.setPivotX(0);
            page.setPivotY(page.getMeasuredHeight() / 2);

            page.setScaleX(scale);
            page.setScaleY(scale);
        }
    }
}
