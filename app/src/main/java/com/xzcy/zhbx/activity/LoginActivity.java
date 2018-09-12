package com.xzcy.zhbx.activity;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.xzcy.zhbx.MainActivity;
import com.xzcy.zhbx.R;
import com.xzcy.zhbx.global.Constant;
import com.xzcy.zhbx.utils.RegexUtils;
import com.xzcy.zhbx.utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

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
                    loginLC(mPhone,mPassword);
                }
                break;
            case R.id.forgetPasswordTextView:
                startActivity(new Intent(this,ForgetPasswordActivityActivity.class));
                break;
                default:
                    break;
        }
    }

    private void loginLC(String mPhone, String mPassword) {
        OkHttpUtils
                .post()
                .url(Constant.LOGIN)
                .addParams("username",mPhone)
                .addParams("password",mPassword)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        SmartToast.show("网络请求失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success){
                                String data = jsonObject.getString("data");
                                SPUtils.put(LoginActivity.this,Constant.ACCESSTOKEN,data);
                                SPUtils.put(LoginActivity.this,Constant.ISLOGIN,true);
                                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private boolean check() {
        passwordTextInputLayout.setErrorEnabled(false);
        phoneTextInputLayout.setErrorEnabled(false);

        mPhone = phoneEditText.getText().toString();
        mPassword = passwordEditText.getText().toString();

        if (TextUtils.isEmpty(mPhone)) {
            phoneTextInputLayout.setError("请输入用户名");
            return false;
        }
        if (TextUtils.isEmpty(mPassword)) {
            passwordTextInputLayout.setError("请输入密码");
            return false;
        }
        return true;
    }
}
