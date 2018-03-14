package com.xinshen.attendancesystem.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.bumptech.glide.Glide;
import com.xinshen.attendancesystem.Global;
import com.xinshen.attendancesystem.R;
import com.xinshen.attendancesystem.main.employee.EmployeeBean;
import com.xinshen.attendancesystem.util.ToastUtil;
import com.xinshen.attendancesystem.util.glide.GlideCircleTransform;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.iwf.photopicker.PhotoPicker;

/**
 * Created by thinkpad on 2017/11/16.
 */

public class APersonActivity extends Activity {

    @BindView(R.id.btn_pick)
    ImageButton btn_pick;
    @BindView(R.id.btn_turn)
    Button btn_turn;
    @BindView(R.id.edit_name)
    EditText edit_name;
    @BindView(R.id.edit_depart)
    EditText edit_depart;
    @BindView(R.id.edit_job)
    EditText edit_job;
    private String avator_url;
    private String name;
    private String depart;
    private String position;
    public static final String EXTRA_NAME = "extra_name";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aperson);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.btn_turn,R.id.btn_pick})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_turn:
                inputInfo();
              if (TextUtils.isEmpty(avator_url) ||
                  TextUtils.isEmpty(avator_url)
                     || TextUtils.isEmpty(avator_url)
                        || TextUtils.isEmpty(avator_url)){
                  ToastUtil.showShort(this,"请完善个人信息");
              }else{
                  if (!Global.Variable.CONNECTED)
                      ToastUtil.showShort(this,"无法连接到网络");
                  else{
                      Intent intent = new Intent(this,AFaceActivity.class);
                      intent.putExtra(EXTRA_NAME,name);
                      startActivityForResult(intent,
                              Global.Const.REQUEST_CODE);
                  }
              }
                break;
            case R.id.btn_pick:
                PhotoPicker.builder()
                        .setPhotoCount(1)
                        .setShowCamera(true)
                        .setPreviewEnabled(false)
                        .start(this, PhotoPicker.REQUEST_CODE);
                break;
        }
    }

    private void inputInfo() {
        name = edit_name.getText().toString().trim();
        depart = edit_depart.getText().toString().trim();
        position = edit_job.getText().toString().trim();
    }

    private void updateTime() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        if (day<10)
            Global.Variable.CURRENT_TIME = year+"-"+month+"-0"+day;
        else
            Global.Variable.CURRENT_TIME = year+"-"+month+"-"+day;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK ) {
            if (requestCode == PhotoPicker.REQUEST_CODE){
                if (data != null) {
                    ArrayList<String> photos =
                            data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                    if (photos!=null && photos.size() >0){
                        avator_url = photos.get(0);
                        Glide.with(this).load(avator_url).transform(new GlideCircleTransform(this))
                                .into(btn_pick);
                    }
                }
            }else if(requestCode == Global.Const.REQUEST_CODE){
                updateTime();
                String face_token = data.getStringExtra(AFaceActivity.EXTRA_FACE_TOKEN);
                EmployeeBean bean = new EmployeeBean(avator_url,name,
                        depart,position,Global.Variable.CURRENT_TIME,face_token);
                Global.Variable.BEAN_TEMP = bean;
                setResult(RESULT_OK);
                finish();
            }
        }
    }
}
