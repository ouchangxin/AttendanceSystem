package com.xinshen.attendancesystem.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.xinshen.attendancesystem.R;
import com.xinshen.attendancesystem.util.ScreenUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by thinkpad on 2017/11/16.
 */

public class AddPersonActivity extends Activity {

    @BindView(R.id.btn_pick)
    Button btn_pick;
    @BindView(R.id.btn_turn)
    Button btn_turn;
    @BindView(R.id.edit_name)
    EditText edit_name;
    @BindView(R.id.edit_depart)
    EditText edit_depart;
    @BindView(R.id.edit_job)
    EditText edit_job;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.btn_turn,R.id.btn_pick})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_turn:
                break;
            case R.id.btn_pick:
                break;
        }
    }


}
