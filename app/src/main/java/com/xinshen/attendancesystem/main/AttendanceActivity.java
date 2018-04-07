package com.xinshen.attendancesystem.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xinshen.attendancesystem.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by thinkpad on 2018/3/14.
 * 今日考勤界面
 */

public class AttendanceActivity extends AppCompatActivity {

    @BindView(R.id.tv_week)
    TextView tvWeek;
    @BindView(R.id.tv_Date)
    TextView tvDate;
    @BindView(R.id.tv_work)
    TextView tvWork;
    @BindView(R.id.tv_offWork)
    TextView tvOffWork;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.tv_normal)
    TextView tvNormal;
    @BindView(R.id.tv_num_normal)
    TextView tvNumNormal;
    @BindView(R.id.tv_num_late)
    TextView tvNumLate;
    @BindView(R.id.tv_num_leave)
    TextView tvNumLeave;
    @BindView(R.id.tv_num_absent)
    TextView tvNumAbsent;
    @BindView(R.id.tv_at_config)
    TextView tvAtConfig;
    @BindView(R.id.tv_at_record)
    TextView tvAtRecord;
    @BindView(R.id.ly_menu)
    LinearLayout lyMenu;
    @BindView(R.id.img_late)
    ImageView imgLate;
    @BindView(R.id.img_absence)
    ImageView imgAbsence;
    @BindView(R.id.tv_absence)
    TextView tvAbsence;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendnace);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_add})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_add:
                lyMenu.setVisibility(View.VISIBLE);
                break;
        }
    }
}
