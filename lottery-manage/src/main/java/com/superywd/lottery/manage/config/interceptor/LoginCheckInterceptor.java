package com.superywd.lottery.manage.config.interceptor;

import com.superywd.lottery.manage.dao.AdminDao;
import org.apache.catalina.Session;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author 迷宫的中心
 * @date 2019/3/28 13:26
 */
public class LoginCheckInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String loginEmail = (String) session.getAttribute("loginEmail");
        if(loginEmail != null){
            request.setAttribute("email",loginEmail);
            return true;
        }
        response.sendRedirect("/lottery/login.html");
        return false;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
