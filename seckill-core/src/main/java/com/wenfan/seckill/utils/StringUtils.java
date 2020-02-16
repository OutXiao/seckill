package com.wenfan.seckill.utils;

/**
 * Created by wenfan on 2020/1/25 18:42
 */
public class StringUtils {

    public static boolean isAnEmpty(String s){
        if (s != null && s.length() !=0)
            return false;
        else
            return true;
    }

    /**
     *  判断所有参数是否为空，有一个为空则返回true，
     * @param s
     * @return
     */
    public static boolean isAllEmptys(String... s){
        for (int i = 0; i< s.length;++i){
            if (isAnEmpty(s[i])){
                return true;
            }
        }
        return false;
    }


    /*public static void main(String [] args){
        *//*System.out.println(isAnEmpty(""));
        System.out.println(isAnEmpty("1"));
        System.out.println(isAllEmptys("",""));
        System.out.println(isAllEmptys("1","2","23"));
        System.out.println(isAllEmptys("","123"));*//*
        System.out.println(0&1);
    }*/

}
