package com.skylaker.yunzhi.config;

import com.skylaker.yunzhi.pojo.db.Role;
import com.skylaker.yunzhi.pojo.db.User;
import com.skylaker.yunzhi.service.IUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户验证
 *
 * User: zhuyong
 * Date: 2018/5/12 10:30
 */
public class UserRealm  extends AuthorizingRealm {
    @Resource(name = "userServiceImpl")
    private IUserService userService;

    /**
     * 授权：根据用户信息返回权限信息，权限信息通过角色来管理
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(
            PrincipalCollection principals) {
        //获取手机号
        String phone = (String) principals.getPrimaryPrincipal();
        //权限信息管理对象
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        //获取用户拥有的角色
        Set<Role> roles = userService.getUserRoles(phone);

        if(null != roles && roles.size() > 0){
            Set<String> roleNames = new HashSet<>();
            for (Role role : roles){
                roleNames.add(role.getRolename());
            }
            //将角色名称提供给权限管理对象
            authorizationInfo.setRoles(roleNames);

            //获取用户权限信息
//        Set<Permission> permissions = userService.getUserPermissions(username);
//        Set<String> permissionNames = new HashSet<>();
//        for(Permission permission : permissions){
//            permissionNames.add(permission.getPername());
//        }
//        //将权限信息名称提供给权限管理对象
//        authorizationInfo.setStringPermissions(permissionNames);
        }

        return authorizationInfo;
    }

    /**
     * 认证：根据用户信息返回认证信息
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken token) throws AuthenticationException {
        //登录用户手机号
        String phone = (String) token.getPrincipal();
        //根据账号数据库查找对应用户
        User user = userService.getUserByPhone(phone);

        //用户不存在
        if(null == user){
            throw new UnknownAccountException();
        }

        //用户被锁定
        if(Boolean.TRUE.equals(user.isLocked())){
            throw new LockedAccountException();
        }

        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配
        SimpleAuthenticationInfo authenticationInfo =
                new SimpleAuthenticationInfo(user.getPhone(), user.getPassword(), getName());

        //密码加密盐
        authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(user.getSalt()));

        return  authenticationInfo;
    }
}