package com.xinshen.attendancesystem;

import android.app.Application;
import com.tencent.bugly.crashreport.CrashReport;
import com.xinshen.attendancesystem.dao.DaoMaster;
import com.xinshen.attendancesystem.dao.DaoSession;
import com.xinshen.sdk.face.FaceConfig;

import org.greenrobot.greendao.database.Database;


/**
 * Created by thinkpad on 2017/11/14.
 */

public class MyApplication extends Application {

    private static DaoSession mDaoSession;
    public static final String DATABASE_NAME = "attendance.db";
    @Override
    public void onCreate() {
        super.onCreate();
        initSQLite();
        //初始化bugly异常上报
        CrashReport.initCrashReport(getApplicationContext(), "90a88075fa", true);
        FaceConfig.newInstance().initFaceInfo(this);
    }


    private void initSQLite() {
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(
                this,DATABASE_NAME);
        Database db = openHelper.getWritableDb();
        DaoMaster daoMaster = new DaoMaster(db);
        mDaoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoSession(){
        return mDaoSession;
    }
}
