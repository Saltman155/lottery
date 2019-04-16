package com.superywd.library.restserver.router;

import com.superywd.library.restserver.annotation.RequestMapping;
import com.superywd.library.restserver.annotation.RestController;
import com.superywd.library.restserver.http.HttpMethod;
import com.superywd.library.restserver.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * 路由注册中心
 * @author 迷宫的中心
 * @date 2019/4/13 16:59
 */
public class UriRoutingRegistry {

    private static final Logger logger = LoggerFactory.getLogger(UriRoutingRegistry.class);

    private volatile Map<UriRoutingPath,UriRoutingController> uriRoutingRegistry = new HashMap<>();

    private UriRoutingRegistry() { }

    public static UriRoutingRegistry getInstance(){
        return new UriRoutingRegistry();
    }

    /**清除路由映射表*/
    public void clear(){
        uriRoutingRegistry.clear();
    }

    /**
     * 获取路由映射表
     * @return 路由映射表（去重）
     */
    public Map<UriRoutingPath,UriRoutingController> uriRoutings(){
        return uriRoutingRegistry;
    }

    /**
     * 注册路由
     * @param clazz     控制器类
     */
    public void register(Class<?> clazz) {
        RestController controller = clazz.getAnnotation(RestController.class);
        //如果类上没有RestController注解，表示不是控制器，不进行处理
        if (controller == null) {
            return;
        }
        RequestMapping classMapping = clazz.getAnnotation(RequestMapping.class);
        String classPath = classMapping.path();
        HttpMethod classMethod = classMapping.Method();
        Method[] controllerMethods = clazz.getDeclaredMethods();
        for(Method method : controllerMethods){
            //静态的、非公开的、无RequestMapping注释的方法都不予处理
            if (method.getAnnotation(RequestMapping.class) == null ||
                    Modifier.isStatic(method.getModifiers()) ||
                    !Modifier.isPublic(method.getModifiers())) {
                continue;
            }
            RequestMapping methodMapping = method.getAnnotation(RequestMapping.class);
            String methodPath = methodMapping.path();
            HttpMethod methodMethod = methodMapping.Method();
            String resultPath = String.format("%s%s",classPath,methodPath);
            HttpMethod resultMethod = methodMethod != HttpMethod.DEFAULT ?
                    methodMethod : classMethod;
            resultMethod = resultMethod == HttpMethod.DEFAULT ? HttpMethod.GET : resultMethod;
            //创建路由对象
            UriRoutingPath uriRoutingPath = UriRoutingPath.create(resultPath,resultMethod);
            //创建控制器方法入口对象
            UriRoutingController routeController = UriRoutingController.create(uriRoutingPath, clazz, method);
            //绑定路由对象到入口的映射，添加到映射表
            uriRoutingRegistry.put(uriRoutingPath,routeController);
            logger.debug("添加路由映射 {} ,请求方法为 {}",null,null);
        }

    }

    public Router findRouteController(HttpRequest httpRequest){
        String RequestURI = httpRequest.getRequestURI();
        HttpMethod method = httpRequest.getRequestMethod();
        return null;
    }


}


