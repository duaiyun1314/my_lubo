package com.example.MyWeibo.weight.viewpagerIndicator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by Andy.Wang on 2015/11/2.
 */
public class TabStrip extends LinearLayout {
    private int mSelection;
    private float mPositionOffset;
    private Paint mPaint;

    public TabStrip(Context context) {
        super(context);
        init();
    }

    public TabStrip(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TabStrip(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void onPagerChanged(int selection, float positionOffset) {
        mSelection = selection;
        mPositionOffset = positionOffset;
        invalidate();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setColor(Color.GRAY);
        mPaint.setStyle(Paint.Style.FILL);

        setWillNotDraw(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        final int childCount = getChildCount();
        if (childCount > 0) {
            View view = getChildAt(mSelection);

            int left = view.getLeft();
            int right = view.getRight();
            if (mPositionOffset > 0) {
                final View nextView = getChildAt(mSelection + 1);

                left = (int) ((nextView.getLeft() - left) * mPositionOffset) + left;
                right = (int) ((nextView.getRight() - right) * mPositionOffset) + right;
            }
            RectF rectF = new RectF(left, getHeight() - 7, right, getHeight());
            canvas.drawRect(rectF, mPaint);
        }

    }

}
