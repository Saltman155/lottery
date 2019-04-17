package com.superywd.library.restserver.router;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.superywd.library.restserver.http.HttpRequest;
import com.superywd.library.restserver.http.HttpResponse;
import com.superywd.library.restserver.model.MethodDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

import static com.superywd.library.restserver.model.MethodDescriptor.MethodParameter;

/**
 * 控制器方法入口对象（代理对象）
 * @author 迷宫的中心
 * @date 2019/4/13 17:56
 */
public class UriRoutingController {

    private static final Logger logger = LoggerFactory.getLogger(UriRoutingController.class);
    /**用于格式化输出的类（目前只支持content-type application/json类型的，后续再加吧）*/
    private static final ObjectMapper mapper = new ObjectMapper();

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
        try {
            Object invokeResult = invokeMethod.invoke(methodDescriptor.getInvokeTarget(), args);
            writeResponseBody(invokeResult, response);
        } catch (Exception ex) {
            //处理异常 以后的异常拦截器可以直接在这里处理
            handleException(ex, request, response);
        }
        return null;
    }

    private void writeResponseBody(Object body, HttpResponse response) throws JsonProcessingException {
        if (body == null) {
            return;
        }
        //简单处理
        response.setHeader("content-type", "application/json;charset=utf-8");
        response.write(mapper.writeValueAsString(body).getBytes());
    }

    /**
     * 异常处理
     * @param ex            抛出的异常
     * @param request       请求对象
     * @param response      响应对象
     */
    private void handleException(Exception ex, HttpRequest request, HttpResponse response) {
        response.setHeader("content-type", "*/*");
        response.write("error".getBytes());
    }

    private Object[] resolveParameters(HttpRequest request, HttpResponse response, String[] pathVariables) {
        MethodParameter[] methodParameters = methodDescriptor.getMethodParameters();
        if (methodParameters.length == 0) {
            return null;
        }
        Object[] methodArgs = new Object[methodParameters.length];
        for (int i = 0; i < methodArgs.length; i++) {
            //获取参数在路由里的原始位置
            int pathVariableIndex = uriRoutingPath.resolvePathIndex(methodParameters[i].getName());
//            methodArgs[i] = resolveMethodArg(request, response, methodDescriptor, methodParameters[i],
//                    pathVariableIndex == -1 ? null : pathVariables[pathVariableIndex]);
        }
        return methodArgs;
    }



}
