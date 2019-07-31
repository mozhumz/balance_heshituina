package com.mozhumz.balance.utils;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class EmailUtil {


    /**
     * 获取n位数字验证码
     * @param n
     * @return
     */
    public static String getEmailCode(int n){
        if(n<1){
            n=6;
        }
        return (int)((Math.random()*9+1)*(Math.pow(10,n-1)))+"";
    }



}
