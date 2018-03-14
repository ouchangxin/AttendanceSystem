package com.xinshen.attendancesystem.main;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.xinshen.attendancesystem.Global;
import com.xinshen.attendancesystem.R;
import com.xinshen.attendancesystem.util.PermissionUtil;
import com.xinshen.attendancesystem.util.SPUtils;
import com.xinshen.attendancesystem.util.ScreenUtil;
import com.xinshen.attendancesystem.util.ToastUtil;

import static com.xinshen.attendancesystem.Global.Const.KEY_COMPANY_NAME;
import static com.xinshen.attendancesystem.Global.Const.LOG_TAG;
import static com.xinshen.attendancesystem.Global.Const.REQUEST_CODE;

/**
 * Created by thinkpad on 2017/10/25.
 */

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenUtil.setFullScreen(this);
        ScreenUtil.keepScreenLight(this);
        setContentView(R.layout.activity_login);
        /**初始化Logger*/
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .tag(LOG_TAG)
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
        /**检测权限*/
        String[] permissions = new String[]{Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION};
        if (PermissionUtil.checkPermission(this, permissions)) {
            login();
        }
    }


    /**
     * 权限检测回调
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int grant : grantResults) {
            if (grant != PackageManager.PERMISSION_GRANTED) {
                ToastUtil.showShort(this, "未获得授权");
                finish();
                return;
            }
        }
        login();
    }

    private void login() {
        String company = (String) SPUtils.get(this, KEY_COMPANY_NAME, "NULL");
        if (!"NULL".equals(company)) {
            Global.Variable.COMPANY_NAME = company;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    LoginActivity.this.finish();
                }
            }, Global.Const.LOGIN_TIME);
        } else {
            startActivityForResult(new Intent(this, EnterActivity.class), REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_CANCELED) {
                finish();
            } else if (resultCode == RESULT_OK) {
                startActivity(new Intent(this, MainActivity.class));
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
