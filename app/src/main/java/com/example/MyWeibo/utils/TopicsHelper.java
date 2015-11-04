package com.example.MyWeibo.utils;


import com.example.MyWeibo.LuBoApplication;
import com.example.MyWeibo.R;
import com.example.dbutil.DbUtils;
import com.example.dbutil.TopicItem;
import com.example.dbutil.TopicItemDao;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;

import de.greenrobot.dao.query.Query;

/**
 * Created by wanglu on 15/11/3.
 */
public class TopicsHelper {
    public static List<TopicItem> readLocalTopics() {
        List<TopicItem> lists = null;
        final TopicItemDao dao = DbUtils.getInstance().getTopicItemDao();
        Query query = dao.queryBuilder().build();
        lists = query.list();
        if (lists == null || lists.size() == 0) {
            try {
                InputStream is = LuBoApplication.getInstance().getResources().openRawResource(R.raw.topic_all);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, Charset.forName("utf-8")));
                while (bufferedReader.ready()) {
                    String[] line = bufferedReader.readLine().split("\t");
                    TopicItem item = new TopicItem();
                    item.setLatter(line[0]);
                    item.setTopicId(line[1]);
                    item.setTopicName(line[2]);
                    item.setTopicImage(line[3]);
                    lists.add(item);

                }
                final List<TopicItem> finalItems = lists;
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        dao.insertInTx(finalItems);
                    }
                }.start();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return lists;
    }
}
