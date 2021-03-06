package com.example.dbutil;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.example.dbutil.TopicItem;

import com.example.dbutil.TopicItemDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig topicItemDaoConfig;

    private final TopicItemDao topicItemDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        topicItemDaoConfig = daoConfigMap.get(TopicItemDao.class).clone();
        topicItemDaoConfig.initIdentityScope(type);

        topicItemDao = new TopicItemDao(topicItemDaoConfig, this);

        registerDao(TopicItem.class, topicItemDao);
    }
    
    public void clear() {
        topicItemDaoConfig.getIdentityScope().clear();
    }

    public TopicItemDao getTopicItemDao() {
        return topicItemDao;
    }

}
