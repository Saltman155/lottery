package com.superywd.lottery.manage.service.impl;

import com.superywd.lottery.manage.dao.AdminDao;
import com.superywd.lottery.manage.model.TbAdmin;
import com.superywd.lottery.manage.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 迷宫的中心
 * @date 2019/3/28 11:28
 */

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminDao adminDao;

    @Override
    public boolean adminLogin(String email, String password) {
        TbAdmin user = adminDao.selectUserByEmail(email);
        if(user == null){
            return false;
        }
        String realPassword = user.getPassword();
        return password.equals(realPassword);
    }
}
