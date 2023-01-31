package com.dangkang.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.dangkang.cbrn.db.DeviceInfo;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "DEVICE_INFO".
*/
public class DeviceInfoDao extends AbstractDao<DeviceInfo, Long> {

    public static final String TABLENAME = "DEVICE_INFO";

    /**
     * Properties of entity DeviceInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Brand = new Property(1, String.class, "brand", false, "BRAND");
        public final static Property Result = new Property(2, String.class, "result", false, "RESULT");
        public final static Property Type = new Property(3, String.class, "type", false, "TYPE");
        public final static Property Status = new Property(4, int.class, "status", false, "STATUS");
    }


    public DeviceInfoDao(DaoConfig config) {
        super(config);
    }
    
    public DeviceInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"DEVICE_INFO\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"BRAND\" TEXT," + // 1: brand
                "\"RESULT\" TEXT," + // 2: result
                "\"TYPE\" TEXT," + // 3: type
                "\"STATUS\" INTEGER NOT NULL );"); // 4: status
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"DEVICE_INFO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, DeviceInfo entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String brand = entity.getBrand();
        if (brand != null) {
            stmt.bindString(2, brand);
        }
 
        String result = entity.getResult();
        if (result != null) {
            stmt.bindString(3, result);
        }
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(4, type);
        }
        stmt.bindLong(5, entity.getStatus());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, DeviceInfo entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String brand = entity.getBrand();
        if (brand != null) {
            stmt.bindString(2, brand);
        }
 
        String result = entity.getResult();
        if (result != null) {
            stmt.bindString(3, result);
        }
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(4, type);
        }
        stmt.bindLong(5, entity.getStatus());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public DeviceInfo readEntity(Cursor cursor, int offset) {
        DeviceInfo entity = new DeviceInfo( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // brand
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // result
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // type
            cursor.getInt(offset + 4) // status
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, DeviceInfo entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setBrand(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setResult(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setType(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setStatus(cursor.getInt(offset + 4));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(DeviceInfo entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(DeviceInfo entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(DeviceInfo entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
