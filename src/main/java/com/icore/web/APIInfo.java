package com.icore.web;

public class APIInfo {

    private APIInfo(){

    }

    public static class User{
        private User(){

        }

        public static final String USER_INFO = "用户相关接口";

        public static class ApiName{
            private ApiName(){

            }
            public static final String USER_ADD = "增加用户接口";
            public static final String USER_UPDATE = "修改用户接口";
            public static final String USER_DELETE = "删除用户接口";
            public static final String USER_GET = "获取用户接口";
            public static final String USER_GETLIST = "查询用户列表接口";


        }

    }


}
