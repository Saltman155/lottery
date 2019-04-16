package com.superywd.library.restserver.http.impl;

import com.superywd.library.restserver.http.HttpMethod;
import com.superywd.library.restserver.http.HttpRequest;
import com.superywd.library.restserver.netty.ChannelHolder;
import com.superywd.library.utils.ArrayUtil;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.QueryStringDecoder;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自定义httpRequest实现
 * @author 迷宫的中心
 * @date 2019/4/16 16:00
 */
public class HttpRequestImpl implements HttpRequest {

    /**原始HttpRequest对象*/
    private final FullHttpRequest request;
    /**请求方法*/
    private HttpMethod method;
    /**携带的参数*/
    private final Map<String, List<String>> parameters;
    /**请求头部信息*/
    private final HttpHeaders headers;
    /**uri解析器*/
    private final URIParser uriParser;

    public HttpRequestImpl(FullHttpRequest request,String contextPath) {
        this.request = request;
        this.parameters = new HashMap<>();
        this.headers = request.headers();

        this.uriParser = new URIParser(contextPath);
        /**请求参数解码器*/
        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(request.uri());
        this.uriParser.parse(queryStringDecoder.path());
        parameters.putAll(queryStringDecoder.parameters());
        method = HttpMethod.getInstance(request.method().name());
        //关于这块的解析，如果是post请求，参数的解析方式是不同的，但目前暂时只支持get请求
        //...（待补充post解析方式）
    }

    @Override
    public String getRequestURI() {
        return uriParser.getRequestUri();
    }

    @Override
    public HttpMethod getRequestMethod() {
        return method;
    }

    @Override
    public String getParameter(String name) {
        List<String> parameterValues = parameters.get(name);
        return (ArrayUtil.isEmpty(parameterValues)) ? null : parameterValues.get(0);
    }

    @Override
    public String[] getParameters(String name) {
        return this.parameters.get(name).toArray(new String[] {});
    }

    @Override
    public String getRemoteAddr() {
        String xForwardFor = headers.get("X-Forwarded-For");
        if (xForwardFor == null) {
            InetSocketAddress addr = (InetSocketAddress) ChannelHolder.get().remoteAddress();
            return addr.getAddress().getHostAddress();
        }
        return xForwardFor.split(",")[0];
    }

    @Override
    public String getHeader(String name) {
        return headers.get(name);
    }

    @Override
    public String[] getHeaders(String name) {
        return headers.getAll(name).toArray(new String[] {});
    }

}
