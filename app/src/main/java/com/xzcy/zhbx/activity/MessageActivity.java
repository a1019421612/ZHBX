package com.xzcy.zhbx.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xzcy.zhbx.R;
import com.xzcy.zhbx.adapter.MessageAdapter;
import com.xzcy.zhbx.bean.MessageBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageActivity extends BaseActivity {

    @BindView(R.id.rv_message)
    RecyclerView rvMessage;
    private MessageAdapter adapter;
    private List<MessageBean> mList=new ArrayList<>();
    @Override
    protected void initData() {

    }

    @Override
    protected String getTitleName() {
        return "消息中心";
    }

    @Override
    protected void initView() {
        LinearLayoutManager manager=new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvMessage.setLayoutManager(manager);
        adapter=new MessageAdapter(mList);
        rvMessage.setAdapter(adapter);

        for (int i = 0; i < 10; i++) {
            MessageBean messageBean=new MessageBean();
            messageBean.setName(i+"");
            mList.add(messageBean);
        }
        adapter.notifyDataSetChanged();

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(MessageActivity.this,MessageDetailActivity.class));
            }
        });
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_message;
    }

}
