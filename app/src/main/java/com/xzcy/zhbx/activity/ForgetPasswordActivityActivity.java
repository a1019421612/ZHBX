package com.xzcy.zhbx.activity;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xzcy.zhbx.R;
import com.xzcy.zhbx.utils.RegexUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class ForgetPasswordActivityActivity extends BaseActivity {
    @BindView(R.id.phoneEditText)
    EditText phoneEditText;
    @BindView(R.id.phoneTextInputLayout)
    TextInputLayout phoneTextInputLayout;
    @BindView(R.id.codeEditText)
    EditText codeEditText;
    @BindView(R.id.codeTextInputLayout)
    TextInputLayout codeTextInputLayout;
    @BindView(R.id.sendCodeButton)
    Button sendCodeButton;
    @BindView(R.id.passwordEditText)
    EditText passwordEditText;
    @BindView(R.id.passwordTextInputLayout)
    TextInputLayout passwordTextInputLayout;
    @BindView(R.id.saveButton)
    TextView saveButton;

    private String mPhone;
    private String mCode;
    private String mPassword;
    private String mData;
    private TimeCount timeCount;
    @Override
    protected void initData() {

    }

    @Override
    protected String getTitleName() {
        return "重置密码";
    }

    @Override
    protected void initView() {
    ivBaseBack.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    });
    }
    @OnClick({ R.id.sendCodeButton, R.id.saveButton})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sendCodeButton:
                if (checkPhone()){
                    timeCount = new TimeCount(60000,1000);
                    timeCount.start();
//                    getVailCode(mPhone);
                }
                break;
            case R.id.saveButton:
                if (check()){
//                    forgetPsw(mPhone,mCode,mPassword);
                }
                break;
                default:
                    break;        }
    }
    @Override
    protected int getLayoutID() {
        return R.layout.activity_forget_password_activity;
    }
    private boolean check() {
        codeTextInputLayout.setErrorEnabled(false);

        if (!checkPhone()) return false;

        mCode = codeEditText.getText().toString();
        if (TextUtils.isEmpty(mCode)) {
            codeTextInputLayout.setError("请输入验证码");
            return false;
        }
        passwordTextInputLayout.setErrorEnabled(false);
        mPassword=passwordEditText.getText().toString();
        if (TextUtils.isEmpty(mPassword)){
            passwordTextInputLayout.setError("请输入密码");
            return false;
        }
        return true;
    }
    private boolean checkPhone() {
        phoneTextInputLayout.setErrorEnabled(false);

        mPhone = phoneEditText.getText().toString();
        if (TextUtils.isEmpty(mPhone)) {
            phoneTextInputLayout.setError("请输入手机号");
            return false;
        } else if (!RegexUtils.isMobile(mPhone)) {
            phoneTextInputLayout.setError("请输入有效的手机号");
            return false;
        }
        return true;
    }
    class TimeCount extends CountDownTimer {

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            try {
                sendCodeButton.setEnabled(false);
                sendCodeButton.setBackgroundColor(Color.TRANSPARENT);
                sendCodeButton.setText(millisUntilFinished / 1000 + "秒");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFinish() {
            try {
                sendCodeButton.setEnabled(true);
                sendCodeButton.setText("");
                sendCodeButton.setBackgroundResource(R.mipmap.ic_code);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
