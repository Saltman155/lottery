package com.superywd.library.restserver.model;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * 默认的根据方法参数注解获得方法参数名
 * @author 迷宫的中心
 * @date 2019/4/16 20:20
 */
public class DefaultParameterNameResolver implements ParameterNameResolver {

    @Override
    public String[] getParameterNames(Method method) {
        Parameter[] methodParameters = method.getParameters();
        String[] parameterNames = new String[methodParameters.length];

        for (int i = 0; i < parameterNames.length; i++) {
            parameterNames[i] = methodParameters[i].getName();
        }

        return parameterNames;
    }


}
