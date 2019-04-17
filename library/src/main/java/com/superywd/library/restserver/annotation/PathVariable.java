package com.superywd.library.restserver.annotation;

import java.lang.annotation.*;

/**
 * url路由参数接收注解
 * @author 迷宫的中心
 * @date 2019/4/17 14:00
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
public @interface PathVariable {

    /**形参名称*/
    String name();

}
