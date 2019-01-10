package com.xzcy.zhbx.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.coder.zzq.smartshow.toast.SmartToast;
import com.google.gson.Gson;
import com.xzcy.zhbx.R;
import com.xzcy.zhbx.adapter.FindAdapter;
import com.xzcy.zhbx.adapter.HomeAdapter;
import com.xzcy.zhbx.bean.HomeWorkBean;
import com.xzcy.zhbx.global.Constant;
import com.xzcy.zhbx.utils.HandlerData;
import com.xzcy.zhbx.utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class FindActivity extends AppCompatActivity {

    @BindView(R.id.iv_base_back)
    ImageView ivBaseBack;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    private List<HomeWorkBean.Data.Content> mList = new ArrayList<>();
    private FindAdapter adapter;
    private ArrayList<String> imageUrl = new ArrayList<>();
    private ImageView iv_msg;
    private String accessToken;
    private int mPage = 0;
    private int mSize = 10;
    private String keywork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        ButterKnife.bind(this);
        accessToken = (String) SPUtils.get(this, Constant.ACCESSTOKEN, "");
        //设置下拉颜色
        swipe.setColorSchemeColors(Color.rgb(47, 223, 189));
        LinearLayoutManager manager = new LinearLayoutManager(FindActivity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(manager);
        adapter = new FindAdapter(mList);
        recyclerview.setAdapter(adapter);

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPage = 0;
//                getStock(type,true);
                initData(keywork, true);
            }
        });
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPage++;
                initData(keywork, false);
            }
        }, recyclerview);
        ivBaseBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.tv_order_do:
                        SmartToast.show("处理");
                        break;
                    case R.id.tv_order_receiver:
                        SmartToast.show("接收");
                        break;
                }
            }
        });
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                int happening = mList.get(position).happening;
                if (happening==2){
                    startActivity(new Intent(FindActivity.this,OrderDetailActivity.class)
                            .putExtra("orderId",mList.get(position).id));
                }
            }
        });
    }

    @OnClick(R.id.ll_search)
    public void onViewClicked() {
        keywork = etSearch.getText().toString().trim();
        if (TextUtils.isEmpty(keywork)) {
            SmartToast.show("关键字不能为空");
            return;
        }
        initData(keywork, true);
    }

    private void initData(String word, final boolean refresh) {
        OkHttpUtils
                .get()
                .addHeader(Constant.ACCESSTOKEN, accessToken)
                .url(Constant.WORKORDER)
//                .addParams("happening","1")
//                .addParams("type","")
                .addParams("size", mSize + "")
                .addParams("title", word)
                .addParams("page", mPage + "")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        SmartToast.show("网络连接错误");
                        swipe.setRefreshing(false);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        HandlerData.requestIsSucess(FindActivity.this, response);
                        Log.e("sss", response);
                        adapter.loadMoreComplete();
                        swipe.setRefreshing(false);
                        HomeWorkBean homeWorkBean = new Gson().fromJson(response, HomeWorkBean.class);
                        Boolean success = homeWorkBean.success;
                        if (success) {
                            int total = homeWorkBean.data.totalElements;
                            List<HomeWorkBean.Data.Content> content = homeWorkBean.data.content;
                            if (refresh) {
                                mList.clear();
                            }
                            mList.addAll(content);
                            adapter.notifyDataSetChanged();
                            if (mList.size() == total) {
                                adapter.loadMoreEnd();
                            }
                        }
                    }
                });
    }
}
