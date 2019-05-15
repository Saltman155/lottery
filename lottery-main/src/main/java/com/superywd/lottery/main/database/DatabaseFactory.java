package com.superywd.lottery.main.database;

import com.superywd.library.properties.ConfigurableProcessor;
import com.superywd.library.properties.PropertiesUtil;
import com.superywd.lottery.main.model.config.DataBaseConfig;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author 迷宫的中心
 * @date 2019/3/14 23:02
 */

public class DatabaseFactory {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseFactory.class);

    private static SqlSessionFactory sqlSessionFactory;

    public synchronized static void init() {
        if(sqlSessionFactory != null){
            return;
        }
        try {
            List<Properties> propertiesList = null;
            try {
                propertiesList = PropertiesUtil.loadAllFromDirectory(new File("./config"));
            }catch (Exception e){
                logger.info("加载数据库配置文件失败，载入默认配置...");
                propertiesList = new ArrayList<>();
            }
            ConfigurableProcessor.process(DataBaseConfig.class,propertiesList);
            //从文件中加载sqlSessionFactory
            InputStream inputStream = Resources.getUrlAsStream(DataBaseConfig.MYBATIS_CONFIG_PATH);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            logger.info("数据库连接服务已创建！");
        } catch (Exception e) {
            logger.error("mybatis加载错误！");
            throw new Error(e);
        }
    }



    public static SqlSessionFactory getSqlSessionFactory(){
        return sqlSessionFactory;
    }


}
