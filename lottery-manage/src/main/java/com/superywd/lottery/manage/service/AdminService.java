package com.superywd.lottery.manage.service;

/**
 * @author 迷宫的中心
 * @date 2019/3/28 11:28
 */
public interface AdminService {

    /**
     * 用户登录
     * @param email     登录账户
     * @param password  登录密码
     * @return          是否登录成功
     */
    boolean adminLogin(String email,String password);
}
