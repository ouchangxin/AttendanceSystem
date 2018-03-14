package com.xinshen.attendancesystem.main;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.xinshen.attendancesystem.Global;
import com.xinshen.attendancesystem.R;
import com.xinshen.attendancesystem.adapter.EmployeeAdapter;
import com.xinshen.attendancesystem.main.employee.EmployeeBean;
import com.xinshen.attendancesystem.main.event.MessageEvent;
import com.xinshen.attendancesystem.util.EmployeeUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by thinkpad on 2017/11/15.
 */

public class ManagerActivity extends Activity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    @BindView(R.id.list_staff)
    ListView listView;
    @BindView(R.id.btn_add)
    Button btn_add;
    private ArrayList<EmployeeBean> employees;
    private EmployeeAdapter mAdapter;
    private int index;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        ButterKnife.bind(this);
        initAdapter();
        registerEvent();
    }

    private void registerEvent() {
        EventBus.getDefault().register(this);
    }

    private void initAdapter() {
        employees = (ArrayList<EmployeeBean>) EmployeeUtils.getEmployees(this);
        mAdapter = new EmployeeAdapter(employees,this);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
    }


    @OnClick(R.id.btn_add)
    public void onclick(View view){
        startActivityForResult(new Intent(this,APersonActivity.class),
                Global.Const.REQUEST_CODE);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event){
        switch (event.getType()){
            case MessageEvent.EVENT_UPDATE:
                employees.set(index,event.getBean());
                mAdapter.notifyDataSetChanged();
                EmployeeUtils.setEmployee(this,event.getBean(),index);
                break;
            case MessageEvent.EVENT_AFACE:
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Global.Const.REQUEST_CODE && resultCode == RESULT_OK){
                if (Global.Variable.BEAN_TEMP != null){
                    mAdapter.addItem(Global.Variable.BEAN_TEMP);
                    EmployeeUtils.saveEmployee(this,Global.Variable.BEAN_TEMP);
                    Global.Variable.BEAN_TEMP =  null;
                }
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Global.Variable.BEAN_TEMP = employees.get(position);
        index = position;
        startActivity(new Intent(this,InfoActivity.class));
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
