package com.example.dbutil;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.example.dbutil.TopicItem;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table topic.
*/
public class TopicItemDao extends AbstractDao<TopicItem, Void> {

    public static final String TABLENAME = "topic";

    /**
     * Properties of entity TopicItem.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Saved = new Property(0, Boolean.class, "saved", false, "SAVED");
        public final static Property TopicId = new Property(1, String.class, "topicId", false, "TOPIC_ID");
        public final static Property TopicName = new Property(2, String.class, "topicName", false, "TOPIC_NAME");
        public final static Property TopicImage = new Property(3, String.class, "topicImage", false, "TOPIC_IMAGE");
    };


    public TopicItemDao(DaoConfig config) {
        super(config);
    }
    
    public TopicItemDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'topic' (" + //
                "'SAVED' INTEGER," + // 0: saved
                "'TOPIC_ID' TEXT," + // 1: topicId
                "'TOPIC_NAME' TEXT," + // 2: topicName
                "'TOPIC_IMAGE' TEXT);"); // 3: topicImage
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'topic'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, TopicItem entity) {
        stmt.clearBindings();
 
        Boolean saved = entity.getSaved();
        if (saved != null) {
            stmt.bindLong(1, saved ? 1l: 0l);
        }
 
        String topicId = entity.getTopicId();
        if (topicId != null) {
            stmt.bindString(2, topicId);
        }
 
        String topicName = entity.getTopicName();
        if (topicName != null) {
            stmt.bindString(3, topicName);
        }
 
        String topicImage = entity.getTopicImage();
        if (topicImage != null) {
            stmt.bindString(4, topicImage);
        }
    }

    /** @inheritdoc */
    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    /** @inheritdoc */
    @Override
    public TopicItem readEntity(Cursor cursor, int offset) {
        TopicItem entity = new TopicItem( //
            cursor.isNull(offset + 0) ? null : cursor.getShort(offset + 0) != 0, // saved
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // topicId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // topicName
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3) // topicImage
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, TopicItem entity, int offset) {
        entity.setSaved(cursor.isNull(offset + 0) ? null : cursor.getShort(offset + 0) != 0);
        entity.setTopicId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setTopicName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setTopicImage(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
     }
    
    /** @inheritdoc */
    @Override
    protected Void updateKeyAfterInsert(TopicItem entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    /** @inheritdoc */
    @Override
    public Void getKey(TopicItem entity) {
        return null;
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
