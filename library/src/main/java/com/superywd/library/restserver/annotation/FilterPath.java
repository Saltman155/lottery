package com.superywd.library.restserver.annotation;

import java.lang.annotation.*;

/**
 * 过滤器
 * @author 迷宫的中心
 * @date 2019/4/17 15:24
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface FilterPath {

    /**过滤器拦截路由*/
    String[] include() default {"/**"};
    /**过滤器例外路由*/
    String[] exclude() default {};
}
