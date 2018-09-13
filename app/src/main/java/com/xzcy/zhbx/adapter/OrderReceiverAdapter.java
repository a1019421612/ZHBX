package com.xzcy.zhbx.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xzcy.zhbx.R;
import com.xzcy.zhbx.bean.HomeWorkBean;

import java.util.List;

public class OrderReceiverAdapter extends BaseQuickAdapter<HomeWorkBean.Data.Content,BaseViewHolder> {
    public OrderReceiverAdapter(@Nullable List<HomeWorkBean.Data.Content> data) {
        super(R.layout.order_receiver_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeWorkBean.Data.Content item) {
        int level = item.level;
        if (level==0){
            helper.setText(R.id.tv_order_level,"[一般]");
            helper.setTextColor(R.id.tv_order_level,Color.parseColor("#666666"));
        }else if (level==1){
            helper.setText(R.id.tv_order_level,"[紧急]");
            helper.setTextColor(R.id.tv_order_level, Color.parseColor("#ff0000"));
        }
        helper.setText(R.id.tv_order_content,item.content);
        helper.setText(R.id.tv_order_time,item.createTime);

        helper.addOnClickListener(R.id.tv_order_receiver);
    }
}
