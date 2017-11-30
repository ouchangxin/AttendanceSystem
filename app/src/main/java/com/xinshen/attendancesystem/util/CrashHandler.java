package com.xinshen.attendancesystem.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Process;

import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by thinkpad on 2017/11/6.
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler{
    private static final String FILE_NAME = "crash.trace";
    private static CrashHandler mCrashHandler = new CrashHandler();
    private Thread.UncaughtExceptionHandler mDefaultCrashHandler;
    private Context mContext;
    private CrashHandler(){}
    public void init(Context context){
        mContext = context.getApplicationContext();
        mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }
    public static CrashHandler getInstance(){
        return mCrashHandler;
    }
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        e.printStackTrace();
        saveInfo(e);
        if (mDefaultCrashHandler!=null){
            mDefaultCrashHandler.uncaughtException(t,e);
        }else {
            Process.killProcess(Process.myPid());
        }
    }
    private void saveInfo(Throwable e) {
        long current_time = System.currentTimeMillis();
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(current_time));
        File file = new File(FILE_NAME);
        try {
            FileOutputStream out = mContext.openFileOutput(file.getName(),Context.MODE_PRIVATE);
            PrintWriter writer = new PrintWriter(out);
            writer.print(time);
            savePhoneInfo(writer);
            writer.println();
            e.printStackTrace(writer);
            writer.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
    }

    private void savePhoneInfo(PrintWriter writer) {
        PackageManager manager = mContext.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(mContext.getPackageName(),
                    PackageManager.GET_ACTIVITIES);
            writer.println("APP Version："+info.versionName+"_"+info.versionCode);
            writer.println("OS Version："+ Build.VERSION.RELEASE+"_"+Build.VERSION.SDK_INT);
            writer.println("Vendor："+Build.MANUFACTURER);
            writer.println("Model："+Build.MODEL);
            writer.print("CPU ABI："+Build.CPU_ABI);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
