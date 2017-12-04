package com.xinshen.attendancesystem.main;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.xinshen.attendancesystem.Global;
import com.xinshen.attendancesystem.R;
import com.xinshen.attendancesystem.receiver.NetWorkReceiver;
import com.xinshen.attendancesystem.util.Base64Util;
import com.xinshen.attendancesystem.util.ScreenUtil;
import com.xinshen.attendancesystem.util.ToastUtil;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {

    @BindView(R.id.text_year)
    TextView text_year;
    @BindView(R.id.text_date)
    TextView text_date;
    @BindView(R.id.text_detect)
    TextView text_detect;
    @BindView(R.id.text_manager)
    TextView text_manager;
    @BindView(R.id.text_attendance)
    TextView text_attendance;
    private NetWorkReceiver netReceiver;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenUtil.setFullScreen(this);
        ScreenUtil.keepScreenLight(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initTime();
        register();
    }

    private void register() {
        IntentFilter filter = new IntentFilter(Global.Const.ACTION_NETSTATE_CHANGE);
        netReceiver  = new NetWorkReceiver();
        registerReceiver(netReceiver,filter);
    }

    private void initTime() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        text_year.setText(year+" 年");
        text_date.setText(month+" 月"+" "+day+" 日");
    }


    @OnClick({R.id.text_detect,R.id.text_manager,R.id.text_attendance})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.text_detect:
                if (Global.Variable.CONNECTED){
                    startActivity(new Intent(this,DetectActivity.class));
                } else{
                    ToastUtil.showShort(this,"无法连接到网络");
                }
                break;
            case R.id.text_manager:
                if (Global.Variable.CONNECTED){
                    startActivity(new Intent(this,StaffManagerActivity.class));
                } else{
                    ToastUtil.showShort(this,"无法连接到网络");
                }
                break;
            case R.id.text_attendance:

                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(netReceiver);
    }
}
