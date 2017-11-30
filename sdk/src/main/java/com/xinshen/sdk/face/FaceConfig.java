package com.xinshen.sdk.face;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.xinshen.sdk.Global;
import com.xinshen.sdk.util.LogWrapper;

/**
 * Created by thinkpad on 2017/11/16.
 */

public class FaceConfig {

    private static FaceConfig config;
    private FaceConfig(){}
    public static synchronized FaceConfig newInstance(){
        if (config == null){
            config = new FaceConfig();
        }
        return config;
    }
    public void initFaceInfo(Context context){
        LogWrapper.isDebug = true;
        LogWrapper.TAG = Global.LOG_TAG;
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            Global.API_KEY =  info.metaData.getString(Global.API_KEY_NAME);
            Global.API_SECRET =  info.metaData.getString(Global.API_SECRET_NAME);
            if (Global.API_KEY == null || Global.API_SECRET == null)
                throw  new NullPointerException("key or secret was null");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
