package com.xzcy.zhbx.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.xzcy.zhbx.R;

public class EditPswActivity extends BaseActivity {

    @Override
    protected void initData() {

    }

    @Override
    protected String getTitleName() {
        return "修改密码";
    }

    @Override
    protected void initView() {
        tvBaseSave.setText("完成");
        tvBaseSave.setVisibility(View.VISIBLE);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_edit_psw;
    }
}
