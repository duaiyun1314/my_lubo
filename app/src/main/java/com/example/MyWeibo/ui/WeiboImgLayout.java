package com.example.MyWeibo.ui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.MyWeibo.R;
import com.example.MyWeibo.network.LuBoCache;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

/**
 * Created by wanglu on 15/4/23.
 */
public class WeiboImgLayout extends RelativeLayout {
    private SimpleDraweeView img0, img1, img2, img3, img4, img5, img6, img7, img8;
    private RelativeLayout status_multi;
    private ImageView status_single;
    private ArrayList<SimpleDraweeView> views;
    private ArrayList<String> urls;
    private Context mContext;
    private RequestQueue mQueue;
    private ImageLoader mLoader;
    private float mBitmapMaxHeight;
    private float mBitmapMaxWidth;

    /*dimen*/
    private int mDimen;
    private int mPadding;

    public WeiboImgLayout(Context context) {
        super(context);
        initData(context);
    }

    public WeiboImgLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData(context);
    }

    public WeiboImgLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context);
    }

    public WeiboImgLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initData(context);
    }

    private void initData(Context context) {
        /*init data*/
        Resources res = context.getResources();
        mBitmapMaxHeight = res.getDimension(R.dimen.bitmap_max_height);
        mBitmapMaxWidth = res.getDimension(R.dimen.bitmap_max_width);
        mDimen = (int) context.getResources().getDimension(R.dimen.weibo_image_dimen);
        mPadding = (int) context.getResources().getDimension(R.dimen.weibo_image_padding);

        mQueue = Volley.newRequestQueue(context);
        mLoader = new ImageLoader(mQueue, LuBoCache.getInstance());
        // status_single = (SimpleDraweeView) View.inflate(context, R.layout.simpledraweeview, null);
        status_single = (ImageView) View.inflate(context, R.layout.singleimageview, null);
        status_multi = (RelativeLayout) View.inflate(context, R.layout.weibo_image, null);
        img0 = (SimpleDraweeView) status_multi.findViewById(R.id.weibo_img_0);
        img1 = (SimpleDraweeView) status_multi.findViewById(R.id.weibo_img_1);
        img2 = (SimpleDraweeView) status_multi.findViewById(R.id.weibo_img_2);
        img3 = (SimpleDraweeView) status_multi.findViewById(R.id.weibo_img_3);
        img4 = (SimpleDraweeView) status_multi.findViewById(R.id.weibo_img_4);
        img5 = (SimpleDraweeView) status_multi.findViewById(R.id.weibo_img_5);
        img6 = (SimpleDraweeView) status_multi.findViewById(R.id.weibo_img_6);
        img7 = (SimpleDraweeView) status_multi.findViewById(R.id.weibo_img_7);
        img8 = (SimpleDraweeView) status_multi.findViewById(R.id.weibo_img_8);
        views = new ArrayList<>();
        views.add(img0);
        views.add(img1);
        views.add(img2);
        views.add(img3);
        views.add(img4);
        views.add(img5);
        views.add(img6);
        views.add(img7);
        views.add(img8);
        RelativeLayout.LayoutParams layoutParams = new LayoutParams(mDimen * 3 + 2 * mPadding, mDimen * 3 + 2 * mPadding);
        RelativeLayout.LayoutParams layoutParams1 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        addView(status_multi, layoutParams);
        addView(status_single);


    }

    public void setUrls(ArrayList<String> urls) {
        this.urls = urls;
        if (urls != null) {
            if (urls.size() == 1) {
                status_multi.setVisibility(View.GONE);
                status_single.setVisibility(View.VISIBLE);
                ImageLoader.ImageListener listener = getImageListener(status_single, R.drawable.ic_launcher, R.drawable.ic_launcher);
                mLoader.get(urls.get(0), listener);

            } else {
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) status_multi.getLayoutParams();
                layoutParams.height = getDimenHeight(urls.size());
                status_multi.setLayoutParams(layoutParams);
                status_multi.setVisibility(View.VISIBLE);
                status_single.setVisibility(View.GONE);
                for (int i = 0; i < 9; i++) {
                    String url = i < urls.size() ? urls.get(i) : null;
                    if (url != null) {
                        views.get(i).setVisibility(View.VISIBLE);
                        views.get(i).setImageURI(Uri.parse(url));
                    } else {
                        views.get(i).setVisibility(View.GONE);
                    }
                }
            }
        } else {
            status_multi.setVisibility(View.GONE);
            status_single.setVisibility(View.GONE);
        }
    }

    private int getDimenHeight(int size) {
        int rows = size > 9 ? 3 : (size % 3 > 0 ? size / 3 + 1 : size / 3);
        return rows * mDimen + mPadding * (rows - 1);
    }

    public ImageLoader.ImageListener getImageListener(final ImageView view,
                                                      final int defaultImageResId, final int errorImageResId) {
        return new ImageLoader.ImageListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (errorImageResId != 0) {
                    view.setImageResource(errorImageResId);
                }
            }

            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                if (response.getBitmap() != null) {
                    //Bitmap bitmap = getScaleBitmap(response.getBitmap());
                    //view.setImageBitmap(bitmap);
                    view.setImageBitmap(response.getBitmap());
                } else if (defaultImageResId != 0) {
                    view.setImageResource(defaultImageResId);
                }
            }
        };


    }

    private Bitmap getScaleBitmap(Bitmap bitmap) {
        Bitmap resizeBitmap;
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
        if (height > mBitmapMaxHeight || width > mBitmapMaxWidth) {
            float scaleX = width > mBitmapMaxWidth ? mBitmapMaxWidth / width : 1.0f;
            float scaleY = height > mBitmapMaxHeight ? mBitmapMaxHeight / height : 1.0f;
            Matrix matrix = new Matrix();
            matrix.setScale(scaleX, scaleY);
            Log.i("lulu", width + ":" + height);
            resizeBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);
            return resizeBitmap;
        } else {
            return bitmap;
        }
    }

}
