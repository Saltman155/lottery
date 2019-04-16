package com.superywd.library.restserver.router;

import com.superywd.library.restserver.http.HttpMethod;

import java.util.Objects;

/**
 * 路由对象
 * @author 迷宫的中心
 * @date 2019/4/13 17:23
 */
public class UriRoutingPath implements Comparable<UriRoutingPath> {

    private String routingPath;
    private HttpMethod httpMethod;

    public static UriRoutingPath create(String routingPath,HttpMethod httpMethod){
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UriRoutingPath that = (UriRoutingPath) o;
        return Objects.equals(routingPath, that.routingPath) &&
                httpMethod == that.httpMethod;
    }

    @Override
    public int hashCode() {
        return Objects.hash(routingPath, httpMethod);
    }

    @Override
    public int compareTo(UriRoutingPath o) {
        return 0;
    }
}
