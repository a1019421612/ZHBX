package com.xzcy.zhbx.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class HandleOrderActivity extends BaseActivity implements TakePhoto.TakeResultListener, InvokeListener {

    @BindView(R.id.tv_add_image)
    TextView tvAddImage;
    @BindView(R.id.gridView_image)
    GridView gridViewImage;
    private Dialog dialog;

    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    private Myadapter mMyadapter;
    private List<String> mList = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected String getTitleName() {
        return "处理工单";
    }

    @Override
    protected void initView() {
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
