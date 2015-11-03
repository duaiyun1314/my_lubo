package com.example.MyWeibo.weight.viewpagerIndicator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
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

    public void onPagerChanged(int selection, float offset) {
        mSelection = selection;
        mPositionOffset = offset;
        invalidate();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);

        setWillNotDraw(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF rectF = new RectF(getInitPosisition(mSelection) + mPositionOffset, getHeight() - 5, getInitPosisition(mSelection + 1) + mPositionOffset, getHeight());
        //RectF rectF = new RectF(0, getHeight()-10, 100, getHeight());
        canvas.drawRect(rectF, mPaint);
    }

    private int getInitPosisition(int selection) {
        int width = 0;
        if (getChildCount() > 0) {
            for (int i = 0; i < selection; i++) {
                if (selection >= getChildCount()) {
                    width = getWidth();
                    return width;
                }
                width += getChildAt(i).getWidth();
            }
        }
        return width;
    }
}
