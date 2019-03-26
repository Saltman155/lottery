package com.superywd.lottery.manage.config.datasource;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


/**
 * @author: 迷宫的中心
 * @date: 2019/3/26 11:21
 */

@Aspect
@Component
@Order(value = 0)
public class DataSourceAop {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceAop.class);


    @Pointcut("@annotation(com.superywd.lottery.manage.config.datasource.annotaion.Master)")
    public void writePointCut(){}

    @Pointcut("@annotation(com.superywd.lottery.manage.config.datasource.annotaion.Slave)")
    public void readPointCut(){}

    /**
     * 操作主库方法前切点
     */
    @Before("writePointCut()")
    public void beforeWrite(){
        logger.trace("切换到主库...");
        DataSourceContextHolder.setMaster();
    }

    /**
     * 操作从库方法前切点
     */
    @Before("readPointCut()")
    public void beforeRead(){
        logger.trace("切换到从库...");
        DataSourceContextHolder.setSlave();
    }

    /**
     * 操作从库方法后切点
     */
    @Before("readPointCut()")
    public void afterRead(){
        //用完后需要切换回主库标识
        logger.trace("切换回主库...");
        DataSourceContextHolder.setMaster();
    }


}
