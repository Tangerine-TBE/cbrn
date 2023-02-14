package com.dangkang.cbrn.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dangkang.cbrn.db.DeviceInfo;
import com.dangkang.cbrn.db.TaintInfo;
import com.dangkang.cbrn.db.TypeInfo;
import com.dangkang.db.DaoMaster;
import com.dangkang.db.DaoSession;
import com.dangkang.db.DeviceInfoDao;
import com.dangkang.db.TaintInfoDao;
import com.dangkang.db.TypeInfoDao;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DaoTool {
    public static DaoSession sDaoSession;
    private static DaoMaster daoMaster;

    public static void init(Context ctx) {
        CbrnOpenHelper helper = new CbrnOpenHelper(ctx, "cbrn.db");
        SQLiteDatabase db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        sDaoSession = daoMaster.newSession();
    }

    public static void clearDataBase() {
        DaoMaster.dropAllTables(daoMaster.getDatabase(), true);
        DaoMaster.createAllTables(daoMaster.getDatabase(), true);
    }

    public static List<TaintInfo> queryRadiationTaintInfo() {
        return sDaoSession.getTaintInfoDao().queryBuilder().where(TaintInfoDao.Properties.Type.eq(1)).orderDesc(TaintInfoDao.Properties.Taint_num).build().list();
    }

    public static List<TaintInfo> queryChemicalTaintInfo() {
        return sDaoSession.getTaintInfoDao().queryBuilder().where(TaintInfoDao.Properties.Type.eq(2)).build().list();
    }

    public static DeviceInfo queryDeviceInfo(String name) {
        List<DeviceInfo> deviceInfo = sDaoSession.getDeviceInfoDao().queryBuilder().where(DeviceInfoDao.Properties.Brand.eq(name)).build().list();
        if (deviceInfo != null && deviceInfo.size() > 0) {
            return deviceInfo.get(0);
        } else {
            return null;
        }
    }

    public static List<DeviceInfo> queryAllDeviceInfo() {
        return sDaoSession.getDeviceInfoDao().queryBuilder().orderDesc(DeviceInfoDao.Properties.Id).list();
    }

    /*1 生物
     * 2 化学*/
    public static List<TypeInfo> queryAllTypeInfo(int status) {
        List<TypeInfo> typeInfo = sDaoSession.getTypeInfoDao().queryBuilder().where(TypeInfoDao.Properties.Status.eq(status)).orderDesc(TypeInfoDao.Properties.Create_time).list();
        if (typeInfo != null && typeInfo.size() > 0) {
            return typeInfo;
        } else {
            return null;
        }
    }

    public static List<String> queryAllTypeInfoName(int status) {
        String sql = "select t.NAME from TYPE_INFO t where t.STATUS == ? order by t.CREATE_TIME desc";
        Cursor cursor = sDaoSession.getDatabase().rawQuery(sql, new String[]{String.valueOf(status)});
        List<String> values = new ArrayList<>();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    values.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }
        }
        return values;
    }

    public static void removeTaintInfo(int taint_num) {
        String sql = "delete from TAINT_INFO where TAINT_NUM = ?";
        sDaoSession.getDatabase().execSQL(sql, new String[]{String.valueOf(taint_num)});
    }

    public static void updateTaintInfo(List<TaintInfo> taintInfo, int type) {
        String sql = "DELETE from TAINT_INFO where type = ?";
        sDaoSession.getDatabase().execSQL(sql, new String[]{String.valueOf(type)});
        sDaoSession.getTaintInfoDao().insertOrReplaceInTx(taintInfo);
    }

    public static void updateDeviceInfo(List<DeviceInfo> deviceInfo) {
        String sql = "delete from DEVICE_INFO ";
        sDaoSession.getDatabase().execSQL(sql);
        /*如果是未知设备就不进行保存*/
        /*迭代器循环*/
        Iterator<DeviceInfo> iterator = deviceInfo.iterator();
        while (iterator.hasNext()) {
            String value = iterator.next().getBrand();
            if ("未知设备".equals(value)) {
                iterator.remove();
            }
        }
        sDaoSession.getDeviceInfoDao().insertOrReplaceInTx(deviceInfo);
    }

    /**
     * 生物 status==1
     * 辐射 status==2
     */
    public static void addTypeInfo(int type, String name, long create_time, int status) {
        sDaoSession.getTypeInfoDao().insertOrReplace(new TypeInfo(create_time, type, name, status));
    }

}
