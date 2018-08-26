package com.xzcy.zhbx.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xzcy.zhbx.R;
import com.xzcy.zhbx.activity.OrderDetailActivity;
import com.xzcy.zhbx.adapter.OrderCompleteAdapter;
import com.xzcy.zhbx.bean.OrderCompleteBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 已完成
 */
public class OrderCompleteFragment extends Fragment {

    @BindView(R.id.rv_order_complete)
    RecyclerView rvOrderComplete;
    private Unbinder bind;

    private OrderCompleteAdapter adapter;
    private List<OrderCompleteBean> mList=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_complete, container, false);
        bind = ButterKnife.bind(this, view);
        LinearLayoutManager manager=new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvOrderComplete.setLayoutManager(manager);
        adapter=new OrderCompleteAdapter(mList);
        rvOrderComplete.setAdapter(adapter);
        for (int i = 0; i < 10; i++) {
            OrderCompleteBean orderCompleteBean=new OrderCompleteBean();
            orderCompleteBean.setName(i+"");
            mList.add(orderCompleteBean);
        }
        adapter.notifyDataSetChanged();
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(getActivity(),OrderDetailActivity.class));
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
    }
}
