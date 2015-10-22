package com.example.MyWeibo.lib.kits;

import com.example.MyWeibo.lib.Configure;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import org.apache.http.Header;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.message.BasicHeader;

/**
 * Created by wanglu on 15/6/19.
 */
public class NetKit {
    private static NetKit mInstance;
    private AsyncHttpClient mClient;

    private NetKit() {
        mClient = new AsyncHttpClient();
        mClient.setCookieStore(new BasicCookieStore());
        mClient.setConnectTimeout(3000);
        mClient.setResponseTimeout(6000);
        mClient.setMaxRetriesAndTimeout(3, 200);
        mClient.setUserAgent("Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.45 Safari/537.36");

    }

    public static NetKit getInstance() {
        if (mInstance == null) {
            mInstance = new NetKit();
        }
        return mInstance;
    }

    public void getNewslistByPage(int page, String type, ResponseHandlerInterface handlerInterface) {
        RequestParams params = new RequestParams();
        params.add("type", type);
        params.add("page", page + "");
        params.add("_", System.currentTimeMillis() + "");
        mClient.get(null, Configure.NEWS_LIST_URL, getAuthHeader(), params, handlerInterface);
    }

    public static Header[] getAuthHeader() {
        return new Header[]{
                new BasicHeader("Referer", "http://www.cnbeta.com/"),
                new BasicHeader("Origin", "http://www.cnbeta.com"),
                new BasicHeader("X-Requested-With", "XMLHttpRequest")
        };
    }
}
