package com.superywd.library.restserver.http;

/**
 * Http请求响应封装
 * @author 迷宫的中心
 * @date 2019/4/13 18:09
 */
public interface HttpResponse {

    void setHeader(String name, String value);

    void addHeader(String name, String value);

    void sendRedirect(String location);

    void sendError(int sc, String msg);

    void sendError(int sc);

}
