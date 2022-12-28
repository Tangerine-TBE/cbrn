package com.dangkang.cbrn.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.dangkang.cbrn.db.TaintInfo;
import com.dangkang.db.DaoMaster;
import com.dangkang.db.DaoSession;

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

    public static List<TaintInfo> queryTaintInfo() {
        return sDaoSession.getTaintInfoDao().queryBuilder().build().list();
    }

}
