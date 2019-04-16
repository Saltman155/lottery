package com.superywd.library.restserver;

import com.superywd.library.restserver.router.UriRoutingRegistry;
import com.superywd.library.utils.PackageScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * http容器上下文
 * @author 迷宫的中心
 * @date 2019/4/3 9:51
 */
public class ServerContext {

    private static final Logger logger = LoggerFactory.getLogger(ServerContext.class);

    private final String contextPath;
    /**扫描包*/
    private final String[] packages;
    /**路由注册中心*/
    private final UriRoutingRegistry registry;
    /**专门用于执行业务处理的线程池*/
    private final ServerHandlerExecutor executor;


    private ServerContext(String contextPath,String[] packages,ServerHandlerExecutor executor) {
        this.contextPath = contextPath;
        this.packages = packages;
        this.executor = executor;
        this.registry = UriRoutingRegistry.getInstance();
    }

    public static ServerContext create(ServerBuilder builder){
        //创建执行业务的线程池
        ServerHandlerExecutor executor = new ServerHandlerExecutor(
                builder.getMinHandleThreadCount(),
                builder.getMaxHandleThreadCount(),
                new LinkedBlockingQueue<>(builder.getMaxPendingRequest()));
        ServerContext context = new ServerContext(
                builder.getContextPath(),
                builder.getScanPackages(),
                executor);
        context.initialize();
        return context;
    }

    /**
     * 容器初始化
     */
    private void initialize(){
        List<Class<?>> classes = new ArrayList<>();
        try {
            for (String scanPackage : packages) {
                classes.addAll(PackageScanner.scan(scanPackage));
            }
            //处理扫描的所有类，注册控制器
            classes.forEach(this::registerWebComponent);
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage(),e);
        }

    }

    /**
     * 在应用初始化时，处理类上的各类注解以及功能接口，注册到应用上下文中
     * @param clazz         待处理的类
     */
    private void registerWebComponent(Class<?> clazz){

    }

    /**
     * 路由注册
     * @param controller    待注册的控制器
     */
    private void initializeUriRouting(Class<?> controller) {
        registry.register(controller);
    }

    /**
     * 获取项目的一级路由
     * @return              一级路由
     */
    public String getContextPath() {
        return contextPath;
    }

    /**
     * 获取路由注册中心
     * @return              路由注册中心
     */
    public UriRoutingRegistry getRegistry() {
        return registry;
    }

    /**
     * 获取业务处理线程池
     * @return              业务处理线程池
     */
    public ServerHandlerExecutor getExecutor() {
        return executor;
    }
}
