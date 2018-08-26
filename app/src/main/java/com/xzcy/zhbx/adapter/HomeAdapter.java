package com.xzcy.zhbx.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xzcy.zhbx.R;
import com.xzcy.zhbx.bean.HomeBean;

import java.util.List;

public class HomeAdapter extends BaseQuickAdapter<HomeBean,BaseViewHolder> {
    public HomeAdapter( @Nullable List<HomeBean> data) {
        super(R.layout.home_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeBean item) {

    }
}
