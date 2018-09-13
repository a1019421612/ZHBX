package com.xzcy.zhbx.utils;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import com.google.gson.Gson;
import com.xzcy.zhbx.MyApplication;
import com.xzcy.zhbx.activity.LoginActivity;
import com.xzcy.zhbx.bean.BaseBean;

import org.json.JSONException;
import org.json.JSONObject;

public class HandlerData {

    public static void requestIsSucess(Activity activity, String data){
        String resultjson="";
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(data);
            int code = jsonObject.getInt("code");
            if (code==403){
                activity.startActivity(new Intent(activity,LoginActivity.class));
                MyApplication.finishAllActivity();
                return;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
