package com.skylaker.yunzhi.test;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;

/**
 * Created with IntelliJ IDEA.
 * User: zhuyong
 * Date: 2018/5/9
 * Time: 19:37
 * Description:
 */
public class MyRealm1 implements Realm {
    @Override
    public String getName() {
        return "myrealm1";
    }

    @Override
    public boolean supports(AuthenticationToken authenticationToken) {
        //假设仅支持UsernamePasswordToken类型的Token
        return authenticationToken instanceof UsernamePasswordToken;
    }

    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal(); //得到用户名
        String password =  new String((char[])token.getCredentials());

        if(!"zhang".equals(username)){
            throw  new UnknownAccountException(); //用户名不正确
        }

        if(!"123".equals(password)){
            throw  new IncorrectCredentialsException(); // 密码不正确
        }

        //身份验证成功
        return new SimpleAuthenticationInfo(username, password, getName());
}
}