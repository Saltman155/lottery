package com.superywd.lottery.main.model;

/**
 * @author 迷宫的中心
 * @date 2019/5/14 23:09
 */
public class TbAdmin {

    private String email;

    private String password;

    private Integer identity;

    private TbUser user;

    public TbAdmin() {
    }

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

    public TbUser getUser() {
        return user;
    }

    public void setUser(TbUser user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "TbAdmin{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", identity=" + identity +
                ", user=" + user +
                '}';
    }
}
