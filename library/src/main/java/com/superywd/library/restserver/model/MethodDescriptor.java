package com.superywd.library.restserver.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

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
    /**消耗*/
    private String consume;
    /**生产*/
    private String produce;

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

        this.invokeTarget = Dorado.beanContainer.getBean(clazz);
        this.annotations = method.getAnnotations();
        Consume consumeAnnotation = method.getAnnotation(Consume.class);
        Produce produceAnnotation = method.getAnnotation(Produce.class);

        methodParameters = new MethodParameter[method.getParameterCount()];
        for (int i = 0; i < method.getParameterCount(); i++) {
            Annotation annotation = parameterAnnotations[i].length == 0 ? null : parameterAnnotations[i][0];
            Class<?> type = parameterTypes[i];
            String name = parameterNames[i];
            Type genericParameterType = genericParameterTypes[i];

            MethodParameter methodParameter = MethodParameter.create(name, type, genericParameterType, annotation);
            if (methodParameter.annotationType == MultipartFile.class) {
            }
            methodParameters[i] = methodParameter;
            methodParameters[i].setMethodParameterCount(method.getParameterCount());
            registerMessageDescriptorForTypeIfNeed(type);
        }

        this.consume = consumeAnnotation == null ? guessConsume() : consumeAnnotation.value();
        this.produce = produceAnnotation == null ? guessProduce() : produceAnnotation.value();
    }

    public static MethodDescriptor create(Class<?> clazz, Method method) {
        return new MethodDescriptor(clazz, method);
    }

    private void registerMessageDescriptorForTypeIfNeed(Class<?> type) {
        try {
            if (Message.class.isAssignableFrom(type)) {
                registerMessageDescriptorForType(type);
            }
        } catch (Throwable ex) {
            // ignore this ex
        }
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

    }
}

