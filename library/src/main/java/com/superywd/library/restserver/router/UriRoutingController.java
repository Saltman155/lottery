package com.superywd.library.restserver.router;


import com.superywd.library.restserver.http.HttpRequest;
import com.superywd.library.restserver.http.HttpResponse;
import com.superywd.library.restserver.model.MethodDescriptor;

import java.lang.reflect.Method;

import static com.superywd.library.restserver.model.MethodDescriptor.MethodParameter;

/**
 * 控制器方法入口对象（代理对象）
 * @author 迷宫的中心
 * @date 2019/4/13 17:56
 */
public class UriRoutingController {

    /**方法及所述类封装对象*/
    private final MethodDescriptor methodDescriptor;
    /**路由信息封装对象*/
    private final UriRoutingPath uriRoutingPath;

    private UriRoutingController(UriRoutingPath uriRoutingPath, Class<?> clazz, Method method) {
        methodDescriptor = MethodDescriptor.create(clazz, method);
        this.uriRoutingPath = uriRoutingPath;
    }

    public static UriRoutingController create(UriRoutingPath uriRoutingPath, Class<?> clazz, Method method) {
        return new UriRoutingController(uriRoutingPath, clazz, method);
    }


    /**
     * 代理对象的调用方法
     * @param request           封装的请求
     * @param response          封装的结果
     * @param pathVariables     路径
     * @return
     * @throws Exception
     */
    public Object invoke(HttpRequest request, HttpResponse response, String[] pathVariables) throws Exception {
        Method invokeMethod = methodDescriptor.getMethod();
        Object[] args = resolveParameters(request, response, pathVariables);
        MethodReturnValueHandlerConfig methodReturnValueHandlerConfig = Webapp.get()
                .getMethodReturnValueHandlerConfig();

        try {
            Object invokeResult = invokeMethod.invoke(methodDescriptor.getInvokeTarget(), args);
            // 支持对controller返回结果进行统一处理
            MediaType mediaType = MediaTypeUtils.defaultForType(methodDescriptor.getReturnType(),
                    methodDescriptor.produce());

            if (supportHandleMethodReturnValue(request, methodDescriptor, methodReturnValueHandlerConfig)) {
                invokeResult = methodReturnValueHandlerConfig.getHandler().handleMethodReturnValue(invokeResult,
                        methodDescriptor);
                if (invokeResult != null) {
                    mediaType = MediaTypeUtils.defaultForType(invokeResult.getClass(), null);
                }
            }
            if (methodReturnValueHandlerConfig != null
                    && !methodReturnValueHandlerConfig.exclude(request.getRequestURI())) {
            }
            writeResponseBody(invokeResult, mediaType, response);
        } catch (Exception ex) {
            handleException(ex, request, response);
        }
        return null;
    }

    private Object[] resolveParameters(HttpRequest request, HttpResponse response, String[] pathVariables) {
        MethodParameter[] methodParameters = methodDescriptor.getMethodParameters();
        if (methodParameters.length == 0) {
            return null;
        }
        Object[] methodArgs = new Object[methodParameters.length];
        for (int i = 0; i < methodArgs.length; i++) {
            int pathVariableIndex = uriRoutingPath.resolvePathIndex(methodParameters[i].getName());
            methodArgs[i] = resolveMethodArg(request, response, methodDescriptor, methodParameters[i],
                    pathVariableIndex == -1 ? null : pathVariables[pathVariableIndex]);
        }
        return methodArgs;
    }



}
