package com.superywd.library.restserver.http.impl;

/**
 * URI解析器
 * @author 迷宫的中心
 * @date 2019/4/16 17:04
 */
public class URIParser {

    /**uri里'?'前的字符串*/
    private String requestUri;
    /**uri里'?'后的字符串*/
    private String queryString;
    /**应用预设的一级路由*/
    private String contextPath;

    public URIParser(String contextPath) {
        this.contextPath = contextPath;
    }

    public void parse(String uri) {
        int idx = uri.indexOf('?');

        //当包含'?'时
        if (idx != -1) {
            this.queryString = uri.substring(idx + 1);
            this.requestUri = uri.substring(0, idx);
        } else {
            this.requestUri = uri;
        }

        if (requestUri.startsWith(contextPath)) {
            this.requestUri = this.requestUri.substring(contextPath.length());
        }

        if (this.requestUri.endsWith("/"))
            this.requestUri = this.requestUri.substring(0,this.requestUri.length() - 1);

        if ("".equals(this.requestUri)) {
            this.requestUri = "/";
        }
    }

    public String getQueryString() {
        return queryString;
    }

    public String getRequestUri() {
        return requestUri;
    }

}
