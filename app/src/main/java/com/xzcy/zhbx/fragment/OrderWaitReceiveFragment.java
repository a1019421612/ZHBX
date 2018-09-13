package com.xzcy.zhbx.fragment;

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
import com.xzcy.zhbx.adapter.HomeAdapter;
import com.xzcy.zhbx.adapter.OrderReceiverAdapter;
import com.xzcy.zhbx.bean.HomeWorkBean;
import com.xzcy.zhbx.global.Constant;
import com.xzcy.zhbx.utils.HandlerData;
import com.xzcy.zhbx.utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * 待接收
 */
public class OrderWaitReceiveFragment extends Fragment {
    private SwipeRefreshLayout swipe;
    private RecyclerView recyclerView;

    private OrderReceiverAdapter adapter;
    private List<HomeWorkBean.Data.Content> mList=new ArrayList<>();
    private String accessToken;
    private int mPage = 0;
    private int mSize = 10;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_order_wait_receive,container,false);
        swipe=view.findViewById(R.id.swipe_receive);
        recyclerView=view.findViewById(R.id.rv_receive);
        accessToken = (String) SPUtils.get(getActivity(), Constant.ACCESSTOKEN, "");
        swipe.setColorSchemeColors(Color.rgb(47, 223, 189));
        LinearLayoutManager manager=new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter=new OrderReceiverAdapter(mList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                String id = mList.get(position).id;
                receiverOrder(id,position);
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
        },recyclerView);
        initData(true);
        return view;
    }

    private void receiverOrder(String Id, final int pos) {
        OkHttpUtils
                .post()
                .url(Constant.RECEIVERORDER)
                .addHeader(Constant.ACCESSTOKEN,accessToken)
                .addParams("id",Id)
                .addParams("happening","1")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        SmartToast.show("网络连接错误");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        HandlerData.requestIsSucess(getActivity(),response);
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success){
                                SmartToast.show("接收成功");
                                mList.remove(pos);
                                adapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void initData(final boolean refresh) {
        OkHttpUtils
                .get()
                .addHeader(Constant.ACCESSTOKEN,accessToken)
                .url(Constant.WORKORDER)
                .addParams("happening","0")
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
}
