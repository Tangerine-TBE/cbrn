package com.dangkang.db;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.dangkang.cbrn.db.DeviceInfo;
import com.dangkang.cbrn.db.TaintInfo;

import com.dangkang.db.DeviceInfoDao;
import com.dangkang.db.TaintInfoDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig deviceInfoDaoConfig;
    private final DaoConfig taintInfoDaoConfig;

    private final DeviceInfoDao deviceInfoDao;
    private final TaintInfoDao taintInfoDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        deviceInfoDaoConfig = daoConfigMap.get(DeviceInfoDao.class).clone();
        deviceInfoDaoConfig.initIdentityScope(type);

        taintInfoDaoConfig = daoConfigMap.get(TaintInfoDao.class).clone();
        taintInfoDaoConfig.initIdentityScope(type);

        deviceInfoDao = new DeviceInfoDao(deviceInfoDaoConfig, this);
        taintInfoDao = new TaintInfoDao(taintInfoDaoConfig, this);

        registerDao(DeviceInfo.class, deviceInfoDao);
        registerDao(TaintInfo.class, taintInfoDao);
    }
    
    public void clear() {
        deviceInfoDaoConfig.clearIdentityScope();
        taintInfoDaoConfig.clearIdentityScope();
    }

    public DeviceInfoDao getDeviceInfoDao() {
        return deviceInfoDao;
    }

    public TaintInfoDao getTaintInfoDao() {
        return taintInfoDao;
    }

}
