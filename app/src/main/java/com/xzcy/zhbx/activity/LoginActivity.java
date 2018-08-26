package com.xzcy.zhbx.activity;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.xzcy.zhbx.R;
import com.xzcy.zhbx.utils.RegexUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.phoneEditText)
    EditText phoneEditText;
    @BindView(R.id.phoneTextInputLayout)
    TextInputLayout phoneTextInputLayout;
    @BindView(R.id.passwordEditText)
    EditText passwordEditText;
    @BindView(R.id.passwordTextInputLayout)
    TextInputLayout passwordTextInputLayout;
    @BindView(R.id.forgetPasswordTextView)
    TextView forgetPasswordTextView;
    private String mPhone;
    private String mPassword;
    @Override
    protected void initData() {

    }

    @Override
    protected String getTitleName() {
        return "登录";
    }

    @Override
    protected void initView() {
        titleBarLL.setVisibility(View.GONE);
        ivBaseBack.setVisibility(View.INVISIBLE);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_login;
    }
    @OnClick({R.id.tv_login, R.id.forgetPasswordTextView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                if (check()){
//                    loginLC(mPhone,mPassword);
                }
                break;
            case R.id.forgetPasswordTextView:
                startActivity(new Intent(this,ForgetPasswordActivityActivity.class));
                break;
                default:
                    break;
        }
    }
    private boolean check() {
        passwordTextInputLayout.setErrorEnabled(false);
        phoneTextInputLayout.setErrorEnabled(false);

        mPhone = phoneEditText.getText().toString();
        mPassword = passwordEditText.getText().toString();

        if (TextUtils.isEmpty(mPhone)) {
            phoneTextInputLayout.setError("请输入手机号");
            return false;
        } else if (!RegexUtils.isMobile(mPhone)) {
            phoneTextInputLayout.setError("请输入有效的手机号");
            return false;
        }
        if (TextUtils.isEmpty(mPassword)) {
            passwordTextInputLayout.setError("请输入密码");
            return false;
        }
        return true;
    }
}
