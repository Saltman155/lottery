package com.superywd.library.restserver.model;

import com.superywd.library.restserver.http.HttpRequest;
import com.superywd.library.restserver.http.HttpResponse;
import com.superywd.library.utils.ClassUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 调用方法及上下文信息包装类
 * @author 迷宫的中心
 * @date 2019/4/16 14:13
 */
public class MethodDescriptor {

    /**具体类*/
    private final Class clazz;
    /**具体方法*/
    private final Method method;
    /**方法上注解*/
    private Annotation[] annotations;
    /**具体对象*/
    private Object invokeTarget;
    /**返回类型*/
    private Class<?> returnType;
    /**方法参数封装类*/
    private MethodParameter[] methodParameters;

    private MethodDescriptor(Class clazz, Method method) {
        this(clazz, method, new DefaultParameterNameResolver());
    }

    private MethodDescriptor(Class clazz, Method method, ParameterNameResolver parameterNameResolver) {
        this.clazz = clazz;
        this.method = method;
        this.returnType = method.getReturnType();

        if (!method.isAccessible()) {
            method.setAccessible(true);
        }
        //获取形参类型列表
        Class<?>[] parameterTypes = method.getParameterTypes();
        //获取形参名称
        String[] parameterNames = parameterNameResolver.getParameterNames(method);
        //获取形参上的注释
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();

        try {
            this.invokeTarget = ClassUtil.buildInstance(clazz,null,null);
        } catch (NoSuchMethodException e) {
            //先这么干吧
            throw new Error("不支持控制器含有带参数的构造器！");
        }
        this.annotations = method.getAnnotations();
        methodParameters = new MethodParameter[method.getParameterCount()];
        for (int i = 0; i < method.getParameterCount(); i++) {
            Annotation annotation = parameterAnnotations[i].length == 0 ? null : parameterAnnotations[i][0];
            Class<?> type = parameterTypes[i];
            String name = parameterNames[i];
            MethodParameter methodParameter = new MethodParameter(name,type,annotation);
            methodParameters[i] = methodParameter;
        }
    }

    public static MethodDescriptor create(Class<?> clazz, Method method) {
        return new MethodDescriptor(clazz, method);
    }


    public Class getClazz() {
        return clazz;
    }

    public Annotation[] getAnnotations() {
        return annotations;
    }

    public Method getMethod() {
        return method;
    }

    public Object getInvokeTarget() {
        return invokeTarget;
    }

    public Class<?> getReturnType() {
        return returnType;
    }

    public MethodParameter[] getMethodParameters() {
        return methodParameters;
    }

    /** 方法参数信息封装类*/
    public static class MethodParameter {
        /**形参名称*/
        private String name;
        /**形参类型*/
        private Class<?> type;
        /**形参注解*/
        private Annotation annotation;
        /**注解类型*/
        private Class<?> annotationType;

        private MethodParameter(String name, Class<?> type, Annotation annotation) {
            this.name = name;
            this.type = type;
            this.annotation =annotation;
            this.annotationType = annotation == null ? null : annotation.getClass();

            if(type == HttpRequest.class){
                this.annotationType = HttpRequest.class;
            }
            if (type == HttpResponse.class) {
                this.annotationType = HttpResponse.class;
            }
        }

        public String getName() { return name; }
        public Class<?> getType() { return type; }
        public Annotation getAnnotation() { return annotation; }
        public Class<?> getAnnotationType() { return annotationType; }
    }

}

