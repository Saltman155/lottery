package com.superywd.lottery.manage.dao;

import com.superywd.lottery.manage.model.TbUser;
import org.apache.ibatis.annotations.Param;

/**
 * @author: 迷宫的中心
 * @date: 2019/3/26 14:19
 */
public interface UserDao {

    /**
     * 根据用户id获取用户信息
     * @param userId    用户id
     * @return          用户信息
     */
    TbUser selectUserById(@Param("useId")Long userId);

}
