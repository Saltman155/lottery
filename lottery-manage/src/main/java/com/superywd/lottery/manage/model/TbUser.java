package com.superywd.lottery.manage.model;


/**
 * @author: 迷宫的中心
 * @date: 2019/3/26 14:22
 */
public class TbUser {

    /**用户id*/
    private Long userId;
    /**用户昵称*/
    private String nickName;
    /**用户真实姓名*/
    private String realName;
    /**用户性别*/
    private String gender;
    /**用户年龄*/
    private Integer age;
    /**用户地址*/
    private String address;
    /**电话号码*/
    private String phone;
    /**邮箱*/
    private String email;
    /**备注*/
    private String remark;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "TbUser{" +
                "userId=" + userId +
                ", nickName='" + nickName + '\'' +
                ", realName='" + realName + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
