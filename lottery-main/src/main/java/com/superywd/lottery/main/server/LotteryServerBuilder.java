package com.superywd.lottery.main.server;

import com.superywd.library.properties.ConfigurableProcessor;
import com.superywd.library.properties.PropertiesUtil;
import com.superywd.library.properties.Property;
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
public final class LotteryServerBuilder {

    private static final Logger logger = LoggerFactory.getLogger(LotteryServerBuilder.class);

    @Property(key = "server.thread.accept.count",defaultValue = "1")
    private int acThreadCount;
    @Property(key = "server.thread.io.count",defaultValue = "4")
    private int ioTreadCount;
    @Property(key = "server.thread.handle.max",defaultValue = "")
    private int maxHandleThreadCount;
    @Property(key = "server.thread.handle.min",defaultValue = "")
    private int minHandleThreadCount;
    @Property(key = "server.collection.max",defaultValue = "")
    private int maxCollection;
    @Property(key = "server.collection.",defaultValue = "")
    private int MAX_PENDING_REQUEST;
    @Property(key = "",defaultValue = "")
    private int MAX_IDLE_TIME;
    @Property(key = "server.collection.buffer.send.size",defaultValue = "")
    private int SEND_BUFFER_SIZE;
    @Property(key = "server.collection.buffer.receive.size",defaultValue = "")
    private int RECV_BUFFER_SIZE;
    @Property(key = "",defaultValue = "")
    private int MAX_PACKET_LENGTH;

    private final int port;

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


    private LotteryServerBuilder(int port) {
        this.port = port;
    }

    public static LotteryServerBuilder byPort(int port){ return new LotteryServerBuilder(port); }

    /**
     * 创建服务
     * @return 服务
     */
    public LotteryServer build(){
        Class<?> rootClass = getRootClass();
        serverParameterCheck();
        return new LotteryServer(this);
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
        }catch (Exception e){
            //ignore this ex
        }
        return rootClass;
    }

    private void serverParameterCheck(){

    }



    public int getAcThreadCount() {
        return acThreadCount;
    }

    public void setAcThreadCount(int acThreadCount) {
        this.acThreadCount = acThreadCount;
    }

    public int getIoTreadCount() {
        return ioTreadCount;
    }

    public void setIoTreadCount(int ioTreadCount) {
        this.ioTreadCount = ioTreadCount;
    }

    public int getMaxHandleThreadCount() {
        return maxHandleThreadCount;
    }

    public void setMaxHandleThreadCount(int maxHandleThreadCount) {
        this.maxHandleThreadCount = maxHandleThreadCount;
    }

    public int getMinHandleThreadCount() {
        return minHandleThreadCount;
    }

    public void setMinHandleThreadCount(int minHandleThreadCount) {
        this.minHandleThreadCount = minHandleThreadCount;
    }

    public int getMaxCollection() {
        return maxCollection;
    }

    public void setMaxCollection(int maxCollection) {
        this.maxCollection = maxCollection;
    }

    public int getPort() {
        return port;
    }
}
