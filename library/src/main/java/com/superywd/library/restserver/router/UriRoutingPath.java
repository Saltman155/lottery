package com.superywd.library.restserver.router;

import com.superywd.library.restserver.http.HttpMethod;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 路由对象（主要是匹配）
 * @author 迷宫的中心
 * @date 2019/4/13 17:23
 */
public class UriRoutingPath implements Comparable<UriRoutingPath> {

    /**正则表达式解析引擎*/
    private static final Pattern pathVariablePattern = Pattern.compile("\\{([^{}]*)\\}");
    /**在没有特殊声明正则匹配式的情况下使用的参数匹配（注意使用了^/，又做了分组处理，就可以按照/隔开匹配了）*/
    private static final String defaultPathVariableRegex = "[^/]+";
    /**代码中声明的uri全路径*/
    private String routingPath;
    /**真正用于uri匹配的正则引擎*/
    private Pattern routingPathPattern;
    /**限定请求方法*/
    private HttpMethod httpMethod;
    /**所有在路由里声明的参数集*/
    private Map<String, Integer> pathVariableIndexHolder = new ConcurrentHashMap<>();

    private UriRoutingPath(String routingPath, HttpMethod httpMethod) {
        this.routingPath = routingPath;
        this.httpMethod = httpMethod;
        //获取匹配结果
        Matcher matchResult = pathVariablePattern.matcher(routingPath);
        int pathVariableIndex = 0;
        StringBuffer routingPathRegex = new StringBuffer();
        while (matchResult.find()) {
            //取得在{}中的字符（其中的字符被设置成分组）
            String pathVariableExpression = matchResult.group(1);
            String[] pathVariableSegments = pathVariableExpression.split(":");
            //字符的正则匹配式，如果有指定的匹配式（:后）则采用指定的，没有即是自身
            String pathVariableRegex = pathVariableSegments.length == 2 ? pathVariableSegments[1]
                    : defaultPathVariableRegex;
            //匹配的名称（对应参数）
            String pathVariableName = pathVariableSegments[0];
            //用新的匹配式替换老的字符串中的匹配式（把:前的换掉）
            matchResult.appendReplacement(routingPathRegex, String.format("(%s)", pathVariableRegex));
            pathVariableIndexHolder.put(pathVariableName, pathVariableIndex);
            pathVariableIndex++;
        }
        //把剩余的追到尾部
        matchResult.appendTail(routingPathRegex);
        routingPathPattern = Pattern.compile(routingPathRegex.toString());
    }

    public static UriRoutingPath create(String routingPath,HttpMethod httpMethod){
        return new UriRoutingPath(routingPath, httpMethod);
    }

    /**
     * 查询在匹配式里的参数
     * @param parameterName     参数名称
     * @return                  参数被匹配到的分组号
     */
    public int resolvePathIndex(String parameterName) {
        Integer pathIndex = pathVariableIndexHolder.get(parameterName);
        return pathIndex == null ? -1 : pathIndex;
    }

    public Pattern getRoutingPathPattern() {
        return routingPathPattern;
    }

    public String getRoutingPath() {
        return routingPath;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (this.getClass() != obj.getClass())
            return false;
        UriRoutingPath other = (UriRoutingPath) obj;
        if (httpMethod == null) {
            if (other.httpMethod != null)
                return false;
        } else if (!httpMethod.equals(other.httpMethod))
            return false;
        if (routingPath == null) {
            if (other.routingPath != null)
                return false;
        } else if (!routingPath.equals(other.routingPath))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hash = 1;
        hash = prime * hash + ((httpMethod == null) ? 0 : httpMethod.hashCode());
        hash = prime * hash + ((routingPath == null) ? 0 : routingPath.hashCode());
        return hash;
    }

    @Override
    public int compareTo(UriRoutingPath o) {
        //TODO： 这里的比较有问题，后期得改一下
        int targetPathSegments = o.routingPath.split("/").length;
        int thisPathSegments = routingPath.split("/").length;
        if (targetPathSegments != thisPathSegments) {
            return targetPathSegments - thisPathSegments;
        }

        return pathVariableIndexHolder.size() - o.pathVariableIndexHolder.size();
    }
}
