package com.xinshen.attendancesystem.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by thinkpad on 2017/11/30.
 */

public class NetWorkUtils {
   public static boolean isConnected(Context context){
       ConnectivityManager manager = (ConnectivityManager) context.getSystemService
               (Context.CONNECTIVITY_SERVICE);
       if (manager!=null){
           NetworkInfo info = manager.getActiveNetworkInfo();
           if (info != null && info.isConnected()){
                return  true;
           }
       }
       return false;
   }
}
