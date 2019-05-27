package com.mozhumz.balance.enums;

/**
 * @author huyuanjia
 * @date 2019/5/6 20:53
 * 错误码
 */
public enum ErrorCode {
    LOGIN_ERR(10001,"账号或密码错误"),
    PARAM_ERR(10002,"参数错误"),
    ROLE_ERR(10003,"角色错误"),
    PRODUCT_NAME_ERR(20001,"该名称已使用"),
    CUSTOMER_ERR(20002,"该客户不存在或被删除"),
    CUSTOMER_BALANCE_ERR(20003,"该客户余额不足"),
    CUSTOMER_ADD_ERR(20004,"导入失败，请检查字段是否正确"),
    CUSTOMER_PHONE_ERR(20005,"该手机号已经在系统中"),

    ;
    public Integer code;
    public String desc;

    ErrorCode(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
