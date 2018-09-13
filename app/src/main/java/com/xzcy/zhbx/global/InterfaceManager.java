package com.xzcy.zhbx.global;

import java.util.HashMap;

public class InterfaceManager {
    private static InterfaceManager manager;
    private static HashMap<String,String> urlManager=new HashMap<>();

    private static final String LOGIN="LOGIN";//登录

    public static InterfaceManager getInstance(){
        if (manager==null){
            manager=new InterfaceManager();
            urlManager.put(InterfaceManager.LOGIN,"http://www.baicu.com");
        }
        return manager;
    }

    public String getURL(String name){
        return urlManager.get(name);
    }
}
