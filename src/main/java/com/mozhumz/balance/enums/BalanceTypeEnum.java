package com.mozhumz.balance.enums;

/**
 * @author huyuanjia
 * @date 2019/5/27 15:04
 */
public enum BalanceTypeEnum {
    consume(1,"消费"),
    charge(2,"充值"),

    ;
    public Integer code;
    public String desc;

    BalanceTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
