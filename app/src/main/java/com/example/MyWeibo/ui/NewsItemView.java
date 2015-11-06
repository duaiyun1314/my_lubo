package com.example.MyWeibo.ui;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.MyWeibo.R;
import com.example.MyWeibo.model.NewsItem;
import com.example.MyWeibo.utils.imageload.ImageLoaderUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by wanglu on 15/9/11.
 */
public class NewsItemView extends CardView {
    private TextView news_time;
    private TextView news_title;
    private TextView news_views;
    private TextView news_summary;
    private TextView news_comment;
    private ImageView news_image_small;

    public NewsItemView(Context context) {
        super(context);
    }

    public NewsItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NewsItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.news_time = (TextView) findViewById(R.id.news_time);
        this.news_title = (TextView) findViewById(R.id.news_title);
        this.news_views = (TextView) findViewById(R.id.news_views);
        this.news_summary = (TextView) findViewById(R.id.news_summary);
        this.news_comment = (TextView) findViewById(R.id.news_comments);
        this.news_image_small = (ImageView) findViewById(R.id.news_image_small);
    }

    public void showNews(NewsItem item, DisplayImageOptions options) {
        news_title.setText(item.getTitle());
        news_views.setText(item.getCounter());
        news_time.setText(item.getInputtime());
        news_comment.setText(item.getComments());
        news_summary.setText(item.getSummary());
        //ImageLoaderUtil.loadImage(item.getThumb(), news_image_small, R.drawable.imagehoder, R.drawable.imagehoder_error);
        ImageLoader.getInstance().displayImage(item.getThumb(), news_image_small, options);
    }
}
