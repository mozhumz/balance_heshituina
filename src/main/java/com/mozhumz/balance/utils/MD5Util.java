package com.mozhumz.balance.utils;

import com.hyj.util.param.CheckParamsUtil;
import org.springframework.util.DigestUtils;

/**
 * @author huyuanjia
 * @date 2019/2/24 16:24
 */
public class MD5Util {
    public static final String DEFAULT_KEY="Mozhumz_Xr_WangWei";
    private static final String PWD_KEY="Mozhumz_555_PWD_KEY";
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
        if(!CheckParamsUtil.check(text)){
            return null;
        }
        if (!CheckParamsUtil.check(key)) {
            return DigestUtils.md5DigestAsHex(text.getBytes());
        }
        //加密后的字符串
        return DigestUtils.md5DigestAsHex((text + key).getBytes());
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

    /**
     * 获取客户支付密码
     * @param pwd 前端密码
     * @return
     */
    public static String getBalancePwd(String pwd){
        return md5(pwd,BALANCE_KEY);
    }

    /**
     * 密码校验（登录密码 操作密码）
     * @param pwd0 前端密码
     * @param pwd1 数据库密码
     * @return
     */
    public static boolean checkPwd(String pwd0,String pwd1) {
        if (!CheckParamsUtil.check(pwd0,pwd1)) {
            return false;
        }
        return md5(pwd0, MD5Util.PWD_KEY).equals(pwd1);
    }

    /**
     * 默认登录/操作密码 123456
     * @return
     */
    public static String getDefaultPwd(){
        //147397e354ea6a1b25bcaa82c2692614
        return md5(md5("123456",DEFAULT_KEY),PWD_KEY);
    }


}
