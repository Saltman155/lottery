package com.superywd.library.restserver.annotation;

import com.superywd.library.restserver.http.HttpMethod;

import java.lang.annotation.*;

/**
 * 请求路径及方法设置注解
 * @author 迷宫的中心
 * @date 2019/4/13 16:30
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
public @interface RequestMapping {

    String path() default "/";

    HttpMethod Method() default HttpMethod.DEFAULT;
}
