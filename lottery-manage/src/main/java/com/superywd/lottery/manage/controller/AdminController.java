package com.superywd.lottery.manage.controller;

import com.superywd.lottery.manage.service.AdminService;
import com.superywd.lottery.manage.utils.RegexUtil;
import com.superywd.lottery.manage.utils.ResultPack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpSession;

/**
 * 管理员控制器
 * @author 迷宫的中心
 * @date 2019/3/28 11:17
 */
@RequestMapping(path = "/admin")
@RestController
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private AdminService adminService;

    /**
     * 用户登录
     * @param email         登录邮箱
     * @param password      登录密码
     * @param explainTime   会话有效期，默认为6小时
     */
    @RequestMapping(path = "/login",method = RequestMethod.POST)
    public Object login(
            HttpSession session,
            @RequestParam(name = "email")String email,
            @RequestParam(name = "password")String password,
            @RequestParam(name = "explainTime",required = false,defaultValue = "21600")Integer explainTime) throws Exception{
        if(!email.matches(RegexUtil.CURRENT_EMAIL_REGEX)){
            throw new Exception("错误的邮箱信息！");
        }
        if(!password.matches(RegexUtil.CURRENT_PASSWORD_REGEX)){
            throw new Exception("错误的密码格式！");
        }
        boolean judge = adminService.adminLogin(email,password);
        if(judge){
            logger.debug("用户 {} 的登录校验成功",email);
            //设置session以及失效时间，最多7天
            session.setAttribute("loginEmail",email);
            session.setMaxInactiveInterval(Math.min(604800,explainTime));
            return ResultPack.successInstance();
        }else{
            return ResultPack.failInstance(-1,"账号或密码错误");
        }
    }

    /**
     * 退出登录
     * @param session       会话对象
     * @return              返回数据包
     */
    @RequestMapping(path = "/exit",method = RequestMethod.GET)
    public Object exit(HttpSession session){
        session.setAttribute("loginEmail",null);
        return ResultPack.successInstance();
    }
}
