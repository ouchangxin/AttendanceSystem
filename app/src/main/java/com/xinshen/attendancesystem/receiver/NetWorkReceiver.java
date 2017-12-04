package com.xinshen.attendancesystem.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.xinshen.attendancesystem.Global;
import com.xinshen.attendancesystem.main.MainActivity;
import com.xinshen.attendancesystem.util.NetWorkUtils;
import com.xinshen.attendancesystem.util.ToastUtil;

/**
 * Created by thinkpad on 2017/11/30.
 */

public class NetWorkReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
            if (Global.Const.ACTION_NETSTATE_CHANGE.equals(intent.getAction())){
                boolean isConnected = NetWorkUtils.isConnected(context);
                if (isConnected){
                    Global.Variable.CONNECTED = true;
                }else{
                    Global.Variable.CONNECTED = false;
                    context.startActivity(new Intent(context, MainActivity.class));
                    ToastUtil.showShort(context,"网络连接已断开");
                }
            }
    }
}
