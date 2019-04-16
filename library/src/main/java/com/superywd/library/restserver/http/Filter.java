package com.superywd.library.restserver.http;

/**
 * http请求拦截器
 * @author 迷宫的中心
 * @date 2019/4/16 15:00
 */
public interface Filter {

    /**
     * 请求前拦截器
     * @param request       request对象
     * @param response      response对象
     * @return              是否允许通过
     */
    default boolean preFilter(HttpRequest request, HttpResponse response) {
        return true;
    }

    /**
     * 请求后拦截器
     * @param request       request对象
     * @param response      response对象
     */
    default void postFilter(HttpRequest request, HttpResponse response) {

    }


}
