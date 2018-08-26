package com.xzcy.zhbx.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xzcy.zhbx.R;
import com.xzcy.zhbx.activity.EditPswActivity;
import com.xzcy.zhbx.activity.PersonInfoActivity;
import com.xzcy.zhbx.view.ItemSettingView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        bind = ButterKnife.bind(this, view);
        return view;
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
                startActivity(new Intent(getActivity(),PersonInfoActivity.class));
                break;
            case R.id.mine_reset_psw:
                startActivity(new Intent(getActivity(),EditPswActivity.class));
                break;
            case R.id.mine_edit_phone:
                break;
            case R.id.mine_my_order:
                break;
        }
    }
}
