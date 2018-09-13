package com.xzcy.zhbx.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.coder.zzq.smartshow.toast.SmartToast;
import com.google.gson.Gson;
import com.xzcy.zhbx.R;
import com.xzcy.zhbx.activity.HandleOrderActivity;
import com.xzcy.zhbx.adapter.OrderWaitDoAdapter;
import com.xzcy.zhbx.bean.HomeWorkBean;
import com.xzcy.zhbx.bean.OrderWaitDoBean;
import com.xzcy.zhbx.global.Constant;
import com.xzcy.zhbx.utils.HandlerData;
import com.xzcy.zhbx.utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * 待处理
 */
public class OrderWaitdoFragment extends Fragment {
    @BindView(R.id.rv_order_waitdo)
    RecyclerView rvOrderWaitdo;
    Unbinder unbinder;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    private OrderWaitDoAdapter adapter;
    private List<HomeWorkBean.Data.Content> mList=new ArrayList<>();
    private String accessToken;
    private int mPage = 0;
    private int mSize = 10;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_wait_do, container, false);
        unbinder = ButterKnife.bind(this, view);
        accessToken = (String) SPUtils.get(getActivity(), Constant.ACCESSTOKEN, "");
        swipe.setColorSchemeColors(Color.rgb(47, 223, 189));
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvOrderWaitdo.setLayoutManager(manager);
        adapter = new OrderWaitDoAdapter(mList);
        rvOrderWaitdo.setAdapter(adapter);

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(getActivity(),HandleOrderActivity.class)
                        .putExtra("orderId",mList.get(position).id));
            }
        });
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPage=0;
//                getStock(type,true);
                initData(true);
            }
        });
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPage++;
                initData(false);
            }
        },rvOrderWaitdo);
        initData(true);
        return view;
    }
    private void initData(final boolean refresh) {
        OkHttpUtils
                .get()
                .addHeader(Constant.ACCESSTOKEN,accessToken)
                .url(Constant.WORKORDER)
                .addParams("happening","1")
//                .addParams("type","")
                .addParams("size",mSize+"")
//                .addParams("title","")
                .addParams("page",mPage+"")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        SmartToast.show("网络连接错误");
                        swipe.setRefreshing(false);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        HandlerData.requestIsSucess(getActivity(),response);
                        Log.e("sss",response);
                        adapter.loadMoreComplete();
                        swipe.setRefreshing(false);
                        HomeWorkBean homeWorkBean = new Gson().fromJson(response, HomeWorkBean.class);
                        Boolean success = homeWorkBean.success;
                        if (success){
                            int total = homeWorkBean.data.total;
                            List<HomeWorkBean.Data.Content> content = homeWorkBean.data.content;
                            if (refresh){
                                mList.clear();
                            }
                            mList.addAll(content);
                            adapter.notifyDataSetChanged();
                            if (mList.size()==total){
                                adapter.loadMoreEnd();
                            }
                        }
                    }
                });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        initData(true);
    }
}
