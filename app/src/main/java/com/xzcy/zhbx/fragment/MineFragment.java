package com.xzcy.zhbx.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.google.gson.Gson;
import com.xzcy.zhbx.R;
import com.xzcy.zhbx.activity.EditPhoneActivity;
import com.xzcy.zhbx.activity.EditPswActivity;
import com.xzcy.zhbx.activity.PersonInfoActivity;
import com.xzcy.zhbx.bean.InfoBean;
import com.xzcy.zhbx.global.Constant;
import com.xzcy.zhbx.utils.SPUtils;
import com.xzcy.zhbx.view.ItemSettingView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;

import static com.xzcy.zhbx.utils.HandlerData.handlerData;

public class MineFragment extends Fragment {
    @BindView(R.id.mine_info)
    ItemSettingView mineInfo;
    @BindView(R.id.mine_reset_psw)
    ItemSettingView mineResetPsw;
    @BindView(R.id.mine_edit_phone)
    ItemSettingView mineEditPhone;
    @BindView(R.id.mine_my_order)
    ItemSettingView mineMyOrder;
    private Unbinder bind;
    private String accessToken;
    private String phone;
    private String name;
    private String organizeName;
    private String organizeId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        bind = ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        accessToken = (String) SPUtils.get(getActivity(), Constant.ACCESSTOKEN, "");
        OkHttpUtils
                .get()
                .url(Constant.USERINFO)
                .addHeader("accessToken", accessToken)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        SmartToast.show("网络连接错误");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        handlerData(getActivity(), response);
                        InfoBean infoBean = new Gson().fromJson(response, InfoBean.class);
                        Boolean success = infoBean.success;
                        if (success) {
                            phone = infoBean.data.phone;
                            name = infoBean.data.name;
                            organizeName = infoBean.data.organize.name;
                            organizeId = infoBean.data.organize.id;
                            mineEditPhone.setDescName(phone);
                        }
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
    }

    @OnClick({R.id.mine_info, R.id.mine_reset_psw, R.id.mine_edit_phone, R.id.mine_my_order})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mine_info:
                startActivity(new Intent(getActivity(), PersonInfoActivity.class)
                        .putExtra("phone", phone)
                        .putExtra("name", name)
                        .putExtra("organizeName", organizeName)
                        .putExtra("organizeId", organizeId));
                break;
            case R.id.mine_reset_psw:
                startActivity(new Intent(getActivity(), EditPswActivity.class));
                break;
            case R.id.mine_edit_phone:
                startActivity(new Intent(getActivity(), EditPhoneActivity.class));
                break;
            case R.id.mine_my_order:
                break;
        }
    }
}
