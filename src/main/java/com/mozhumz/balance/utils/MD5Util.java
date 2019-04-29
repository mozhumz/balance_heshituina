package com.mozhumz.balance.utils;

import com.hyj.util.param.CheckParamsUtil;
import org.springframework.util.DigestUtils;

/**
 * @author huyuanjia
 * @date 2019/2/24 16:24
 */
public class MD5Util {
    public static final String DEFAULT_KEY="Mozhumz_Xr_WangWei";

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
    public static String getDefaultPwd(){
        return md5("123456",DEFAULT_KEY);
    }


}
