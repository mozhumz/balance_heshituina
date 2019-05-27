package com.mozhumz.balance.utils;

import com.hyj.util.param.CheckParamsUtil;
import org.springframework.util.DigestUtils;

/**
 * @author huyuanjia
 * @date 2019/2/24 16:24
 */
public class MD5Util {
    public static final String DEFAULT_KEY="Mozhumz_Xr_WangWei";
    public static final String BALANCE_KEY="Mozhumz_Balance_610_WangWei";

    /**
     * MD5方法
     *
     * @param text 明文
     * @param key  密钥
     * @return 密文
     * @throws Exception
     */
    public static String md5(String text, String key) {
        if (!CheckParamsUtil.check(key)) {
            return DigestUtils.md5DigestAsHex(text.getBytes());
        }
        //加密后的字符串
        return DigestUtils.md5DigestAsHex((text + key).getBytes()).toString();
    }

    /**
     * 默认密码 123456
     * @return
     */
    public static String getDefaultBalancePwd(){
        return md5(md5("123456",DEFAULT_KEY),BALANCE_KEY);
    }

    /**
     * 客户支付密码校验
     * @param pwd1
     * @param sqlPwd
     * @return
     */
    public static boolean checkBalancePwd(String pwd1,String sqlPwd){
        if(!CheckParamsUtil.check(pwd1,sqlPwd)){
            return false;
        }
        return (md5(pwd1,BALANCE_KEY).equals(sqlPwd));
    }



}
