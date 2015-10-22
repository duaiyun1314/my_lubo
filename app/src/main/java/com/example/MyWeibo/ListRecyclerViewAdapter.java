package com.example.MyWeibo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.MyWeibo.ui.BorderedRoundedCornersImageView;
import com.example.MyWeibo.ui.WeiboImgLayout;

/**
 * Created by wanglu on 15/5/15.
 */
public abstract class ListRecyclerViewAdapter<VH extends ListRecyclerViewAdapter.ItemViewHolder> extends RecyclerView.Adapter {



    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public BorderedRoundedCornersImageView user_img;
        public TextView user_name;
        public TextView status_source;
        public TextView status_text;
        public WeiboImgLayout layout;

        public ItemViewHolder(View itemView) {
            super(itemView);
            user_img = (BorderedRoundedCornersImageView) itemView.findViewById(R.id.user_img);
            user_name = (TextView) itemView.findViewById(R.id.user_name);
            status_source = (TextView) itemView.findViewById(R.id.status_source);
            status_text = (TextView) itemView.findViewById(R.id.status_text);
            layout = (WeiboImgLayout) itemView.findViewById(R.id.weibo_user_img);
        }
    }
}
