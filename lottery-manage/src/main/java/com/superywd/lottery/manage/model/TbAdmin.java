package com.superywd.lottery.manage.model;

/**
 * 管理员表
 * @author: 迷宫的中心
 * @date: 2019/3/26 14:54
 */
public class TbAdmin {

    private String email;

    private String password;

    private Integer identity;

    private TbUser userInfo;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getIdentity() {
        return identity;
    }

    public void setIdentity(Integer identity) {
        this.identity = identity;
    }

    public TbUser getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(TbUser userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public String toString() {
        return "TbAdmin{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", identity=" + identity +
                ", userInfo=" + userInfo +
                '}';
    }
}
