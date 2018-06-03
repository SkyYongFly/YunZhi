package com.skylaker.yunzhi.service.impl;

import com.skylaker.yunzhi.config.GlobalConstant;
import com.skylaker.yunzhi.mappers.UserMapper;
import com.skylaker.yunzhi.pojo.db.Fileupload;
import com.skylaker.yunzhi.pojo.db.Role;
import com.skylaker.yunzhi.pojo.db.User;
import com.skylaker.yunzhi.pojo.res.BaseResult;
import com.skylaker.yunzhi.pojo.res.IResult;
import com.skylaker.yunzhi.pojo.res.LoginResult;
import com.skylaker.yunzhi.service.IUserService;
import com.skylaker.yunzhi.service.aop.LogAnnotation;
import com.skylaker.yunzhi.utils.BaseUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * 用户处理逻辑类
 *
 * @author sky
 */
@Service("userServiceImpl")
public class UserServiceImpl extends BaseService<User>  implements IUserService {
    @Autowired
    private UserMapper userMapper;


    /**
     * 手机号、密码验证
     *
     * @param phone
     * @param password
     * @return
     */
    @Override
    public LoginResult userPwdValidate(String phone, String password){
        if(BaseUtil.isNullOrEmpty(phone) || BaseUtil.isNullOrEmpty(password)){
            return LoginResult.NULL_NAME_PWD;
        }

        //获取验证token
        UsernamePasswordToken token = new UsernamePasswordToken(phone, password);

        //用户名、密码验证
        Subject subject = SecurityUtils.getSubject();

        //登录验证，异常由异常处理对象来处理
        subject.login(token);

        //保存用户到session
        User user = getUserByPhone(phone);
        subject.getSession().setAttribute(GlobalConstant.SESSION_USER_NAME, user);

        return LoginResult.SUCCESS;
    }

    /**
     * 获取所有用户
     *
     * @return 用户列表
     */
    @Override
    public List<User> getAllUsers() {
        return userMapper.getAllUsers();
    }

    /**
     * 根据手机号获取用户信息对象
     *
     * @param   phone 手机号
     * @return
     */
    @Override
    @Cacheable(value = "userCache")
    public User getUserByPhone(String phone) {
        if(BaseUtil.isNullOrEmpty(phone)){
            return null;
        }


        return userMapper.getUserByPhone(phone);
    }

    /**
     * 获取用户拥有的角色信息
     *
     * @param username 用户名
     * @return {set}     角色信息集合
     */
    @Override
    public Set<Role> getUserRoles(String username) {
        //TODO
        return null;
    }

    /**
     * 保存注册用户信息
     *
     * @param user  注册用户
     */
    @Override
    @Transactional
    public void saveRegisterUser(User user) throws RuntimeException{
        //用户密码加盐
        String salt = UUID.randomUUID().toString().replaceAll("-","");

        //加密密码
        SimpleHash simpleHash = new SimpleHash(GlobalConstant.PWD_MD5,
                user.getPassword(), salt, GlobalConstant.PASSWORD_ENCRYPT_COUNT);

        //构造用户实体
        User userEntity = new User.Builder(user.getPhone().trim())
                .username(user.getUsername().trim())
                .password(simpleHash.toHex())
                .salt(salt)
                .build();

        //保存用户
        userMapper.addUser(userEntity);
    }

    @Override
    public String getUserHeadImg(Integer userId) {
        if(BaseUtil.isNullOrEmpty(String.valueOf(userId))){
            return null;
        }

        //先从redis中获取用户头像相对路径
        String filePath = (String) redisService.getUserHeadImg(userId);

        //没有则从数据库查找
        if(BaseUtil.isNullOrEmpty(filePath)){
            Fileupload fileupload = userMapper.getUserHeadImg(userId);
            if(null == fileupload){
                return null;
            }

            filePath = fileupload.getFspath();
        }

        return filePath;
    }

    @Override
    public User getUserInfo() {
        return super.selectByKey(BaseUtil.getSessionUser().getId());
    }

    @Transactional
    @Override
    public IResult updateUserInfo(User user) {
        super.updateNotNull(user);

        return BaseResult.SUCCESS;
    }
}

