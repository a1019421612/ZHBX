package com.xzcy.zhbx.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.xzcy.zhbx.R;

public class PersonInfoActivity extends BaseActivity {

    @Override
    protected void initData() {

    }

    @Override
    protected String getTitleName() {
        return "个人信息";
    }

    @Override
    protected void initView() {
        tvBaseSave.setVisibility(View.VISIBLE);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_person_info;
    }
}
