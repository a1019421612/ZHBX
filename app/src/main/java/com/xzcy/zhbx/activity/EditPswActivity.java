package com.xzcy.zhbx.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.xzcy.zhbx.R;
import com.xzcy.zhbx.global.Constant;
import com.xzcy.zhbx.utils.HandlerData;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class EditPswActivity extends BaseActivity {

    @BindView(R.id.et_old_psw)
    EditText etOldPsw;
    @BindView(R.id.et_new_psw)
    EditText etNewPsw;
    @BindView(R.id.et_enter_psw)
    EditText etEnterPsw;

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
        tvBaseSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPsw = etOldPsw.getText().toString().trim();
                String newPsw = etNewPsw.getText().toString().trim();
                String enterPsw = etEnterPsw.getText().toString().trim();
                if (TextUtils.isEmpty(oldPsw)) {
                    SmartToast.show("旧密码不能为空");
                    return;
                }
                if (TextUtils.isEmpty(newPsw)) {
                    SmartToast.show("新密码不能为空");
                    return;
                }
                if (TextUtils.isEmpty(enterPsw)) {
                    SmartToast.show("确认密码不能为空");
                    return;
                }
                if (!newPsw.equals(enterPsw)) {
                    SmartToast.show("密码不一致");
                    return;
                }
                editPassword(oldPsw, newPsw);
            }
        });
    }

    private void editPassword(String oldPsw, String newPsw) {
        OkHttpUtils
                .post()
                .url(Constant.MODIFYPASSWORD)
                .addHeader(Constant.ACCESSTOKEN, getToken())
                .addParams("password", oldPsw)
                .addParams("newPassword", newPsw)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        SmartToast.show("网络连接错误");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        HandlerData.requestIsSucess(EditPswActivity.this, response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            String msg = jsonObject.getString("msg");
                            String code = jsonObject.getString("code");
                            SmartToast.show(msg);
                            if (code.equals("200")) {
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_edit_psw;
    }

}
