package com.xzcy.zhbx;

import android.util.Log;

import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public abstract class MyStringCallback extends StringCallback {
    @Override
    public void onError(Call call, Exception e, int id) {
        Log.w("失败:", e.toString());
        onMyonError(call, e, id);

//        if (e.toString().contains("401")) {
//            Intent login = new Intent(WuyueApplication.mContext, LoginActivity.class);
//            login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            WuyueApplication.mContext.startActivity(login);
//        }
    }

    @Override
    public void onResponse(String response, int id) {
        Log.w("成功:", response);
        onMyResponse(response, id);
    }

    public abstract void onMyonError(Call call, Exception e, int id);

    public abstract void onMyResponse(String response, int id);
}
