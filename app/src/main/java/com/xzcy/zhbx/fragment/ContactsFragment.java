package com.xzcy.zhbx.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ListView;
import android.widget.TextView;

import com.xzcy.zhbx.R;
import com.xzcy.zhbx.adapter.FriendListAdapter;
import com.xzcy.zhbx.bean.Friend;
import com.xzcy.zhbx.utils.CharacterParser;
import com.xzcy.zhbx.utils.PinyinComparator;
import com.xzcy.zhbx.view.SideBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ContactsFragment extends Fragment{
    private ListView mListView;
    private SideBar mSidBar;
    /**
     * 中部展示的字母提示
     */
    private TextView mDialogTextView;

    private List<Friend> mFriendList;

    private PinyinComparator mPinyinComparator;
    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser mCharacterParser;
    /**
     * 好友列表的 mFriendListAdapter
     */
    private FriendListAdapter mFriendListAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        initView(view);
        initData();
        return view;
    }
    private void initData() {
//        mFriendList=new ArrayList<>();
////        FriendListAdapter adapter=new FriendListAdapter(getActivity(),mFriendList);
////        mListView.setAdapter(adapter);
//        //实例化汉字转拼音类
//        mCharacterParser = CharacterParser.getInstance();
//        mPinyinComparator=PinyinComparator.getInstance();
//
//        List<Friend> friends=new ArrayList<>();
//        friends.add(new Friend(){{setName("张三");}});
//        friends.add(new Friend(){{setName("张三");}});
//        friends.add(new Friend(){{setName("张三");}});
//        friends.add(new Friend(){{setName("李四");}});
//        friends.add(new Friend(){{setName("李四");}});
//        friends.add(new Friend(){{setName("李四");}});
//        friends.add(new Friend(){{setName("王五");}});
//        friends.add(new Friend(){{setName("王五");}});
//        friends.add(new Friend(){{setName("王五");}});
//        friends.add(new Friend(){{setName("安六");}});
//        friends.add(new Friend(){{setName("安六");}});
//        friends.add(new Friend(){{setName("安六");}});
//        friends.add(new Friend(){{setName("包包");}});
//        friends.add(new Friend(){{setName("包包");}});
//        friends.add(new Friend(){{setName("包包");}});
//        friends.add(new Friend(){{setName("112");}});
//        mFriendList.addAll(filledData(friends));
//        Collections.sort(mFriendList,mPinyinComparator);
//        mFriendListAdapter=new FriendListAdapter(getActivity(),mFriendList);
//        mListView.setAdapter(mFriendListAdapter);
    }
    private List<Friend> filledData(List<Friend> date){
        List<Friend> mSortList = new ArrayList<Friend>();

        for(int i=0; i<date.size(); i++){
            //汉字转换成拼音
            String pinyin = mCharacterParser.getSelling(date.get(i).getName());
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if(sortString.matches("[A-Z]")){
                date.get(i).setLetters(sortString.toUpperCase());
            }else{
                date.get(i).setLetters("#");
            }

            mSortList.add(date.get(i));
        }
        return mSortList;

    }
    private void initView(View view) {
        WebView webView=view.findViewById(R.id.webView);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLoadWithOverviewMode(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setSaveFormData(false);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.loadUrl("file:///android_asset/test.html");
//        mSidBar=view.findViewById(R.id.sidrbar);
//        mListView=view.findViewById(R.id.listview);
//        mDialogTextView=view.findViewById(R.id.group_dialog);
//        mSidBar.setTextView(mDialogTextView);
//        mSidBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
//            @Override
//            public void onTouchingLetterChanged(String s) {
//                int position=mFriendListAdapter.getPositionForSection(s.charAt(0));
//                if (position!=-1){
//                    mListView.setSelection(position);
//                }
//            }
//        });
    }
}
