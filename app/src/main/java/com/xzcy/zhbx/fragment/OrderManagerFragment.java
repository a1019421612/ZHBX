package com.xzcy.zhbx.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.xzcy.zhbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 工单管理
 */
public class OrderManagerFragment extends Fragment {
    @BindView(R.id.rb_zixuan)
    RadioButton rbZixuan;
    @BindView(R.id.rb_movie)
    RadioButton rbMovie;
    @BindView(R.id.rb_sports)
    RadioButton rbSports;
    @BindView(R.id.rg_group)
    RadioGroup rgGroup;
    @BindView(R.id.fragment_movie)
    FrameLayout fragmentMovie;
    private Unbinder bind;

    private OrderWaitReceiveFragment orderWaitReceiveFragment;//待接收
    private OrderWaitdoFragment orderWaitdoFragment;//待处理
    private OrderCompleteFragment orderCompleteFragment;//已完成
    private FragmentManager fragmentManager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ordermanager, container, false);
        bind = ButterKnife.bind(this, view);
        fragmentManager = getFragmentManager();
//        getQutotaionInfo();
        initFGroupClick();
        return view;
    }
    private void initFGroupClick() {
        rgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                hide(transaction);
                switch (checkedId) {
                    //自选
                    case R.id.rb_zixuan:
                        if (orderWaitReceiveFragment != null) {
                            transaction.show(orderWaitReceiveFragment);
                        } else {
                            orderWaitReceiveFragment = new OrderWaitReceiveFragment();
                            transaction.add(R.id.fragment_movie, orderWaitReceiveFragment);
                        }
                        break;
                    case R.id.rb_movie:
                        //影视
                        if (orderWaitdoFragment != null) {
                            transaction.show(orderWaitdoFragment);
                        } else {
                            orderWaitdoFragment = new OrderWaitdoFragment();
//                            Bundle bundle = new Bundle();
//                            bundle.putString("TYPE",id1);
//                            orderWaitdoFragment.setArguments(bundle);
                            transaction.add(R.id.fragment_movie, orderWaitdoFragment);
                        }
                        break;
                    case R.id.rb_sports:
                        //体育明星
                        if (orderCompleteFragment != null) {
                            transaction.show(orderCompleteFragment);
                        } else {
                            orderCompleteFragment = new OrderCompleteFragment();
//                            Bundle bundle = new Bundle();
//                            bundle.putString("TYPE",id2);
//                            orderCompleteFragment.setArguments(bundle);
                            transaction.add(R.id.fragment_movie, orderCompleteFragment);
                        }
                        break;
                        default:
                            break;
                }
                transaction.commit();
            }
        });

        rbZixuan.setChecked(true);
    }
    private void hide(FragmentTransaction transaction) {
        if (orderWaitReceiveFragment != null) {
            transaction.hide(orderWaitReceiveFragment);
        }
        if (orderWaitdoFragment != null) {
            transaction.hide(orderWaitdoFragment);
        }
        if (orderCompleteFragment != null) {
            transaction.hide(orderCompleteFragment);
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
    }
}
