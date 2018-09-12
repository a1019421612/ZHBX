package com.xzcy.zhbx.bean;

import java.lang.reflect.Field;
import java.io.Serializable;
import java.util.List;

public class InfoBean implements Serializable {

    public String msg;
    public String code;
    public Boolean success;
    public Data data;

    public class Data implements Serializable {

        public String UCPassword;
        public String count;
        public String updateTime;
        public String UC;
        public String modifyUser;
        public String password;
        public String loginTime;
        public String phone;
        public String createTime;
        public String name;
        public String createUser;
        public String id;
        public Boolean isDel;
        public String email;
        public String username;
        public String status;
        public List<Roles> roles;

        public class Roles implements Serializable {

            public String modifyUser;
            public String createTime;
            public String name;
            public String description;
            public String createUser;
            public String updateTime;
            public String id;
            public Boolean isDel;
            public String status;
            public List<Permissions> permissions;

            public class Permissions implements Serializable {

                public String parent;
                public String icon;
                public String updateTime;
                public int sort;
                public String title;
                public String type;
                public String url;
                public String modifyUser;
                public Boolean expand;
                public String component;
                public String createTime;
                public String name;
                public String createUser;
                public String id;
                public Boolean isDel;
                public String status;
                public
                List<Children> children;

                public class Children {
                }
            }
        }

        public Organize organize;

        public class Organize implements Serializable {

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