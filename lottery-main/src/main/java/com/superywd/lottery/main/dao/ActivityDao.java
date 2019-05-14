package com.superywd.lottery.main.dao;

import com.superywd.lottery.main.model.TbActivity;
import org.apache.ibatis.session.SqlSession;


/**
 * @author 迷宫的中心
 * @date 2019/5/14 23:20
 */
public class ActivityDao extends AbstractDao {


    public static TbActivity getActivityById(Integer id){
        String statement = "com.superywd.lottery.main.dao.ActivityDao.selectActivityById";
        TbActivity result;
        SqlSession session = null;
        try {
            session = getSessionFactory().openSession();
            result = session.selectOne(statement, id);
            return result;
        }catch (Exception e){
            if(session != null){ session.rollback(); }
            return null;
        }finally {
            if(session != null){ session.commit(); }
        }
    }
}
