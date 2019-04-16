package com.superywd.library.restserver.http;

/**
 * Http请求方式
 * @author 迷宫的中心
 * @date 2019/4/13 16:36
 */
public enum HttpMethod {

    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE"),
    HEAD("HEAD"),
    OPTIONS("OPTIONS"),
    DEFAULT("DEFAULT");

    String value;

    HttpMethod(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }


    public static HttpMethod getInstance(String method){

        switch (method.toUpperCase()){
            case "GET" : return GET;
            case "POST" : return POST;
            case "PUT" : return PUT;
            case "DELETE" : return DELETE;
            case "HEAD" : return HEAD;
            case "OPTIONS" : return OPTIONS;
            default: return DEFAULT;
        }

    }

}
