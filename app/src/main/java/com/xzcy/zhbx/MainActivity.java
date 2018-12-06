package com.xzcy.zhbx;

import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xzcy.zhbx.fragment.CheckManagerFragment;
import com.xzcy.zhbx.fragment.ContactsFragment;
import com.xzcy.zhbx.fragment.HomeFragment;
import com.xzcy.zhbx.fragment.MineFragment;
import com.xzcy.zhbx.fragment.OrderManagerFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mImageChats, mImageContact, mImageFind, mImageMe,mImageContacts;
    private TextView mTextChats, mTextContact, mTextFind, mTextMe,mTextContacts;
    private FrameLayout frameLayout;
    private FragmentManager fragmentManager;

    private HomeFragment homeFragment;
    private OrderManagerFragment orderManagerFragment;
    private CheckManagerFragment checkManagerFragment;
    private MineFragment mineFragment;
    private ContactsFragment contactsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        initStatusBar();
        initView();
        initData();
        fragmentManager = getSupportFragmentManager();
        changeTextViewColor();
        changeSelectedTabState(0);
        showFragment(0);
    }

    private void initData() {
    }

    private void initView() {
        frameLayout = findViewById(R.id.fl_container);
        RelativeLayout chatRLayout = (RelativeLayout) findViewById(R.id.seal_chat);
        RelativeLayout contactRLayout = (RelativeLayout) findViewById(R.id.seal_contact_list);
        RelativeLayout foundRLayout = (RelativeLayout) findViewById(R.id.seal_find);
        RelativeLayout mineRLayout = (RelativeLayout) findViewById(R.id.seal_me);
        RelativeLayout contatcsRLayout=findViewById(R.id.seal_contacts);
        mImageChats = (ImageView) findViewById(R.id.tab_img_chats);
        mImageContact = (ImageView) findViewById(R.id.tab_img_contact);
        mImageFind = (ImageView) findViewById(R.id.tab_img_find);
        mImageMe = (ImageView) findViewById(R.id.tab_img_me);
        mImageContacts=findViewById(R.id.tab_img_contacts);
        mTextChats = (TextView) findViewById(R.id.tab_text_chats);
        mTextContact = (TextView) findViewById(R.id.tab_text_contact);
        mTextFind = (TextView) findViewById(R.id.tab_text_find);
        mTextMe = (TextView) findViewById(R.id.tab_text_me);
        mTextContacts=findViewById(R.id.tab_text_contacts);

        chatRLayout.setOnClickListener(this);
        contactRLayout.setOnClickListener(this);
        foundRLayout.setOnClickListener(this);
        mineRLayout.setOnClickListener(this);
        contatcsRLayout.setOnClickListener(this);
    }
    private void showFragment(int i) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hide(transaction);
        switch (i) {
            case 0:
                if (homeFragment!=null){
                    transaction.show(homeFragment);
                }else {
                    homeFragment=new HomeFragment();
                    transaction.add(R.id.fl_container,homeFragment);
                }
                break;
            case 1:
                if (orderManagerFragment!=null){
                    transaction.show(orderManagerFragment);
                }else {
                    orderManagerFragment=new OrderManagerFragment();
                    transaction.add(R.id.fl_container,orderManagerFragment);
                }
                break;
            case 2:
                if (checkManagerFragment!=null){
                    transaction.show(checkManagerFragment);
                }else {
                    checkManagerFragment=new CheckManagerFragment();
                    transaction.add(R.id.fl_container,checkManagerFragment);
                }
                break;
            case 3:
                if (mineFragment!=null){
                    transaction.show(mineFragment);
                }else {
                    mineFragment=new MineFragment();
                    transaction.add(R.id.fl_container,mineFragment);
                }
                break;
            case 4:
                if (contactsFragment!=null){
                    transaction.show(contactsFragment);
                }else {
                    contactsFragment=new ContactsFragment();
                    transaction.add(R.id.fl_container,contactsFragment);
                }
                break;
        }
        transaction.commit();
    }
    private void hide(FragmentTransaction transaction) {

        if (homeFragment!=null){
            transaction.hide(homeFragment);
        }
        if (orderManagerFragment!=null){
            transaction.hide(orderManagerFragment);
        }
        if (checkManagerFragment!=null){
            transaction.hide(checkManagerFragment);
        }
        if (mineFragment!=null){
            transaction.hide(mineFragment);
        }
        if (contactsFragment!=null){
            transaction.hide(contactsFragment);
        }
    }
    private void changeTextViewColor() {
        mImageChats.setBackgroundDrawable(getResources().getDrawable(R.mipmap.nav_home));
        mImageContact.setBackgroundDrawable(getResources().getDrawable(R.mipmap.nav_xm));
        mImageFind.setBackgroundDrawable(getResources().getDrawable(R.mipmap.nav_hq));
        mImageMe.setBackgroundDrawable(getResources().getDrawable(R.mipmap.nav_user));
        mImageContacts.setBackgroundDrawable(getResources().getDrawable(R.mipmap.nav_user));
        mTextChats.setTextColor(Color.parseColor("#abadbb"));
        mTextContact.setTextColor(Color.parseColor("#abadbb"));
        mTextFind.setTextColor(Color.parseColor("#abadbb"));
        mTextMe.setTextColor(Color.parseColor("#abadbb"));
        mTextContacts.setTextColor(Color.parseColor("#abadbb"));
    }
    private void changeSelectedTabState(int position) {
        switch (position) {
            case 0:
                mTextChats.setTextColor(Color.parseColor("#0099ff"));
                mImageChats.setBackgroundDrawable(getResources().getDrawable(R.mipmap.nav_home2));
                break;
            case 1:
                mTextContact.setTextColor(Color.parseColor("#0099ff"));
                mImageContact.setBackgroundDrawable(getResources().getDrawable(R.mipmap.nav_xm2));
                break;
            case 2:
                mTextFind.setTextColor(Color.parseColor("#0099ff"));
                mImageFind.setBackgroundDrawable(getResources().getDrawable(R.mipmap.nav_hq2));
                break;
            case 3:
                mTextMe.setTextColor(Color.parseColor("#0099ff"));
                mImageMe.setBackgroundDrawable(getResources().getDrawable(R.mipmap.nav_user2));
                break;
            case 4:
                mTextContacts.setTextColor(Color.parseColor("#0099ff"));
                mImageContacts.setBackgroundDrawable(getResources().getDrawable(R.mipmap.nav_user2));
                break;
        }
    }

    @Override
    public void onClick(View v) {
        changeTextViewColor();
        switch (v.getId()) {
            case R.id.seal_chat:
                changeSelectedTabState(0);
                showFragment(0);
                break;
            case R.id.seal_contact_list:
                changeSelectedTabState(1);
                showFragment(1);
                break;
            case R.id.seal_find:
                changeSelectedTabState(2);
                showFragment(2);
                break;
            case R.id.seal_me:
                changeSelectedTabState(3);
                showFragment(3);
                break;
            case R.id.seal_contacts:
                changeSelectedTabState(4);
                showFragment(4);
                break;
        }
    }
}
