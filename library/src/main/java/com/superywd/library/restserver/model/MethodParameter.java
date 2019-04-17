package com.superywd.library.restserver.model;

import com.superywd.library.restserver.annotation.PathVariable;
import com.superywd.library.restserver.annotation.RequestParam;
import com.superywd.library.restserver.transform.ParamTransformer;
import com.superywd.library.restserver.transform.Transformer;
import com.superywd.library.utils.StringUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 方法参数描述类
 * @author 迷宫的中心
 * @date 2019/4/17 17:03
 */
public class MethodParameter {

    /**对应的方法*/
    private Method method;
    /**形参名称*/
    private String name;
    /**形参类型*/
    private Class<?> type;
    /**形参的注解*/
    private Annotation annotation;
    /**默认的值*/
    private Object defaultValue;
    /**参数值转换类*/
    private Transformer transformer;

    private MethodParameter(Method method, String name, Class<?> type, Annotation annotation) {
        this.method = method;
        this.name = name;
        this.type = type;
        this.annotation =annotation;
    }

    public Method getMethod() { return method; }
    public String getName() { return name; }
    public Class<?> getType() { return type; }
    public Annotation getAnnotation() { return annotation; }
    public Object getDefaultValue() { return defaultValue; }
    public Transformer getTransformer() { return transformer; }

    /**
     * 根据形参上的注解，解析出具体的参数信息
     * @param annotations       形参上的注解集
     * @return                  相应的参数描述
     */
    public static MethodParameter getInstance(Method method, Class type, Annotation[] annotations){
        Transformer transformer = null;
        MethodParameter methodParameter = null;
        for(Annotation annotation : annotations){
            if(annotation instanceof RequestParam){
                RequestParam requestParam = (RequestParam) annotation;
                methodParameter = new MethodParameter(method,requestParam.name(),type,requestParam);
                transformer = ParamTransformer.getCurrentTransformer(methodParameter);
                //把默认值设置一下
                if(!StringUtil.isIdle(requestParam.defaultValue())){
                    methodParameter.defaultValue = transformer.stringValueTransform(requestParam.defaultValue());
                }
                break;
            }
            if(annotation instanceof PathVariable){
                PathVariable pathVariable = (PathVariable) annotation;
                methodParameter = new MethodParameter(method,pathVariable.name(),type,pathVariable);
                transformer = ParamTransformer.getCurrentTransformer(methodParameter);
                break;
            }
        }
        if(methodParameter == null) {
            throw new Error(String.format("控制器方法形参必须使用注解修饰来确定参数对应关系！在类 %s 上的 %s 方法",
                    method.getDeclaringClass().getName(), method.getName()));
        }
        methodParameter.transformer = transformer;
        return methodParameter;
    }
}
