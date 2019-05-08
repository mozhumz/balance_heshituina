package com.mozhumz.balance.anno;

import java.lang.annotation.*;

/**
 * @author huyuanjia
 * @date 2018/12/19 15:47
 * 登录检查
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SkipLoginCheckAnno {
}
