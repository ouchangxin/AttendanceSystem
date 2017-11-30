package com.xinshen.attendancesystem;

import android.app.Application;
import com.tencent.bugly.crashreport.CrashReport;
import com.xinshen.sdk.face.FaceConfig;


/**
 * Created by thinkpad on 2017/11/14.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化bugly异常上报
        CrashReport.initCrashReport(getApplicationContext(), "90a88075fa", true);
        FaceConfig.newInstance().initFaceInfo(this);
//        CrashHandler crashHandler = CrashHandler.getInstance();
//        crashHandler.init(this);
    }
}
