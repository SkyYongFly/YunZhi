package com.skylaker.yunzhi.controller;

import com.skylaker.yunzhi.pojo.User;
import com.skylaker.yunzhi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 用户相关请求处理逻辑类
 * 
 * @author sky
 */
@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;

	/**
	 * 获取所有用户
	 *
	 * @return 所有用户JSON
	 */
	@RequestMapping(value = "/getAllUsers", method = RequestMethod.GET)
	public @ResponseBody List<User> getAllUsers(){
		return null;
	}

}
