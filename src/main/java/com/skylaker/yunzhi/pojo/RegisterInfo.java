package com.skylaker.yunzhi.pojo;

/**
 * Created with IntelliJ IDEA.
 * User: zhuyong
 * Date: 2018/5/20 16:43
 * Description:
 *      用户注册信息
 */
public class RegisterInfo {
    //用户名/账号
    private String 	username;

    //账号密码
    private String 	password;

    //手机号
    private String  phone;

    //验证码
    private String  vercode;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getVercode() {
        return vercode;
    }

    public void setVercode(String vercode) {
        this.vercode = vercode;
    }
}