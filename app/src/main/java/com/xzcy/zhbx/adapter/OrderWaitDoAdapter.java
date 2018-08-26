package com.xzcy.zhbx.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xzcy.zhbx.R;
import com.xzcy.zhbx.bean.OrderCompleteBean;
import com.xzcy.zhbx.bean.OrderWaitDoBean;

import java.util.List;

public class OrderWaitDoAdapter extends BaseQuickAdapter<OrderWaitDoBean,BaseViewHolder> {
    public OrderWaitDoAdapter(@Nullable List<OrderWaitDoBean> data) {
        super(R.layout.order_waitdo_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderWaitDoBean item) {

    }
}
