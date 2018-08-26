package com.xzcy.zhbx.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xzcy.zhbx.R;
import com.xzcy.zhbx.bean.HomeBean;
import com.xzcy.zhbx.bean.MessageBean;

import java.util.List;

public class MessageAdapter extends BaseQuickAdapter<MessageBean,BaseViewHolder> {
    public MessageAdapter(@Nullable List<MessageBean> data) {
        super(R.layout.message_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageBean item) {

    }
}
