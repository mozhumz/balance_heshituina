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
    LOGIN_EXP_ERR(10005,"登录失效，请重新登录"),
    USER_NOT_EXIST_ERR(10006,"操作员工不存在或已经被删除"),
    BALANCE_ROLE_NOT_EXIST_ERR(10007,"系统扣款角色不存在，请联系管理员添加"),
    SEND_EMAIL_ERR(10008,"发送邮件出错:"),

    PRODUCT_NAME_ERR(20001,"该名称已使用"),
    CUSTOMER_ERR(20002,"该客户不存在或被删除"),
    CUSTOMER_BALANCE_ERR(20003,"该客户余额不足"),
    CUSTOMER_ADD_ERR(20004,"导入失败，请检查字段是否正确"),
    CUSTOMER_PHONE_ERR(20005,"该手机号已经在系统中"),
    CUSTOMER_MONEY_ERR(20006,"该客户余额为负数"),
    CUSTOMER_PWD_ERR(20007,"客户密码错误"),
    CUSTOMER_PWD0_ERR(20008,"客户密码为初始密码，请让客户修改"),
    DOUSER_PWD_ERR(20009,"操作员工密码错误"),
    DOUSER_RIGHT_ERR(20010,"操作员工权限不足"),
    ADMIN_RIGHT_ERR(20011,"admin账号不能添加消费"),
    DOUSER_PWD0_ERR(20012,"操作员工登录密码或操作密码为初始密码，请修改后操作"),




    ;
    public Integer code;
    public String desc;

    ErrorCode(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
