package com.superywd.library.restserver;

import com.superywd.library.properties.ConfigurableProcessor;
import com.superywd.library.properties.PropertiesUtil;
import com.superywd.library.properties.Property;
import com.superywd.library.utils.ArrayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


/**
 * 服务启动配置
 * @author 迷宫的中心
 * @date 2019/4/2 9:41
 */
public final class ServerBuilder {

    private static final Logger logger = LoggerFactory.getLogger(ServerBuilder.class);

    @Property(key = "server.thread.accept.count",defaultValue = "1")
    private int acThreadCount;
    @Property(key = "server.thread.io.count",defaultValue = "4")
    private int ioTreadCount;
    @Property(key = "server.thread.handle.max",defaultValue = "4")
    private int maxHandleThreadCount;
    @Property(key = "server.thread.handle.min",defaultValue = "4")
    private int minHandleThreadCount;
    @Property(key = "server.http.maxPendingRequest",defaultValue = "10")
    private int maxPendingRequest;
    @Property(key = "server.http.contextPath",defaultValue = "/server")
    private String contextPath;
    @Property(key = "server.collection.max",defaultValue = "100")
    private int maxCollection;
    @Property(key = "server.collection.readIdleTime.max")
    private int maxReadIdleTime;
    @Property(key = "server.collection.buffer.send.size",defaultValue = "100")
    private int sendBufferSize;
    @Property(key = "server.collection.buffer.receive.size",defaultValue = "100")
    private int recvBufferSize;
    @Property(key = "server.collection.packet.size",defaultValue = "100")
    private int maxPacketLength;

    private final int port;

    private String[] scanPackages;


    {
        List<Properties> propertiesList = null;
        try {
            propertiesList = PropertiesUtil.loadAllFromDirectory(new File("./config"));
        }catch (Exception e){
            logger.info("加载配置文件失败，载入默认配置...");
            propertiesList = new ArrayList<>();
        }
        ConfigurableProcessor.process(this,propertiesList);
    }


    private ServerBuilder(int port) {
        this.port = port;
    }

    public static ServerBuilder byPort(int port){ return new ServerBuilder(port); }

    /**
     * 创建服务
     * @return 服务
     */
    public Server build(){
        Class<?> rootClass = getRootClass();
        if(ArrayUtil.isEmpty(scanPackages) && rootClass != null){
            String defaultPackage = rootClass.getPackage().getName();
            scanPackages = new String[]{defaultPackage};
        }
        if(ArrayUtil.isEmpty(scanPackages)){
            throw new IllegalArgumentException("没有找到需要扫描注解的包...");
        }
        return new Server(this);
    }


    /**
     * 获取入口方法的类对象
     * @return  类对象
     */
    private Class<?> getRootClass(){
        Class<?> rootClass = null;
        try{
            StackTraceElement[] stackTraceElements = new RuntimeException().getStackTrace();
            for(StackTraceElement item : stackTraceElements){
                if("main".equals(item.getMethodName())){
                    rootClass = Class.forName(item.getClassName());
                    break;
                }
            }
        }catch (Exception ignore){
        }
        return rootClass;
    }

    public int getAcThreadCount() {
        return acThreadCount;
    }

    public int getIoTreadCount() {
        return ioTreadCount;
    }

    public int getMaxHandleThreadCount() {
        return maxHandleThreadCount;
    }

    public int getMinHandleThreadCount() {
        return minHandleThreadCount;
    }

    public int getMaxPendingRequest() {
        return maxPendingRequest;
    }

    public String getContextPath() {
        return contextPath;
    }

    public int getMaxCollection() {
        return maxCollection;
    }

    public int getMaxReadIdleTime() {
        return maxReadIdleTime;
    }

    public int getPort() {
        return port;
    }

    public int getMaxPacketLength() {
        return maxPacketLength;
    }

    public String[] getScanPackages() {
        return scanPackages;
    }
}
