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

    public static List<TaintInfo> queryTaintInfo() {
        return sDaoSession.getTaintInfoDao().queryBuilder().build().list();
    }
    private static void dealWithDemoData() {
        String sql = "delete from TAINT_INFO where  CREATE_TIME <= 1542708900";
        sDaoSession.getDatabase().execSQL(sql);
        Query<TaintInfo> taintInfoQuery = sDaoSession.getTaintInfoDao().queryBuilder().where(TaintInfoDao.Properties.Taint_num.eq("0110")).build();
        TaintInfo taintInfo = taintInfoQuery.unique();
        if (taintInfo == null) {
            sql = "INSERT INTO \"main\".\"TAINT_INFO\"(\"TYPE\", \"TAINT_NUM\", \"TAINT_LOC\", \"TAINT_SIM\", \"TAINT_DIS\", \"TAINT_MAX\", \"CREATE_TIME\") VALUES ('0', '0110', '未知', '56','100', '5300','1542708900');";
            sDaoSession.getDatabase().execSQL(sql);
            sql = "INSERT INTO \"main\".\"TAINT_INFO\"(\"TYPE\", \"TAINT_NUM\", \"TAINT_LOC\", \"TAINT_SIM\", \"TAINT_DIS\", \"TAINT_MAX\", \"CREATE_TIME\") VALUES ('0', '0111', '未知', '56','99', '5400','1542708800');";
            sDaoSession.getDatabase().execSQL(sql);
            sql = "INSERT INTO \"main\".\"TAINT_INFO\"(\"TYPE\", \"TAINT_NUM\", \"TAINT_LOC\", \"TAINT_SIM\", \"TAINT_DIS\", \"TAINT_MAX\", \"CREATE_TIME\") VALUES ('0', '0112', '未知', '56','98', '5500','1542708700');";
            sDaoSession.getDatabase().execSQL(sql);
        }
    }
    public static void removeTaintInfo(int taint_num){
        String sql = "delete from TAINT_INFO where TAINT_NUM = ?";
        sDaoSession.getDatabase().execSQL(sql,new String[]{String.valueOf(taint_num)});
    }
    public static TaintInfo addTaintInfoTest(){
        TaintInfo taintInfo = new TaintInfo();
        taintInfo.setTaint_dis("200");
        taintInfo.setTaint_loc("未知");
        taintInfo.setTaint_max("300");
        taintInfo.setTaint_num(1144);
        taintInfo.setCreate_time(System.currentTimeMillis()/1000);
        sDaoSession.getTaintInfoDao().insert(taintInfo);
        return taintInfo;


    }

}
