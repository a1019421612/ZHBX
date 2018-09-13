package com.xzcy.zhbx.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.coder.zzq.smartshow.toast.SmartToast;
import com.google.gson.Gson;
import com.xzcy.zhbx.R;
import com.xzcy.zhbx.bean.OrderDetailBean;
import com.xzcy.zhbx.global.Constant;
import com.xzcy.zhbx.utils.HandlerData;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class OrderDetailActivity extends BaseActivity {


    @BindView(R.id.tv_order_title)
    TextView tvOrderTitle;
    @BindView(R.id.tv_order_time)
    TextView tvOrderTime;
    @BindView(R.id.tv_pd_name)
    TextView tvPdName;
    @BindView(R.id.tv_pd_org)
    TextView tvPdOrg;
    @BindView(R.id.tv_pd_phone)
    TextView tvPdPhone;
    @BindView(R.id.tv_jd_name)
    TextView tvJdName;
    @BindView(R.id.tv_jd_org)
    TextView tvJdOrg;
    @BindView(R.id.tv_jd_phone)
    TextView tvJdPhone;
    @BindView(R.id.tv_kh_name)
    TextView tvKhName;
    @BindView(R.id.tv_kh_org)
    TextView tvKhOrg;
    @BindView(R.id.tv_kh_phone)
    TextView tvKhPhone;
    @BindView(R.id.tv_order_num)
    TextView tvOrderNum;
    @BindView(R.id.tv_order_type)
    TextView tvOrderType;
    @BindView(R.id.tv_order_level)
    TextView tvOrderLevel;
    @BindView(R.id.tv_subject)
    TextView tvSubject;
    @BindView(R.id.tv_homework_content)
    TextView tvHomeworkContent;
    @BindView(R.id.tv_work_content)
    TextView tvWorkContent;
    @BindView(R.id.tv_use_device)
    TextView tvUseDevice;
    @BindView(R.id.tv_person)
    TextView tvPerson;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.gridView_image)
    GridView gridViewImage;
    private String orderId = "";
    private Myadapter mMyadapter;
    private List<String> mList = new ArrayList<>();

    @Override
    protected void initData() {
        orderId = getIntent().getStringExtra("orderId");
        orderDetail(orderId);
    }

    @Override
    protected String getTitleName() {
        return "工单详情";
    }

    @Override
    protected void initView() {
        mMyadapter = new Myadapter();
        gridViewImage.setAdapter(mMyadapter);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_order_detail;
    }

    private void orderDetail(String orderId) {
        OkHttpUtils
                .get()
                .url(Constant.ORDERDETAIL + "/" + orderId)
                .addHeader(Constant.ACCESSTOKEN, getToken())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        SmartToast.show("网络连接错误");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        HandlerData.requestIsSucess(OrderDetailActivity.this, response);
                        OrderDetailBean orderDetailBean = new Gson().fromJson(response, OrderDetailBean.class);

                        tvOrderTitle.setText(orderDetailBean.data.title);
                        tvOrderTime.setText(orderDetailBean.data.createTime);

                        tvPdName.setText("派单人：" + orderDetailBean.data.publishUserInfo.name);
                        tvPdOrg.setText("部门：" + orderDetailBean.data.publishUserInfo.organize.name);
                        tvPdPhone.setText("联系方式：" + orderDetailBean.data.publishUserInfo.phone);

                        tvJdName.setText("接单人：" + orderDetailBean.data.receiveUserInfo.name);
                        tvJdOrg.setText("部门：" + orderDetailBean.data.receiveUserInfo.organize.name);
                        tvJdPhone.setText("联系方式：" + orderDetailBean.data.receiveUserInfo.phone);

                        tvKhName.setText("客户：" + orderDetailBean.data.clientName);
                        tvKhOrg.setText("部门：" + orderDetailBean.data.clientDept);
                        tvKhPhone.setText("联系方式：" + orderDetailBean.data.clientPhone);

                        tvOrderNum.setText("单号：" + orderDetailBean.data.code);
                        tvOrderType.setText("工单类型：" + orderDetailBean.data.type.name);
                        int level = orderDetailBean.data.level;
                        if (level == 0) {
                            tvOrderLevel.setText("工单级别：一般");
                        } else if (level == 1) {
                            tvOrderLevel.setText("工单级别：紧急");
                        }


                        tvSubject.setText("主题：" + orderDetailBean.data.title);
                        tvHomeworkContent.setText("作业内容：" + orderDetailBean.data.content);
                        tvWorkContent.setText("工作内容：" + orderDetailBean.data.jobContent);
                        tvUseDevice.setText("使用设备：" + orderDetailBean.data.tool);

                        tvTime.setText("到场时间：" + orderDetailBean.data.disposeTime);
                    }
                });

    }
    class Myadapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int i) {
            return mList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            view = LayoutInflater.from(OrderDetailActivity.this).inflate(R.layout.publish_image_item, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.gridview_item);
                Glide.with(OrderDetailActivity.this).load(mList.get(i)).into(imageView);

            return view;
        }
    }
}
