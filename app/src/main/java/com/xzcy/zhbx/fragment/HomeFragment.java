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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.coder.zzq.smartshow.toast.SmartToast;
import com.google.gson.Gson;
import com.xzcy.zhbx.R;
import com.xzcy.zhbx.activity.FindActivity;
import com.xzcy.zhbx.activity.HandleOrderActivity;
import com.xzcy.zhbx.activity.MessageActivity;
import com.xzcy.zhbx.adapter.HomeAdapter;
import com.xzcy.zhbx.bean.HomeBean;
import com.xzcy.zhbx.bean.HomeWorkBean;
import com.xzcy.zhbx.global.Constant;
import com.xzcy.zhbx.utils.HandlerData;
import com.xzcy.zhbx.utils.PicUtils;
import com.xzcy.zhbx.utils.SPUtils;
import com.xzcy.zhbx.utils.StringUtil;
import com.xzcy.zhbx.view.CustomViewPager;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class HomeFragment extends Fragment {
    private RecyclerView rv_home;
    private CustomViewPager viewpager;
    private SwipeRefreshLayout swipe;

    private List<HomeWorkBean.Data.Content> mList = new ArrayList<>();
    private HomeAdapter adapter;
    private ArrayList<String> imageUrl = new ArrayList<>();
    private ImageView iv_msg;
    private String accessToken;
    private int mPage = 0;
    private int mSize = 10;
    private EditText ed_search;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        accessToken = (String) SPUtils.get(getActivity(), Constant.ACCESSTOKEN, "");
        imageUrl.add("http://www.wuyueapp.com/wuyueTest//api/img/show?id=5b694a0b00be4526acf029da");
        imageUrl.add("http://www.wuyueapp.com/wuyueTest/api/img/show?id=5b6949ff00be4526acf029d8");
        imageUrl.add("http://www.wuyueapp.com/wuyueTest/api/img/show?id=5b69499a00be4526acf029d4");
        rv_home = view.findViewById(R.id.rv_home);
        swipe = view.findViewById(R.id.swipe);
        //设置下拉颜色
        swipe.setColorSchemeColors(Color.rgb(47, 223, 189));
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_home.setLayoutManager(manager);
        adapter = new HomeAdapter(mList);
        rv_home.setAdapter(adapter);
        adapter.addHeaderView(getHeaderView());
        viewpager.setImageResources(imageUrl, mAdCycleViewListener);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPage = 0;
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
        }, rv_home);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(getActivity(), HandleOrderActivity.class)
                        .putExtra("orderId",mList.get(position).id));
            }
        });
        initData(true);

        return view;
    }

    private void initData(final boolean refresh) {
        OkHttpUtils
                .get()
                .addHeader(Constant.ACCESSTOKEN, accessToken)
                .url(Constant.WORKORDER)
                .addParams("happening", "1")
//                .addParams("type","")
                .addParams("size", mSize + "")
//                .addParams("title","")
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
                        HandlerData.requestIsSucess(getActivity(), response);
                        Log.e("sss", response);
                        adapter.loadMoreComplete();
                        swipe.setRefreshing(false);
                        HomeWorkBean homeWorkBean = new Gson().fromJson(response, HomeWorkBean.class);
                        Boolean success = homeWorkBean.success;
                        if (success) {
                            int total = homeWorkBean.data.total;
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

    private CustomViewPager.ImageCycleViewListener mAdCycleViewListener = new CustomViewPager.ImageCycleViewListener() {
        @Override
        public void onImageClick(int position, View imageView) {
            // TODO 单击图片处理事件
            int curPos = viewpager.getCurPos();
            String url = imageUrl.get(curPos);
            Toast.makeText(getActivity(), url, Toast.LENGTH_SHORT).show();
            if (StringUtil.isBlank(url)) {
                return;
            }
        }

        @Override
        public void displayImage(String imageURL, ImageView imageView) {
            PicUtils.showImgRoundedNoDiskCache(getActivity(), imageView, imageURL);
        }
    };

    private View getHeaderView() {
        View view = getLayoutInflater().inflate(R.layout.home_header, (ViewGroup) rv_home.getParent(), false);
        viewpager = view.findViewById(R.id.viewpager);
        iv_msg = view.findViewById(R.id.img_msg);
        ed_search = view.findViewById(R.id.ed_search);
        iv_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MessageActivity.class));
            }
        });
        ed_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), FindActivity.class));
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData(true);
    }
}
