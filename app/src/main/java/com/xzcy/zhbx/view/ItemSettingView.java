package com.xzcy.zhbx.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.xzcy.zhbx.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */

public class ItemSettingView extends RelativeLayout {

    @BindView(R.id.iv_icon_item)
    ImageView ivIconItem;
    @BindView(R.id.tv_title_item)
    TextView tvTitleItem;
    @BindView(R.id.tv_news_item)
    TextView tvNewsItem;

    public ItemSettingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.ItemSettingView);
        if (attributes != null) {
            try {
                //处理titleBar背景色
                int titleBarBackGround = attributes.getResourceId(R.styleable.ItemSettingView_background_color, Color.WHITE);
                setBackgroundResource(titleBarBackGround);
            } catch (Exception e) {
            }
            //处理icon图片
            int leftDrawable = attributes.getResourceId(R.styleable.ItemSettingView_left_drawable, -1);
            if (leftDrawable != -1) {
                ivIconItem.setBackgroundResource(leftDrawable);
            } else {
                ivIconItem.setVisibility(GONE);
                tvTitleItem.setPadding(40, 0, 0, 0);
            }
            //处理title文字
            String titleText = attributes.getString(R.styleable.ItemSettingView_title_text);
            if (!TextUtils.isEmpty(titleText)) {
                tvTitleItem.setText(titleText);
            }
            //获取标题显示颜色
            int titleTextColor = attributes.getColor(R.styleable.ItemSettingView_title_text_color, Color.parseColor("#8c8c8c"));
            tvTitleItem.setTextColor(titleTextColor);

            //设置dec_text是否隐藏
            boolean leftButtonVisible = attributes.getBoolean(R.styleable.ItemSettingView_dec_visible, true);
            if (leftButtonVisible) {
                tvNewsItem.setVisibility(View.VISIBLE);
            } else {
                tvNewsItem.setVisibility(View.INVISIBLE);
            }

            //判断是否显示dec_text
            int titleTextDrawable = attributes.getResourceId(R.styleable.ItemSettingView_dec_text_drawable, -1);
            if (titleTextDrawable != -1) {
                tvNewsItem.setBackgroundResource(titleTextDrawable);
            }

            String decText = attributes.getString(R.styleable.ItemSettingView_dec_text);
            if (!TextUtils.isEmpty(decText)) {
                tvNewsItem.setText(decText);
            }

            //获取desc显示颜色
            int decTextColor = attributes.getColor(R.styleable.ItemSettingView_dec_text_color, Color.parseColor("#8c8c8c"));
            tvNewsItem.setTextColor(decTextColor);

            attributes.recycle();
        }
    }

    private void init() {
        View view = View.inflate(getContext(), R.layout.mine_setting_item, this);//先有孩子，再去找爹，喜当爹
        ButterKnife.bind(this, view);
    }

    public void setTitleName(String title){
        tvTitleItem.setText(title);
    }

    public void setDescName(String news){
        tvNewsItem.setText(news);
    }

    public String getDescString(){
        return tvNewsItem.getText().toString().trim();
    }
}
