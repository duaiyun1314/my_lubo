/*
    Copyright (C) 2013 Make Ramen, LLC
*/

package com.example.MyWeibo.ui;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.example.MyWeibo.R;

public class BorderedRoundedCornersImageView extends ImageView {

    public static final String TAG = "BorderedRoundedCornersImageView";
    public int mWidth;
    private int mHeight;

    public static final int DEFAULT_RADIUS = 0;
    public static final int DEFAULT_BORDER = 0;
    public static final int DEFAULT_BORDER_COLOR = Color.BLACK;

    private int mCornerRadius;
    private int mBorderWidth;
    private int mBorderColor;

    private Paint mBadgeTextPaint;
    private float DEFAULT_TEXT_SIZE_RATIO = 0.618f;

    private boolean mCustomizedXmlParamsLoaded;
    private boolean mRoundBackground;
    private int mBackgroundColor;
    private boolean mRoundImage;

    private Drawable mImageDrawable;
    private Bitmap mImageBitmap;
    private Drawable mBackgroundDrawable;

    private ScaleType mScaleType;

    private static final ScaleType[] sScaleTypeArray = {
            ScaleType.MATRIX,
            ScaleType.FIT_XY,
            ScaleType.FIT_START,
            ScaleType.FIT_CENTER,
            ScaleType.FIT_END,
            ScaleType.CENTER,
            ScaleType.CENTER_CROP,
            ScaleType.CENTER_INSIDE
    };

    public BorderedRoundedCornersImageView(Context context) {
        super(context);
        mCornerRadius = DEFAULT_RADIUS;
        mBorderWidth = DEFAULT_BORDER;
        mBorderColor = DEFAULT_BORDER_COLOR;

        mCustomizedXmlParamsLoaded = true;
    }

    public BorderedRoundedCornersImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BorderedRoundedCornersImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mWidth = (int) getResources().getDimension(R.dimen.weibo_user_img);
        mHeight = mWidth;

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BorderedRoundedCornersImageView,
                defStyle, 0);

        mCornerRadius = a
                .getDimensionPixelSize(R.styleable.BorderedRoundedCornersImageView_corner_radius, -1);
        mBorderWidth = a.getDimensionPixelSize(R.styleable.BorderedRoundedCornersImageView_border_width, -1);
        mBackgroundColor = a.getColor(R.styleable.BorderedRoundedCornersImageView_background_color, Color.GRAY);

        // don't allow negative values for radius and border
        if (mCornerRadius < 0) {
            mCornerRadius = DEFAULT_RADIUS;
        }
        if (mBorderWidth < 0) {
            mBorderWidth = DEFAULT_BORDER;
        }

        mBorderColor = a.getColor(R.styleable.BorderedRoundedCornersImageView_border_color,
                DEFAULT_BORDER_COLOR);

        mRoundBackground = a.getBoolean(R.styleable.BorderedRoundedCornersImageView_round_background, false);
        mRoundImage = a.getBoolean(R.styleable.BorderedRoundedCornersImageView_round_image, true);

        a.recycle();

        mCustomizedXmlParamsLoaded = true;

        if (mImageDrawable != null) {
            this.setImageDrawable(mImageDrawable);
        } else if (mImageBitmap != null) {
            this.setImageBitmap(mImageBitmap);
        }

        if (mBackgroundDrawable != null) {
            this.setBackground(mBackgroundDrawable);
        }

        if (mScaleType != null) {
            this.setScaleType(mScaleType);
        }
    }

    /**
     * Controls how the image should be resized or moved to match the size of
     * this ImageView.
     *
     * @param scaleType The desired scaling mode. s
     * @attr ref android.R.styleable#ImageView_scaleType
     */
    @Override
    public void setScaleType(ScaleType scaleType) {
        if (!mCustomizedXmlParamsLoaded) {
            mScaleType = scaleType;
            return;
        }

        if (scaleType == null) {
            throw new NullPointerException();
        }

        mScaleType = scaleType;

        switch (scaleType) {
            case CENTER:
            case CENTER_CROP:
            case CENTER_INSIDE:
            case FIT_CENTER:
            case FIT_START:
            case FIT_END:
            case FIT_XY:
                if (mCustomizedXmlParamsLoaded && mRoundImage) {
                    super.setScaleType(ScaleType.FIT_XY);
                } else {
                    super.setScaleType(scaleType);
                }
                break;
            default:
                super.setScaleType(scaleType);
                break;
        }

        if (mImageDrawable instanceof BorderedRoundedCornersBitmapDrawable
                && ((BorderedRoundedCornersBitmapDrawable) mImageDrawable).getScaleType() != scaleType) {
            ((BorderedRoundedCornersBitmapDrawable) mImageDrawable).setScaleType(scaleType);
        }

        if (mBackgroundDrawable instanceof BorderedRoundedCornersBitmapDrawable
                && ((BorderedRoundedCornersBitmapDrawable) mBackgroundDrawable).getScaleType() != scaleType) {
            ((BorderedRoundedCornersBitmapDrawable) mBackgroundDrawable).setScaleType(scaleType);
        }
        setWillNotCacheDrawing(true);
        requestLayout();
        invalidate();
    }

    /**
     * Return the current scale type in use by this ImageView.
     *
     * @attr ref android.R.styleable#ImageView_scaleType
     * @see ScaleType
     */
    @Override
    public ScaleType getScaleType() {
        return mScaleType;
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        if (!mCustomizedXmlParamsLoaded) {
            mImageDrawable = drawable;
            mImageBitmap = null;
            return;
        }

        if (mRoundImage && drawable != null) {
            mImageDrawable = BorderedRoundedCornersBitmapDrawable.fromDrawable(drawable,
                    mCornerRadius, mBorderWidth, mBorderColor, mScaleType);
        } else {
            mImageDrawable = drawable;
        }
        super.setImageDrawable(mImageDrawable);
    }

    @Override
    public void setImageResource(int resId) {
        setImageDrawable(getContext().getResources().getDrawable(resId));
    }

    public void setImageBitmap(String mBadgeText) {
        setImageBitmap(mBadgeText, 0);
    }

    public void setImageBitmap(String mBadgeText, int color) {
        if (TextUtils.isEmpty(mBadgeText)) {
            return;
        }
        if (color != 0) {
            mBackgroundColor = color;
        }

        Rect rect = new Rect();
        rect.set(0, 0, mWidth, mHeight);

        // Set Paint which is used to draw contact text
        if (mBadgeTextPaint == null) {
            mBadgeTextPaint = new Paint();
            mBadgeTextPaint.setAntiAlias(true);
            mBadgeTextPaint.setTextAlign(Paint.Align.CENTER);

            mBadgeTextPaint.setColor(Color.WHITE);
            mBadgeTextPaint.setTextSize((int) (mWidth * DEFAULT_TEXT_SIZE_RATIO));
        }

        // Draw contact text
        float baseX = (rect.left + rect.right) / 2;
        float baseY = (rect.top + rect.bottom) / 2;
        Paint.FontMetrics fontMetrics = mBadgeTextPaint.getFontMetrics();
        float fontTotalHeight = fontMetrics.bottom - fontMetrics.top;
        float offY = fontTotalHeight / 2 - fontMetrics.bottom;
        float newY = baseY + offY - 2;

        Bitmap customBitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        Canvas mCanvas = new Canvas(customBitmap);
        mCanvas.drawColor(mBackgroundColor);
        mCanvas.drawText(mBadgeText, baseX, newY, mBadgeTextPaint);
        setImageBitmap(customBitmap);

    }

    public void setImageBitmap(Bitmap bm) {
        if (!mCustomizedXmlParamsLoaded) {
            mImageBitmap = bm;
            mImageDrawable = null;
            return;
        }

        if (mRoundImage && bm != null) {
            mImageDrawable = new BorderedRoundedCornersBitmapDrawable(bm, mCornerRadius,
                    mBorderWidth, mBorderColor, mScaleType);
        } else {
            mImageDrawable = null;
        }
        super.setImageDrawable(mImageDrawable);
    }

    @Override
    public void setBackground(Drawable background) {
        setBackgroundDrawable(background);
    }

    @Override
    @Deprecated
    public void setBackgroundDrawable(Drawable background) {
        if (!mCustomizedXmlParamsLoaded) {
            mBackgroundDrawable = background;
            return;
        }

        if (mRoundBackground && background != null) {
            if (background instanceof ColorDrawable) {
                int width = getWidth();
                int height = getHeight();

                if (width < getMeasuredWidth()) {
                    width = getMeasuredWidth();
                }

                if (height < getMeasuredHeight()) {
                    height = getMeasuredHeight();
                }

                if (getLayoutParams() != null) {
                    if (width < getLayoutParams().width) {
                        width = getLayoutParams().width;
                    }

                    if (height < getLayoutParams().height) {
                        height = getLayoutParams().height;
                    }
                }

                BorderedRoundedCornersBitmapDrawable drawable =
                        new BorderedRoundedCornersBitmapDrawable((ColorDrawable) background,
                                mCornerRadius, mBorderWidth, mBorderColor,
                                width, height, mScaleType);
                mBackgroundDrawable = drawable;
            } else {
                mBackgroundDrawable = BorderedRoundedCornersBitmapDrawable.fromDrawable(background,
                        mCornerRadius, mBorderWidth, mBorderColor, mScaleType);
            }
        } else {
            mBackgroundDrawable = background;
        }
        super.setBackgroundDrawable(mBackgroundDrawable);
    }

    @Override
    public void setBackgroundColor(int color) {
        setBackground(new ColorDrawable(color));
    }

    @Override
    public void setBackgroundResource(int resid) {
        Drawable d = null;
        if (resid != 0) {
            d = getContext().getResources().getDrawable(resid);
        }
        setBackground(d);
    }

    public int getCornerRadius() {
        return mCornerRadius;
    }

    public int getBorder() {
        return mBorderWidth;
    }

    public int getBorderColor() {
        return mBorderColor;
    }

    public void setCornerRadius(int radius) {
        if (mCornerRadius == radius) {
            return;
        }

        this.mCornerRadius = radius;
        if (mImageDrawable instanceof BorderedRoundedCornersBitmapDrawable) {
            ((BorderedRoundedCornersBitmapDrawable) mImageDrawable).setCornerRadius(radius);
        }
        if (mRoundBackground && mBackgroundDrawable instanceof BorderedRoundedCornersBitmapDrawable) {
            ((BorderedRoundedCornersBitmapDrawable) mBackgroundDrawable).setCornerRadius(radius);
        }
    }

    public void setBorderWidth(int width) {
        if (mBorderWidth == width) {
            return;
        }

        this.mBorderWidth = width;
        if (mImageDrawable instanceof BorderedRoundedCornersBitmapDrawable) {
            ((BorderedRoundedCornersBitmapDrawable) mImageDrawable).setBorderWidth(width);
        }
        if (mRoundBackground && mBackgroundDrawable instanceof BorderedRoundedCornersBitmapDrawable) {
            ((BorderedRoundedCornersBitmapDrawable) mBackgroundDrawable).setBorderWidth(width);
        }
        invalidate();
    }

    public void setBorderColor(int color) {
        if (mBorderColor == color) {
            return;
        }

        this.mBorderColor = color;
        if (mImageDrawable instanceof BorderedRoundedCornersBitmapDrawable) {
            ((BorderedRoundedCornersBitmapDrawable) mImageDrawable).setBorderColor(color);
        }
        if (mRoundBackground && mBackgroundDrawable instanceof BorderedRoundedCornersBitmapDrawable) {
            ((BorderedRoundedCornersBitmapDrawable) mBackgroundDrawable).setBorderColor(color);
        }
        if (mBorderWidth > 0) {
            invalidate();
        }
    }

    public boolean isRoundBackground() {
        return mRoundBackground;
    }

    public void setRoundBackground(boolean roundBackground) {
        if (this.mRoundBackground == roundBackground) {
            return;
        }

        this.mRoundBackground = roundBackground;
        if (roundBackground) {
            if (mBackgroundDrawable instanceof BorderedRoundedCornersBitmapDrawable) {
                ((BorderedRoundedCornersBitmapDrawable) mBackgroundDrawable)
                        .setScaleType(mScaleType);
                ((BorderedRoundedCornersBitmapDrawable) mBackgroundDrawable)
                        .setCornerRadius(mCornerRadius);
                ((BorderedRoundedCornersBitmapDrawable) mBackgroundDrawable)
                        .setBorderWidth(mBorderWidth);
                ((BorderedRoundedCornersBitmapDrawable) mBackgroundDrawable)
                        .setBorderColor(mBorderColor);
            } else {
                setBackgroundDrawable(mBackgroundDrawable);
            }
        } else if (mBackgroundDrawable instanceof BorderedRoundedCornersBitmapDrawable) {
            ((BorderedRoundedCornersBitmapDrawable) mBackgroundDrawable).setBorderWidth(0);
            ((BorderedRoundedCornersBitmapDrawable) mBackgroundDrawable).setCornerRadius(0);
        }

        invalidate();
    }

    public void setCornerRadii(int topLeftRadius, int topRightRadius, int bottomLeftRadius, int bottomRightRadius) {
        if (mImageDrawable instanceof BorderedRoundedCornersBitmapDrawable) {
            ((BorderedRoundedCornersBitmapDrawable) mImageDrawable).setCornerRadii(topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius);
        }
        if (mRoundBackground && mBackgroundDrawable instanceof BorderedRoundedCornersBitmapDrawable) {
            ((BorderedRoundedCornersBitmapDrawable) mBackgroundDrawable).setCornerRadii(topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }
}
