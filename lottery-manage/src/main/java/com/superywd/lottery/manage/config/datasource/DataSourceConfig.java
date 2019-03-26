package com.superywd.lottery.manage.config.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据源配置
 * @author: 迷宫的中心
 * @date: 2019/3/26 12:00
 */
@Configuration
public class DataSourceConfig {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);

    @Bean
    @ConfigurationProperties("spring.datasource.master")
    public DataSource masterDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.slave1")
    public DataSource slave1DataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.slave2")
    public DataSource slave2DataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "myRoutingDataSource")
    public DataSource myRoutingDataSource(
            @Qualifier("masterDataSource")DataSource masterDataSource,
            @Qualifier("slave1DataSource")DataSource slave1DataSource,
            @Qualifier("slave2DataSource")DataSource slave2DataSource){
        Map<Object,Object> dataSourceMap = new HashMap<>(3);
        dataSourceMap.put(DBTypeEnum.MASTER,masterDataSource);
        dataSourceMap.put(DBTypeEnum.SLAVE1,slave1DataSource);
        dataSourceMap.put(DBTypeEnum.SLAVE2,slave2DataSource);
        MyRoutingDataSource routingDataSource = new MyRoutingDataSource();
        routingDataSource.setTargetDataSources(dataSourceMap);
        routingDataSource.setDefaultTargetDataSource(masterDataSource);
        return routingDataSource;
    }
}
