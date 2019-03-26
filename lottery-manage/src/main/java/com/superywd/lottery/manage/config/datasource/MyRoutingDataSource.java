package com.superywd.lottery.manage.config.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 自定义数据源
 * @author: 迷宫的中心
 * @date: 2019/3/26 13:19
 */
public class MyRoutingDataSource extends AbstractRoutingDataSource {

    private static final Logger logger = LoggerFactory.getLogger(MyRoutingDataSource.class);

    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.getType();
    }
}
