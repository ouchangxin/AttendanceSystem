package com.xinshen.attendancesystem.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xinshen.attendancesystem.Global;
import com.xinshen.attendancesystem.R;
import com.xinshen.attendancesystem.adapter.EmployeeAdapter;
import com.xinshen.attendancesystem.main.employee.EmployeeBean;
import com.xinshen.attendancesystem.util.SPUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by thinkpad on 2017/11/15.
 */

public class ManagerActivity extends Activity {

    @BindView(R.id.list_staff)
    ListView list_staff;
    @BindView(R.id.btn_add)
    Button btn_add;
    private List<EmployeeBean> employees;
    private EmployeeAdapter mAdapter;
    private Gson mGson;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        mGson = new Gson();
        String data = (String) SPUtils.get(this,Global.Const.KEY_EMPLOYEE,"NULL");
        if (!"NULL".equals(data)){
            employees = mGson.fromJson(data,
                    new TypeToken<List<EmployeeBean>>(){}.getType());
        }else{
            employees = new ArrayList<>();
        }
        mAdapter = new EmployeeAdapter(employees,this);
        list_staff.setAdapter(mAdapter);
    }


    @OnClick(R.id.btn_add)
    public void onclick(View view){
        startActivityForResult(new Intent(this,APersonActivity.class),
                Global.Const.REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Global.Const.REQUEST_CODE){
            if (resultCode == RESULT_OK){
                if (Global.Variable.BEAN_TEMP != null){
                    mAdapter.addItem(Global.Variable.BEAN_TEMP);
                    SPUtils.put(this,Global.Const.KEY_EMPLOYEE,mGson.toJson(employees));
                    Global.Variable.BEAN_TEMP =  null;
                }
            }
        }
    }
}
