package com.superywd.lottery.manage.dao;

import com.superywd.lottery.manage.model.TbAdmin;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 管理员表操作接口
 * @author: 迷宫的中心
 * @date: 2019/3/26 14:52
 */
@Repository
public interface AdminDao {

    TbAdmin selectUserByEmail(@Param("email") String userEmail);

}
