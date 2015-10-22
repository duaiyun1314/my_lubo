package com.example.MyWeibo.processers;

import com.example.MyWeibo.data.ListDataProvider;

/**
 * 有menu的区别
 */
public class NewsListProcesser<DataProvider extends ListDataProvider> extends BaseListProcesser<DataProvider> {
    public NewsListProcesser(DataProvider provider) {
        super(provider);
    }
}
