package com.dangkang.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.dangkang.cbrn.db.TypeInfo;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "TYPE_INFO".
*/
public class TypeInfoDao extends AbstractDao<TypeInfo, Void> {

    public static final String TABLENAME = "TYPE_INFO";

    /**
     * Properties of entity TypeInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Type = new Property(0, int.class, "type", false, "TYPE");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
    }


    public TypeInfoDao(DaoConfig config) {
        super(config);
    }
    
    public TypeInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"TYPE_INFO\" (" + //
                "\"TYPE\" INTEGER NOT NULL ," + // 0: type
                "\"NAME\" TEXT);"); // 1: name
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"TYPE_INFO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, TypeInfo entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getType());
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, TypeInfo entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getType());
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
    }

    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    @Override
    public TypeInfo readEntity(Cursor cursor, int offset) {
        TypeInfo entity = new TypeInfo( //
            cursor.getInt(offset + 0), // type
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1) // name
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, TypeInfo entity, int offset) {
        entity.setType(cursor.getInt(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
     }
    
    @Override
    protected final Void updateKeyAfterInsert(TypeInfo entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    @Override
    public Void getKey(TypeInfo entity) {
        return null;
    }

    @Override
    public boolean hasKey(TypeInfo entity) {
        // TODO
        return false;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}