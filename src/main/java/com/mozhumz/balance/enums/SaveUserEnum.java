package com.mozhumz.balance.enums;

public enum SaveUserEnum {
    add(1,"新增"),
    update(2,"修改"),
    ;
    public Integer code;
    public String desc;

    SaveUserEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
