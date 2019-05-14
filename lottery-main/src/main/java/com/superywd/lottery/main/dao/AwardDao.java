package com.superywd.lottery.main.dao;

import com.superywd.lottery.main.model.TbAward;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * @author 迷宫的中心
 * @date 2019/5/14 23:20
 */
public class AwardDao extends AbstractDao {


    public static List<TbAward> getAwardByActivityId(Integer id){
        String statement = "com.superywd.lottery.main.dao.AwardDao.selectAwardByActivityId";
        List<TbAward> result = null;
        SqlSession session = null;
        try {
            session = getSessionFactory().openSession();
            result = session.selectList(statement, id);
        }catch (Exception e){
            if(session != null){ session.rollback(); }
        }finally {
            if(session != null){ session.commit(); }
        }
        return result;
    }
}
