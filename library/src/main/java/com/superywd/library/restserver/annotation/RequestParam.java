package com.superywd.library.restserver.annotation;

import java.lang.annotation.*;

/**
 * url显式参数接收注解
 * @author 迷宫的中心
 * @date 2019/4/17 13:54
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface RequestParam {

    /**形参名称*/
    String name();
    /**形参默认值*/
    String defaultValue() default "";
    /**是否是必须的*/
    boolean required() default true;

}
