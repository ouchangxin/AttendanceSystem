package com.xinshen.attendancesystem.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.bumptech.glide.Glide;
import com.xinshen.attendancesystem.Global;
import com.xinshen.attendancesystem.R;
import com.xinshen.attendancesystem.main.employee.EmployeeBean;
import com.xinshen.attendancesystem.main.event.MessageEvent;
import com.xinshen.attendancesystem.util.GlideCircleTransform;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.iwf.photopicker.PhotoPicker;

/**
 * Created by thinkpad on 2017/12/6.
 */

public class InfoActivity extends Activity {

    @BindView(R.id.btn_edit_save)
    Button btn_edit_save;
    @BindView(R.id.btn_avator)
    ImageButton btn_avator;
    @BindView(R.id.edit_name)
    EditText edit_name;
    @BindView(R.id.edit_depart)
    EditText edit_depart;
    @BindView(R.id.edit_job)
    EditText edit_job;
    @BindView(R.id.edit_sample)
    EditText edit_sample;
    @BindView(R.id.btn_update)
    Button btn_update;
    private EmployeeBean employee;
    private boolean editable = false;
    private String avator_url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        ButterKnife.bind(this);
        btn_avator.setClickable(false);
        if (Global.Variable.BEAN_TEMP != null) {
            employee = Global.Variable.BEAN_TEMP;
            Global.Variable.BEAN_TEMP = null;
            setInfo();
        }
    }

    @OnClick({R.id.btn_avator, R.id.btn_edit_save,R.id.btn_update})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_avator:
               if (btn_avator.isClickable()){
                   PhotoPicker.builder()
                           .setPhotoCount(1)
                           .setShowCamera(true)
                           .setPreviewEnabled(false)
                           .start(this, PhotoPicker.REQUEST_CODE);
               }
                break;
            case R.id.btn_edit_save:
                if (!editable){
                    editable = true;
                    setEditable(true);
                }else{
                    editable = false;
                    saveInfo();
                    setEditable(false);
                }
                break;
            case R.id.btn_update:
                break;
        }
    }
    private void setEditable(boolean isEditing){
        if (isEditing){
            btn_edit_save.setBackgroundResource(R.drawable.insert_save);
            btn_avator.setClickable(true);
            edit_name.setFocusable(true);
            edit_name.setFocusableInTouchMode(true);
            edit_name.setSelection(edit_name.getText().toString().length());
            edit_name.requestFocus();
            edit_depart.setFocusable(true);
            edit_depart.setFocusableInTouchMode(true);
            edit_job.setFocusable(true);
            edit_job.setFocusableInTouchMode(true);
            btn_update.setVisibility(View.VISIBLE);
        }else{
            btn_edit_save.setBackgroundResource(R.drawable.insert_edit);
            btn_avator.setClickable(false);
            edit_name.setFocusable(false);
            edit_depart.setFocusable(false);
            edit_job.setFocusable(false);
            btn_update.setVisibility(View.INVISIBLE);
        }

    }
    private void saveInfo() {
        if (avator_url == null){
            avator_url = employee.getAvator_url();
        }
        String name = edit_name.getText().toString();
        String depate = edit_depart.getText().toString();
        String job = edit_job.getText().toString();
        String sample = edit_sample.getText().toString();
        if (!name.equals(employee.getName()) || !depate.equals(employee.getDepart())
                || !job.toString().equals(employee.getPosition())
                || !sample.equals(employee.getDate())
                || !avator_url.equals(employee.getAvator_url())){
            EmployeeBean newBean = new EmployeeBean(avator_url,name,depate,job,
                    sample,employee.getFace_token());
            EventBus.getDefault().post(new MessageEvent(MessageEvent.EVENT_UPDATE,newBean));
        }
        finish();
    }

    private void setInfo() {
        if (employee == null)
            return;
        Glide.with(this).load(employee.getAvator_url())
                .skipMemoryCache(true)  //不加这句话，只有第一遍加载才是圆形图片，原因暂时不知道
                .transform(new GlideCircleTransform(this))
                .into(btn_avator);
        edit_name.setText(employee.getName());
        edit_depart.setText(employee.getDepart());
        edit_job.setText(employee.getPosition());
        edit_sample.setText(employee.getDate());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == RESULT_OK &&requestCode == PhotoPicker.REQUEST_CODE){
                if (data != null) {
                    ArrayList<String> photos =
                            data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                    if (photos!=null && photos.size() >0){
                        avator_url = photos.get(0);
                        Glide.with(this).load(avator_url).transform(new GlideCircleTransform(this))
                                .into(btn_avator);
                    }
                }
            }
    }
}
