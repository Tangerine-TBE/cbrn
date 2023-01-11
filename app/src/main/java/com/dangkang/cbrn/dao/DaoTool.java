package com.dangkang.cbrn.dao;

import android.app.DownloadManager;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.dangkang.cbrn.db.TaintInfo;
import com.dangkang.db.DaoMaster;
import com.dangkang.db.DaoSession;
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
        dealWithDemoData();
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
    private static void dealWithDemoData() {
        String sql = "delete from TAINT_INFO where  CREATE_TIME <= 1542708900";
        sDaoSession.getDatabase().execSQL(sql);
        Query<TaintInfo> taintInfoQuery = sDaoSession.getTaintInfoDao().queryBuilder().where(TaintInfoDao.Properties.Taint_num.eq("0110")).orderDesc(TaintInfoDao.Properties.Create_time).build();
        TaintInfo taintInfo = taintInfoQuery.unique();
        if (taintInfo == null) {
            sql = "INSERT INTO \"main\".\"TAINT_INFO\"(\"TYPE\", \"TAINT_NUM\", \"TAINT_LOC\", \"TAINT_SIM\", \"TAINT_DIS\", \"TAINT_MAX\", \"CREATE_TIME\") VALUES ('1', '0110', '未知', '56','100', '5300','1542708900');";
            sDaoSession.getDatabase().execSQL(sql);
            sql = "INSERT INTO \"main\".\"TAINT_INFO\"(\"TYPE\", \"TAINT_NUM\", \"TAINT_LOC\", \"TAINT_SIM\", \"TAINT_DIS\", \"TAINT_MAX\", \"CREATE_TIME\") VALUES ('2', '0111', '未知', '56','99', '5400','1542708800');";
            sDaoSession.getDatabase().execSQL(sql);
            sql = "INSERT INTO \"main\".\"TAINT_INFO\"(\"TYPE\", \"TAINT_NUM\", \"TAINT_LOC\", \"TAINT_SIM\", \"TAINT_DIS\", \"TAINT_MAX\", \"CREATE_TIME\") VALUES ('2', '0112', '未知', '56','98', '5500','1542708700');";
            sDaoSession.getDatabase().execSQL(sql);
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

}
