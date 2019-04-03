package com.superywd.lottery.manage.utils;

/**
 * 各种正则表达式
 * @author 迷宫的中心
 * @date 2019/3/28 11:46
 */
public class RegexUtil {

    /**邮箱的正则*/
    public static final String CURRENT_EMAIL_REGEX = "^[A-Za-z\\d]+([-_.][A-Za-z\\d]+)*@([A-Za-z\\d]+[-.])+[A-Za-z\\d]{2,4}$";

    /**密码的正则*/
    public static final String CURRENT_PASSWORD_REGEX = "^[\\w_-]{6,16}$";

}
