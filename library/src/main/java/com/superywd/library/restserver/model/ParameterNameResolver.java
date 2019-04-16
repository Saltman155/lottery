package com.superywd.library.restserver.model;

import java.lang.reflect.Method;

/**
 * 方法的参数名称列表解析器
 * @author 迷宫的中心
 * @date 2019/4/16 19:38
 */
public interface ParameterNameResolver {

    /**
     * 解析指定方法的参数名称列表
     * @param method        待解析的方法
     * @return              参数列表
     */
    String[] getParameterNames(Method method);
}
