package com.xzcy.zhbx.utils;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import com.google.gson.Gson;
import com.xzcy.zhbx.MyApplication;
import com.xzcy.zhbx.activity.LoginActivity;
import com.xzcy.zhbx.bean.BaseBean;

public class HandlerData {
    public static void handlerData(Activity activity, String data){
        BaseBean baseBean = new Gson().fromJson(data, BaseBean.class);
        if (baseBean.code==403){
            activity.startActivity(new Intent(activity,LoginActivity.class));
            MyApplication.finishAllActivity();
            return;
        }
    }
}
