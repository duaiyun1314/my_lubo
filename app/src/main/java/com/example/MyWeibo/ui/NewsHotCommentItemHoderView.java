package com.example.MyWeibo.ui;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.MyWeibo.R;
import com.example.MyWeibo.model.HotCommentItem;
import com.example.MyWeibo.utils.ColorGenerator;
import com.example.MyWeibo.utils.QuickBadgeUtil;

/**
 * Created by wanglu on 15/9/25.
 */
public class NewsHotCommentItemHoderView extends CardView {
    private TextView mCommentContent;
    private BorderedRoundedCornersImageView mCommentImage;
    private TextView mCommentName;
    private TextView mCommentFrom;
    private TextView mNewsTitle;
    private ColorGenerator colorGenerator = ColorGenerator.MATERIAL;

    public NewsHotCommentItemHoderView(Context context) {
        super(context);
    }

    public NewsHotCommentItemHoderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NewsHotCommentItemHoderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mCommentContent = (TextView) findViewById(R.id.comment_content);
        mCommentImage = (BorderedRoundedCornersImageView) findViewById(R.id.comment_image);
        mCommentName = (TextView) findViewById(R.id.comment_name);
        mCommentFrom = (TextView) findViewById(R.id.comment_from);
        mNewsTitle = (TextView) findViewById(R.id.news_title);
    }

    public void showComment(HotCommentItem item) {
        mCommentContent.setText(item.getTitle());
        mCommentFrom.setText(item.getFrom());
        mCommentImage.setImageBitmap(QuickBadgeUtil.setContactBadgeText(String.valueOf(item.getDescription().charAt(0))), colorGenerator.getColor(item.getSid()));
        mCommentName.setText(item.getDescription());
        mNewsTitle.setText(item.getNewstitle());
    }
}
