package com.xinshen.attendancesystem.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.xinshen.attendancesystem.R;
import com.xinshen.attendancesystem.adapter.StaffAdapter;
import com.xinshen.attendancesystem.adapter.StaffBean;
import com.xinshen.attendancesystem.util.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by thinkpad on 2017/11/15.
 */

public class StaffManagerActivity extends Activity {

    @BindView(R.id.list_staff)
    ListView list_staff;
    @BindView(R.id.btn_add)
    Button btn_add;

    private List<StaffBean> staffList;
    private StaffAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        ButterKnife.bind(this);
        test();
    }

    @OnClick(R.id.btn_add)
    public void onclick(View view){
        startActivity(new Intent(this,AddPersonActivity.class));
    }


    private void test() {

        staffList = new ArrayList<StaffBean>();
        for (int i=0;i<10;i++){
            staffList.add(new StaffBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1510764002922&di=9b16b085c06e7f9e5aafe33440a01175&imgtype=0&src=http%3A%2F%2Fpic33.nipic.com%2F20130907%2F13534366_092511672176_2.jpg","xinshen","研发"));
        }
        mAdapter = new StaffAdapter(staffList,this);
        list_staff.setAdapter(mAdapter);
    }
}
