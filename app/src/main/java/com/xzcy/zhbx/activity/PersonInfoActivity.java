package com.xzcy.zhbx.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.google.gson.Gson;
import com.xzcy.zhbx.R;
import com.xzcy.zhbx.bean.OrganizeListBean;
import com.xzcy.zhbx.global.Constant;
import com.xzcy.zhbx.utils.HandlerData;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class PersonInfoActivity extends BaseActivity {

    @BindView(R.id.et_info_name)
    EditText etInfoName;
    @BindView(R.id.et_info_phone)
    EditText etInfoPhone;
    @BindView(R.id.tv_info_organize)
    TextView tvInfoOrganize;
    private String phone;
    private String name;
    private String organizeName;
    private List<OrganizeListBean.Data.Children> children;
    private String organizeId;

    @Override
    protected void initData() {
        phone = getIntent().getStringExtra("phone");
        name = getIntent().getStringExtra("name");
        organizeName = getIntent().getStringExtra("organizeName");
        organizeId = getIntent().getStringExtra("organizeId");

        etInfoName.setText(name);
        etInfoPhone.setText(phone);
        tvInfoOrganize.setText(organizeName);
        organizeList();
    }

    private void organizeList() {
        OkHttpUtils
                .get()
                .url(Constant.ORGANIZELIST)
                .addHeader(Constant.ACCESSTOKEN,getToken())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        SmartToast.show("网络连接错误");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        HandlerData.requestIsSucess(PersonInfoActivity.this,response);
                        OrganizeListBean organizeListBean = new Gson().fromJson(response, OrganizeListBean.class);
                        if (organizeListBean.success){
                            children = organizeListBean.data.get(0).children;

                        }
                    }
                });
    }

    @Override
    protected String getTitleName() {
        return "个人信息";
    }

    @Override
    protected void initView() {
        tvBaseSave.setVisibility(View.VISIBLE);
        tvBaseSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editInfo();
            }
        });
    }

    private void editInfo() {
        OkHttpUtils
                .post()
                .addHeader(Constant.ACCESSTOKEN,getToken())
                .url(Constant.EDITINFO)
                .addParams("phone",phone)
                .addParams("organize",organizeId)
                .addParams("name",name)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        SmartToast.show("网络连接错误");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        HandlerData.requestIsSucess(PersonInfoActivity.this,response);
                    }
                });

    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_person_info;
    }


    @OnClick(R.id.tv_info_organize)
    public void onViewClicked() {
        if (children==null){
            return;
        }
        final String[] array_organize=new String[children.size()];
        String[] array_organizeId=new String[children.size()];
        for (int i = 0; i < children.size(); i++) {
            array_organize[i]=children.get(i).name;
            array_organizeId[i]=children.get(i).id;
        }
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setItems(array_organize, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String name = array_organize[i];
                tvInfoOrganize.setText(name);
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
}
