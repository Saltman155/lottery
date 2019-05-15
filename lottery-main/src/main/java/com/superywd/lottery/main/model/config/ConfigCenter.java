package com.superywd.lottery.main.model.config;

import com.superywd.library.properties.ConfigurableProcessor;
import com.superywd.library.properties.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.security.krb5.Config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author 迷宫的中心
 * @date 2019/5/15 23:38
 */
public class ConfigCenter {

    private static final Logger logger = LoggerFactory.getLogger(ConfigCenter.class);

    public static void loadConfig(){
        logger.info("配置中心载入各类配置...");
        List<Properties> propertiesList = null;
        try {
            propertiesList = PropertiesUtil.loadAllFromDirectory(new File("./config"));
        }catch (Exception e){
            logger.info("加载数据库配置文件失败，载入默认配置...");
            propertiesList = new ArrayList<>();
        }
        ConfigurableProcessor.process(DataBaseConfig.class,propertiesList);
        logger.info("载入MySQL数据库配置成功...");
        ConfigurableProcessor.process(RedisConfig.class,propertiesList);
        logger.info("载入Redis数据库配置成功...");
        logger.info("配置载入完成...");
    }
}
