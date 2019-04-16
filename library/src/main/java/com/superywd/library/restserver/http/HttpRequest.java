package com.superywd.library.restserver.http;

import java.util.Map;

/**
 * http请求封装
 * @author 迷宫的中心
 * @date 2019/4/13 18:08
 */
public interface HttpRequest {

    /**获取请求全路径*/
    String getRequestURI();
    /**获取请求方法*/
    HttpMethod getRequestMethod();
    /**按照key值获取请求参数*/
    String getParameter(String name);
    /**获取请求参数列表*/
    String[] getParameters(String name);
    /**获取请求端ip*/
    String getRemoteAddr();
    /**获取请求头*/
    String getHeader(String name);
    /**获取请求头集合*/
    String[] getHeaders(String name);
}
