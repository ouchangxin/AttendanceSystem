package com.xinshen.attendancesystem.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xinshen.attendancesystem.Global;
import com.xinshen.attendancesystem.R;
import com.xinshen.attendancesystem.util.SPUtils;
import com.xinshen.attendancesystem.util.ToastUtil;
import com.xinshen.sdk.Iface.CreateSetCallBack;
import com.xinshen.sdk.Iface.GetFaceSetCallBack;
import com.xinshen.sdk.entity.CreateRespond;
import com.xinshen.sdk.entity.ErrorRespond;
import com.xinshen.sdk.entity.GetFaceSetRespond;
import com.xinshen.sdk.face.FaceSetManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by thinkpad on 2017/11/28.
 */

public class EnterActivity extends Activity {

    @BindView(R.id.tv_cut)
    TextView tv_cut;
    @BindView(R.id.edit_company)
    EditText edit_company;
    @BindView(R.id.btn_enter)
    Button btn_enter;
    private List<GetFaceSetRespond.FacesetsBean> sets;
    private static final String REG = "[^&^@,=*'\"]+";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);
        setFinishOnTouchOutside(false);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getSetInfo();
    }

    @OnClick(R.id.btn_enter)
    public void onClick(View view){
        if (sets == null){
            ToastUtil.showShort(EnterActivity.this,"获取集合信息失败");
            getSetInfo();
            return;
        }
        final String company = edit_company.getText().toString();
        if (!company.matches(REG)){
            edit_company.setText("");
            tv_cut.setText("不得包含非法字符！！");
            tv_cut.setVisibility(View.VISIBLE);
            return;
        }
        for (GetFaceSetRespond.FacesetsBean  bean : sets ){
            if (bean.getOuter_id().equals(company)) {
                edit_company.setText("");
                tv_cut.setText("该公司已注册！！");
                tv_cut.setVisibility(View.VISIBLE);
                return;
            }
        }
        tv_cut.setVisibility(View.GONE);
        FaceSetManager.newInstance().createSet(company, new CreateSetCallBack() {
            @Override
            public void onRespond(CreateRespond respond) {
                Global.Variable.COMPANY_NAME = company;
                SPUtils.put(EnterActivity.this,Global.Const.KEY_COMPANY_NAME,company);
                setResult(RESULT_OK);
                finish();
            }
            @Override
            public void onError(ErrorRespond error) {
                ToastUtil.showShort(EnterActivity.this, "集合创建错误\n"+error.getError_message());
            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_CANCELED);
    }

    private void getSetInfo() {
        FaceSetManager.newInstance().getFaceSet(new GetFaceSetCallBack() {
            @Override
            public void onRespond(GetFaceSetRespond respond) {
                sets = respond.getFacesets();
            }
            @Override
            public void onError(ErrorRespond error) {
                ToastUtil.showShort(EnterActivity.this,error.getError_message());
            }
        });
    }
}
