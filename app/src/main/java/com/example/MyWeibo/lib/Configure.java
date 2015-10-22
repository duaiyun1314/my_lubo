package com.example.MyWeibo.lib;

import java.util.regex.Pattern;

/**
 * Created by wanglu on 15/6/19.
 */
public class Configure {


    public static final String BASE_URL = "http://www.cnbeta.com";
    public static final String NEWS_LIST_URL = BASE_URL + "/more";
    public static final Pattern HOT_COMMENT_PATTERN = Pattern.compile("来自<strong>(.*)</strong>的(.*)对新闻:<a href=\"/articles/(.*).htm\" target=\"_blank\">(.*)</a>的评论");

}
