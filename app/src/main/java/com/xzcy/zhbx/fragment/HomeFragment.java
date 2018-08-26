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
import android.widget.ImageView;
import android.widget.Toast;

import com.xzcy.zhbx.R;
import com.xzcy.zhbx.activity.MessageActivity;
import com.xzcy.zhbx.adapter.HomeAdapter;
import com.xzcy.zhbx.bean.HomeBean;
import com.xzcy.zhbx.utils.PicUtils;
import com.xzcy.zhbx.utils.StringUtil;
import com.xzcy.zhbx.view.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView rv_home;
    private CustomViewPager viewpager;

    private List<HomeBean> mList=new ArrayList<>();
    private HomeAdapter adapter;
    private ArrayList<String> imageUrl=new ArrayList<>();
    private ImageView iv_msg;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home, container, false);

        imageUrl.add("http://api.abcky.site:81/wuyue/api/img/show?id=5b6b0b6cd06bee5473987669");
        imageUrl.add("http://api.abcky.site:81/wuyue/api/img/show?id=5b6b0b6cd06bee5473987669");
        imageUrl.add("http://api.abcky.site:81/wuyue/api/img/show?id=5b6b0b6cd06bee5473987669");
        rv_home=view.findViewById(R.id.rv_home);
        LinearLayoutManager manager=new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_home.setLayoutManager(manager);
        adapter=new HomeAdapter(mList);
        rv_home.setAdapter(adapter);
        adapter.addHeaderView(getHeaderView());
        viewpager.setImageResources(imageUrl, mAdCycleViewListener);
        for (int i = 0; i < 10; i++) {
            HomeBean homeBean=new HomeBean();
            homeBean.setName(i+"");
            mList.add(homeBean);
        }
        adapter.notifyDataSetChanged();
        return view;
    }
    private CustomViewPager.ImageCycleViewListener mAdCycleViewListener = new CustomViewPager.ImageCycleViewListener() {
        @Override
        public void onImageClick(int position, View imageView) {
            // TODO 单击图片处理事件
            int curPos = viewpager.getCurPos();
            String url = imageUrl.get(curPos);
            Toast.makeText(getActivity(),url,Toast.LENGTH_SHORT).show();
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
        iv_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),MessageActivity.class));
            }
        });
        return view;
    }
}
