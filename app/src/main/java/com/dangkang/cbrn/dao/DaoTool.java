package com.dangkang.cbrn.dao;

import android.app.DownloadManager;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.dangkang.cbrn.db.DeviceInfo;
import com.dangkang.cbrn.db.TaintInfo;
import com.dangkang.db.DaoMaster;
import com.dangkang.db.DaoSession;
import com.dangkang.db.DeviceInfoDao;
import com.dangkang.db.TaintInfoDao;

import org.greenrobot.greendao.query.Query;

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
        return sDaoSession.getTaintInfoDao().queryBuilder().where(TaintInfoDao.Properties.Type.eq(1)).build().list();
    }
    public static List<TaintInfo> queryChemicalTaintInfo(){
        return sDaoSession.getTaintInfoDao().queryBuilder().where(TaintInfoDao.Properties.Type.eq(2)).build().list();
    }
    public static DeviceInfo queryDeviceInfo(String name){
        List<DeviceInfo> deviceInfo = sDaoSession.getDeviceInfoDao().queryBuilder().where(DeviceInfoDao.Properties.Brand.eq(name)).build().list();
        if (deviceInfo != null && deviceInfo.size() > 0){
            return deviceInfo.get(0);
        }else{
            return null;
        }
    }
    public static void removeTaintInfo(int taint_num){
        String sql = "delete from TAINT_INFO where TAINT_NUM = ?";
        sDaoSession.getDatabase().execSQL(sql,new String[]{String.valueOf(taint_num)});
    }
    public static void updateTaintInfo(List<TaintInfo> taintInfo,int type){
        String sql  = "DELETE from TAINT_INFO where type = ?";
        sDaoSession.getDatabase().execSQL(sql,new String[]{String.valueOf(type)});
        sDaoSession.getTaintInfoDao().insertOrReplaceInTx(taintInfo);
    }
    public static void updateDeviceInfo(List<DeviceInfo> deviceInfo){
        String sql = "delete from DEVICE_INFO ";
        sDaoSession.getDatabase().execSQL(sql);
        sDaoSession.getDeviceInfoDao().insertOrReplaceInTx(deviceInfo);
    }

}
