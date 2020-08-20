package com.balloon.springboot.enums.exception;

import com.balloon.core.exception.IBaseExceptionEnums;

public enum SysExceptionEnums implements IBaseExceptionEnums {

    USER_NOT_FOUND(30001,"没有查询到用户信息"),
    ROLE_NOT_MENU(30002,"角色没有配置菜单"),
            ;

    private final Integer code;

    private final String msg;


    SysExceptionEnums(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return null;
    }

    public String getMsg() {
        return null;
    }


}
