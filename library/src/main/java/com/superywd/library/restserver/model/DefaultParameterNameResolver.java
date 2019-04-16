package com.superywd.library.restserver.model;

import java.lang.reflect.Method;

/**
 * 默认的根据方法参数注解获得方法参数名
 * @author 迷宫的中心
 * @date 2019/4/16 20:20
 */
public class DefaultParameterNameResolver implements ParameterNameResolver {

    private ParameterNameResolver parameterNameResolver = new LocalVariableTableParameterNameResolver();

    @Override
    public String[] getParameterNames(Method method) {
        if (method.getParameterCount() == 0) {
            return new String[]{};
        }
    }
}
