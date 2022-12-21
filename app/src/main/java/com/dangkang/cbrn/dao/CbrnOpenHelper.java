package com.dangkang.cbrn.dao;

import android.content.Context;

import com.dangkang.db.DaoMaster;


public class CbrnOpenHelper extends DaoMaster.OpenHelper {
    public CbrnOpenHelper(Context context, String name) {
        super(context, name);
    }
}
