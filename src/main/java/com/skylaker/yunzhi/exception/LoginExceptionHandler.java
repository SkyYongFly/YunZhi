package com.skylaker.yunzhi.exception;

import com.skylaker.yunzhi.pojo.LoginResult;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created with IntelliJ IDEA.
 * User: zhuyong
 * Date: 2018/5/22 21:15
 * Description:
 *      用户登录异常处理
 */
@Component
public class LoginExceptionHandler {
    /**
     * 处理登录异常
     *
     * @param exception
     * @return
     */
    public  String handlerLoginException(Exception exception){
        if(exception instanceof IncorrectCredentialsException){
            //密码不正确
            return LoginResult.INCORRECT_PWD.getMessage();
        }else if(exception instanceof UnknownAccountException){
            //用户不存在
            return LoginResult.NO_ACCOUNT.getMessage();
        }else if(exception instanceof ExcessiveAttemptsException){
            //输错密码次数过多
            return LoginResult.TO_MUCH_ERROR.getMessage();
        }

        return LoginResult.LOGIN_FAILTURE.getMessage();
    }

}