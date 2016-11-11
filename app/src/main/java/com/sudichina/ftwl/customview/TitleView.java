package com.sudichina.ftwl.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.sudichina.ftwl.R;
import com.sudichina.ftwl.utils.CommonUtils;

/**
 * Created by mike on 2016/8/22.
 */
public class TitleView extends View {
    private final int EXTRA_HEIGHT = 10;

    private String mTitleText;
    private int mTitleColor = 0xff303030;
    private int mTitleSize = (int) CommonUtils.sp2px(getContext(), 14);
    private int mTitleMargin = (int) CommonUtils.dp2px(getContext(), 20);
    private int mLineColor = 0xff303030;
    private int mLineHeight = (int) CommonUtils.dp2px(getContext(), 4);

    private Paint mPaint;
    private Rect mTitleBounds;

    public TitleView(Context context) {
        this(context, null);
    }

    public TitleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TitleView);
        int count = ta.getIndexCount();
        for (int i = 0; i < count; i++) {
            int index = ta.getIndex(i);
            switch (index) {
                case R.styleable.TitleView_title_text:
                    mTitleText = ta.getString(index);
                    break;
                case R.styleable.TitleView_title_color:
                    mTitleColor = ta.getColor(index, mTitleColor);
                    break;
                case R.styleable.TitleView_title_margin:
                    mTitleMargin = ta.getDimensionPixelSize(index, mTitleMargin);
                    break;
                case R.styleable.TitleView_title_size:
                    mTitleSize = ta.getDimensionPixelSize(index, mTitleSize);
                    break;
                case R.styleable.TitleView_line_color:
                    mLineColor = ta.getColor(index, mLineColor);
                    break;
                case R.styleable.TitleView_line_height:
                    mLineHeight = ta.getDimensionPixelSize(index, mLineHeight);
                    break;
            }
        }
        ta.recycle();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(mLineColor);
        mPaint.setTextSize(mTitleSize);
        mPaint.setStrokeWidth(mLineHeight);

        mTitleBounds = new Rect();
        mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mTitleBounds);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int width = widthSize;
        int height;

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            int tempHeight = Math.max(mLineHeight, mTitleBounds.height());
            height = Math.min(tempHeight, heightSize);
            if (heightMode == MeasureSpec.UNSPECIFIED) {
                height = tempHeight;
            }
        }

        setMeasuredDimension(width, height + EXTRA_HEIGHT);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        int halfWidth = getMeasuredWidth() / 2;
        int halfHeight = getMeasuredHeight() / 2;

        canvas.drawLine(0, halfHeight, halfWidth - mTitleMargin - mTitleBounds.width() / 2, halfHeight, mPaint);
        canvas.drawLine(halfWidth + mTitleBounds.width() / 2 + mTitleMargin, halfHeight, getMeasuredWidth(), halfHeight, mPaint);
        mPaint.setColor(mTitleColor);
        canvas.drawText(mTitleText, getMeasuredWidth() / 2 - mTitleBounds.width() / 2, getMeasuredHeight() / 2 + mTitleBounds.height() / 2, mPaint);
    }
}
