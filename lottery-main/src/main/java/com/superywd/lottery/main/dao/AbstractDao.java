package com.superywd.lottery.main.dao;

import com.superywd.lottery.main.database.DatabaseFactory;
import org.apache.ibatis.session.SqlSessionFactory;

/**
 * @author 迷宫的中心
 * @date 2019/5/14 23:39
 */
public abstract class AbstractDao {

    private static SqlSessionFactory sessionFactory;

    protected static SqlSessionFactory getSessionFactory(){
        return DatabaseFactory.getSqlSessionFactory();
    }


}
