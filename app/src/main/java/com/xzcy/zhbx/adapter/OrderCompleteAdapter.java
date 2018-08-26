package com.xzcy.zhbx.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xzcy.zhbx.R;
import com.xzcy.zhbx.bean.OrderCompleteBean;

import java.util.List;

public class OrderCompleteAdapter extends BaseQuickAdapter<OrderCompleteBean,BaseViewHolder> {
    public OrderCompleteAdapter(@Nullable List<OrderCompleteBean> data) {
        super(R.layout.order_complete_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderCompleteBean item) {

    }
}
