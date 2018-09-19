package com.xzcy.zhbx.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.bumptech.glide.Glide;
import com.coder.zzq.smartshow.toast.SmartToast;
import com.google.gson.Gson;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.xzcy.zhbx.R;
import com.xzcy.zhbx.bean.OrderDetailBean;
import com.xzcy.zhbx.global.Constant;
import com.xzcy.zhbx.utils.HandlerData;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class HandleOrderActivity extends BaseActivity implements TakePhoto.TakeResultListener, InvokeListener {

    @BindView(R.id.tv_add_image)
    TextView tvAddImage;
    @BindView(R.id.gridView_image)
    GridView gridViewImage;
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
    @BindView(R.id.et_work_content)
    EditText etWorkContent;
    @BindView(R.id.et_use_device)
    EditText etUseDevice;
    @BindView(R.id.et_exception)
    EditText etException;
    @BindView(R.id.tv_person)
    TextView tvPerson;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    private Dialog dialog;

    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    private Myadapter mMyadapter;
    private List<String> mList = new ArrayList<>();
    private TimePickerView pvTime;
    private String orderId = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initData() {
        orderId = getIntent().getStringExtra("orderId");
        orderDetail(orderId);
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
                        HandlerData.requestIsSucess(HandleOrderActivity.this, response);
                        OrderDetailBean orderDetailBean = new Gson().fromJson(response, OrderDetailBean.class);
                        if (orderDetailBean.data==null){
                            return;
                        }
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

//                        tvTime.setText("到场时间：" + orderDetailBean.data.disposeTime);
                    }
                });

    }
    @Override
    protected String getTitleName() {
        return "处理工单";
    }

    @Override
    protected void initView() {
        ivBaseBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initTimePicker();

        mMyadapter = new Myadapter();
        gridViewImage.setAdapter(mMyadapter);
        gridViewImage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == mList.size()) {
                    String[] items = {"相册", "拍照"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(HandleOrderActivity.this);
                    builder.setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            switch (i) {
                                case 0:
                                    //ToastUtils.showShort(PublishActivity.this,"相册");
                                    //takePhoto = getTakePhoto();
                                    int size = mList.size();
                                    takePhoto.onPickMultiple(4 - size);
                                    break;
                                case 1:
                                    //ToastUtils.showShort(PublishActivity.this,"拍照");
                                    File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
                                    if (!file.getParentFile().exists())
                                        file.getParentFile().mkdirs();
                                    Uri imageUri = Uri.fromFile(file);
                                    //takePhoto = getTakePhoto();
                                    takePhoto.onPickFromCapture(imageUri);
                                    break;
                            }
                        }
                    });
                    builder.show();
                }
            }
        });
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_handle_order;
    }

    private void showDialog() {
        View view = getLayoutInflater().inflate(R.layout.dialog_choose_photo, null);
        view.findViewById(R.id.bt_tuku_dialog).setOnClickListener(listener);
        view.findViewById(R.id.bt_paizhao_dialig).setOnClickListener(listener);
        view.findViewById(R.id.bt_quxiao_dialog).setOnClickListener(listener);
        if (dialog == null) {
            dialog = new Dialog(this, R.style.transparentFrameWindowStyle);
        }
        dialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = getWindowManager().getDefaultDisplay().getHeight();
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        // 设置显示位置
        dialog.onWindowAttributesChanged(wl);
        // 设置点击外围解散
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bt_tuku_dialog:
                    startTuKu();
                    break;
                case R.id.bt_paizhao_dialig:
                    startCapture();
                    break;
                case R.id.bt_quxiao_dialog:
                    dialog.dismiss();
                    break;

            }
        }
    };

    private void startTuKu() {
        takePhoto.onPickMultiple(1);
    }

    private void startCapture() {
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        Uri imageUri = Uri.fromFile(file);
        takePhoto.onPickFromCapture(imageUri);
    }

    private CropOptions getCropOptions() {
        CropOptions.Builder builder = new CropOptions.Builder();
        builder.setAspectX(800).setAspectY(800);
        builder.setWithOwnCrop(false);
        return builder.create();
    }

    //使用第三方获取照片
    private TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return takePhoto;
    }

    @Override
    public void takeSuccess(TResult result) {
        ArrayList<TImage> images = result.getImages();
        for (int i = 0; i < images.size(); i++) {
            String originalPath = images.get(i).getOriginalPath();
            //System.out.println("originalPath::::::" + originalPath);
            mList.add(originalPath);
        }
        mMyadapter.notifyDataSetChanged();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        Log.e("SSS", "fail" + msg);
    }

    @Override
    public void takeCancel() {

    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick({R.id.tv_time, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_time:
                pvTime.show();
                break;
            case R.id.tv_submit:
                final String workContent = etWorkContent.getText().toString().trim();
                final String useDevice = etUseDevice.getText().toString().trim();
                final String exception = etException.getText().toString().trim();
                final String time = tvTime.getText().toString().trim();
                if (TextUtils.isEmpty(workContent)){
                    SmartToast.show("工作内容不能为空");
                    return;
                }
                if (TextUtils.isEmpty(useDevice)){
                    SmartToast.show("使用设备材料不能为空");
                    return;
                }
                if (TextUtils.isEmpty(exception)){
                    SmartToast.show("异常情况不能为空");
                    return;
                }
                if (TextUtils.isEmpty(time)){
                    SmartToast.show("操作时间不能为空");
                    return;
                }
//                submitOrder(workContent,useDevice,exception,time);
                if (mList.size()==0){
                    SmartToast.show("图片不能为空");
                    return;
                }

                String array_name="";
                PostFormBuilder post = OkHttpUtils.post();
                for (int i = 0; i < mList.size(); i++) {
                    File file = new File(mList.get(i));
                    String name = file.getName();
                    post.addFile("files",name,file);
                    if (i==0){
                        array_name=name;
                    }else {
                        array_name=array_name+","+name;
                    }
                }
                post.url(Constant.UPLOADIMAGES).addHeader(Constant.ACCESSTOKEN,getToken()).addParams("files",array_name).build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                SmartToast.show("网络连接错误");
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                HandlerData.requestIsSucess(HandleOrderActivity.this,response);
                                try {
                                    JSONObject jsonObject=new JSONObject(response);
                                    boolean success = jsonObject.getBoolean("success");
                                    if (success){
                                        JSONArray data = jsonObject.getJSONArray("data");
                                        String array_name="";
                                        for (int i = 0; i < data.length(); i++) {
                                            String name = (String) data.get(i);
                                            if (i==0){
                                                array_name=name;
                                            }else {
                                                array_name=array_name+","+name;
                                            }
                                        }
                                        submitOrder(workContent,useDevice,exception,time,array_name);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                default:
                    break;
        }
    }

    private void submitOrder(String workContent, String useDevice, String exception,String time,String files) {
        OkHttpUtils
                .post()
                .url(Constant.RECEIVERORDER)
                .addHeader(Constant.ACCESSTOKEN,getToken())
                .addParams("id",orderId)
                .addParams("happening",2+"")
                .addParams("files",files)
                .addParams("jobContent",workContent)
                .addParams("abnormal",exception)
                .addParams("tool",useDevice)
                .addParams("disposeTime",time)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        SmartToast.show("网络连接错误");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        HandlerData.requestIsSucess(HandleOrderActivity.this,response);
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            String msg = jsonObject.getString("msg");
                            SmartToast.show(msg);
                            Intent intent=new Intent();
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void initTimePicker() {
        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
//                Toast.makeText(HandleOrderActivity.this, getTime(date), Toast.LENGTH_SHORT).show();
                Log.i("pvTime", "onTimeSelect");
                tvTime.setText(getTime(date));

            }
        })
                .setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
                    @Override
                    public void onTimeSelectChanged(Date date) {
                        Log.i("pvTime", "onTimeSelectChanged");
                    }
                })
                .setType(new boolean[]{true, true, true, true, true, true})
                .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .build();

        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
            }
        }
//        Calendar selectedDate = Calendar.getInstance();
//        Calendar startDate = Calendar.getInstance();
//        //startDate.set(2013,1,1);
//        Calendar endDate = Calendar.getInstance();
//        //endDate.set(2020,1,1);
//
//        //正确设置方式 原因：注意事项有说明
//        startDate.set(2013,0,1);
//        endDate.set(2020,11,31);
//
//        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
//            @Override
//            public void onTimeSelect(Date date, View v) {//选中事件回调
//                tvTime.setText(getTime(date));
//            }
//        })
//                .setType(new boolean[]{true, true, true, true, true, true})// 默认全部显示
//                .setCancelText("Cancel")//取消按钮文字
//                .setSubmitText("Sure")//确认按钮文字
//                .setContentTextSize(18)
//                .setTitleSize(20)//标题文字大小
//                .setTitleText("Title")//标题文字
//                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
//                .isCyclic(true)//是否循环滚动
//                .setTitleColor(Color.BLACK)//标题文字颜色
//                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
//                .setCancelColor(Color.BLUE)//取消按钮文字颜色
//                .setTitleBgColor(0xFF666666)//标题背景颜色 Night mode
//                .setBgColor(0xFF333333)//滚轮背景颜色 Night mode
//                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
//                .setRangDate(startDate,endDate)//起始终止年月日设定
//                .setLabel("年","月","日","时","分","秒")//默认设置为年月日时分秒
//                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                .isDialog(true)//是否显示为对话框样式
//                .build();
    }
    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }
    class Myadapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mList.size() + 1 == 5 ? 4 : mList.size() + 1;
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

            view = LayoutInflater.from(HandleOrderActivity.this).inflate(R.layout.publish_image_item, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.gridview_item);
            if (mList.size() == i) {
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                Glide.with(HandleOrderActivity.this).load(R.mipmap.icon_addpic_focused).into(imageView);
                if (i == 4) {
                    imageView.setVisibility(View.GONE);
                }
            } else {
                Glide.with(HandleOrderActivity.this).load(mList.get(i)).into(imageView);
            }

            return view;
        }
    }
}
