package com.xzcy.zhbx.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;

import com.xzcy.zhbx.MainActivity;
import com.xzcy.zhbx.R;
import com.xzcy.zhbx.utils.SPUtils;

public class WelcomeActivity extends AppCompatActivity {
    private boolean isLogin;
    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 100:
                    startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
                    finish();
                    break;
                case 200:
                    startActivity(new Intent(WelcomeActivity.this,LoginActivity.class));
                    finish();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);
        isLogin= (Boolean) SPUtils.get(this,"isLogin",false);
        if (isLogin){
            handler.sendEmptyMessageDelayed(100,3000);
        }else {
            handler.sendEmptyMessageDelayed(200,3000);
        }
    }
}
