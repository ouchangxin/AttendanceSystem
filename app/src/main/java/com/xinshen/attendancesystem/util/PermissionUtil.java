package com.xinshen.attendancesystem.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thinkpad on 2017/9/28.
 */

public class PermissionUtil  {

    public static final int REQUEST_CODE = 1 ;
    private static Activity activity;
    private PermissionUtil(){}

    public static boolean checkPermission(Activity ac,final String[] permissions) {
        activity = ac;
        if (Build.VERSION.SDK_INT >= 23){
            List<String> applies = new ArrayList<String>();
            boolean isAllGranted = check(permissions,applies);
            if (!isAllGranted){
                String[] check = new String[applies.size()];
                applies.toArray(check);
                ActivityCompat.requestPermissions(activity,check,REQUEST_CODE);
                return false;
            }
        }
        return true;
    }

    private static boolean check(final String[] permissions,List<String> applies) {
        if (permissions!=null && applies!=null && applies.size()==0){
            for (String permission : permissions){
                if (ContextCompat.checkSelfPermission(activity,permission)!=
                        PackageManager.PERMISSION_GRANTED)
                    applies.add(permission);
            }
            if (applies.size()!=0)
                return false;
        }
        return  true;
    }
}
