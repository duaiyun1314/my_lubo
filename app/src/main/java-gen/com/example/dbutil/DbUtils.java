package com.example.dbutil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.MyWeibo.LuBoApplication;

/**
 * Created by Andy.Wang on 2015/11/4.
 */
public class DbUtils {
    private static DbUtils instance;
    private TopicItemDao topicItemDao;

    private DbUtils( ) {
        DaoMaster.DevOpenHelper openHelper = LuBoApplication.getInstance().getHelper();
        SQLiteDatabase db = openHelper.getWritableDatabase();
        DaoMaster master = new DaoMaster(db);
        DaoSession session = master.newSession();
        topicItemDao = session.getTopicItemDao();
    }

    public static synchronized DbUtils getInstance( ) {
        if (instance == null) {
            instance = new DbUtils();
        }
        return instance;
    }

    public TopicItemDao getTopicItemDao() {
        return topicItemDao;
    }
}
