package com.sudichina.ftwl.utils;

/**
 * Created by SudiChina-105 on 2016/9/9.
 * 接口封装类
 */
public class BaseApI  {
    public static String baseurl;
    public static String BASEURL(){
            baseurl = "http://192.168.1.44:8888/services/";
        return baseurl;
    }
}
