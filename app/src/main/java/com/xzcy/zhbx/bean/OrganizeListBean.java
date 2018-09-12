package com.xzcy.zhbx.bean;

import java.lang.reflect.Field;
import java.io.Serializable;
import java.util.List;

public class OrganizeListBean implements Serializable {

    public String msg;
    public String code;
    public Boolean success;
    public List<Data> data;

    public class Data implements Serializable {

        public String parent;
        public String modifyUser;
        public String createTime;
        public String name;
        public String createUser;
        public String updateTime;
        public String id;
        public Boolean isDel;
        public String status;
        public List<Children> children;

        public class Children implements Serializable {

            public String parent;
            public String modifyUser;
            public String children;
            public String createTime;
            public String name;
            public String createUser;
            public String updateTime;
            public String id;
            public Boolean isDel;
            public String status;
        }
    }
}