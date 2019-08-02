package com.mozhumz.balance.constant;

/**
 * @author huyuanjia
 * @date 2019/5/7 9:09
 */
public class CommonConstant {
    public static  final String token="token";

    public static  final String balanceToken="balance-token";

    /**
     * 全局会话 根据token存储在redis
     */
    public static  final String globalSessionUser="globalSessionUser";

    /**
     * 局部会话
     */
    public static  final String sessionUser="sessionUser";

    /**
     * 客户验证码前缀
     */
    public static final String customerCode="customerCode";

    public static final String emailCodeTitle="何氏推拿-验证码";


}
