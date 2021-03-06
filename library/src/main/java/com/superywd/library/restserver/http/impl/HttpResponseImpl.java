package com.superywd.library.restserver.http.impl;

import com.superywd.library.restserver.http.HttpResponse;
import io.netty.buffer.ByteBufUtil;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponseStatus;

import java.io.IOException;
import java.io.InputStream;

/**
 * 自定义httpResponse实现
 * @author 迷宫的中心
 * @date 2019/4/16 16:00
 */
public class HttpResponseImpl implements HttpResponse {

    private FullHttpResponse originalHttpResponse;



    public HttpResponseImpl(FullHttpResponse response) {
        this.originalHttpResponse = response;
    }

    @Override
    public void setHeader(String name, String value) {
        originalHttpResponse.headers().set(name, value);
    }

    @Override
    public void addHeader(String name, String value) {
        originalHttpResponse.headers().add(name, value);
    }

    @Override
    public void sendRedirect(String location) {
        originalHttpResponse.setStatus(HttpResponseStatus.FOUND);
        originalHttpResponse.headers().set(HttpHeaderNames.LOCATION, location);
    }

    @Override
    public void sendError(int sc, String msg) {
        originalHttpResponse.setStatus(HttpResponseStatus.valueOf(sc, msg));
    }

    @Override
    public void sendError(int sc) {
        originalHttpResponse.setStatus(HttpResponseStatus.valueOf(sc));
    }

    @Override
    public void write(byte[] content) {
        originalHttpResponse.content().writeBytes(content);
    }

    @Override
    public void writeStringUtf8(String str) {
        ByteBufUtil.writeUtf8(originalHttpResponse.content(), str);
    }

    @Override
    public void write(InputStream in) {
        try {
            originalHttpResponse.content().writeBytes(in, in.available());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }


}
