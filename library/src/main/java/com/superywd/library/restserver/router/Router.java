package com.superywd.library.restserver.router;

import com.superywd.library.restserver.http.Filter;
import com.superywd.library.restserver.http.HttpMethod;
import com.superywd.library.restserver.http.HttpRequest;
import com.superywd.library.restserver.http.HttpResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;

/**
 * 完整的请求链描述对象
 * @author 迷宫的中心
 * @date 2019/4/16 14:57
 */
public class Router {
    /**解析出的参数值数组*/
    private final String[] pathVariables;
    /**请求方法*/
    private final HttpMethod httpMethod;
    /**请求拦截器*/
    private final List<Filter> filters;
    /**最终到达的请求方法*/
    private final UriRoutingController controller;

    private Router(UriRoutingController controller, MatchResult matchResult, HttpMethod httpMethod) {
        this.filters = new ArrayList<>();
        this.httpMethod = httpMethod;
        this.controller = controller;
        pathVariables = new String[matchResult.groupCount()];

        for (int i = 0; i < pathVariables.length; i++) {
            pathVariables[i] = matchResult.group(i + 1);
        }
    }

    /**
     * 公有化创建方法
     * @param controller    处理请求控制器
     * @param matchResult   匹配结果
     * @param httpMethod    请求方法
     * @return              请求链处理对象
     */
    public static Router create(UriRoutingController controller, Matcher matchResult, HttpMethod httpMethod) {
        return new Router(controller, matchResult, httpMethod);
    }

    /**
     * 请求链处理流程
     * @param request       request对象
     * @param response      response对象
     * @return              控制器返回结果
     */
    public Object invoke(HttpRequest request, HttpResponse response) {
        try {
            //先执行所有拦截器pre方法
            for (Filter filter : filters) {
                if (!filter.preFilter(request, response)) {
                    return null;
                }
            }
            //然后执行控制器方法
            return this.controller.invoke(request, response, pathVariables);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            //最后依次执行拦截器post方法
            for (Filter filter : filters) {
                filter.postFilter(request, response);
            }
        }
    }

    /**
     * 添加拦截器
     * @param filters       待添加的拦截器
     */
    public void addFilters(List<Filter> filters){
        this.filters.addAll(filters);
    }

    /**
     * 获取请求控制器
     * @return              请求控制器
     */
    public UriRoutingController getController() {
        return controller;
    }

    /**
     * 获取请求路径
     * @return              请求路径
     */
    public String[] getPathVariables() {
        return pathVariables;
    }

    /**
     * 获取请求方法
     * @return              请求方法
     */
    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    /**
     * 获取请求拦截器
     * @return              请求拦截器
     */
    public List<Filter> getFilters() {
        return filters;
    }
}
