package com.superywd.lottery.manage.config.datasource;

/**
 * @author: 迷宫的中心
 * @date: 2019/3/26 11:54
 */
public class DataSourceContextHolder {

    /**
     * 获取当前线程上下文中的数据库标记
     * @return  数据库标记
     */
    static DBTypeEnum getType(){
        return null;
    }

    /**
     * 切换到主库
     */
    static void setMaster(){}

    /**
     * 切换到从库
     */
    static void setSlave(){}
}
