package com.skylaker.yunzhi.controller;

import com.alibaba.fastjson.JSONObject;
import com.skylaker.yunzhi.pojo.db.User;
import com.skylaker.yunzhi.service.IUserService;
import com.skylaker.yunzhi.service.aop.LogAnnotation;
import com.skylaker.yunzhi.utils.BaseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户相关请求处理逻辑类
 * 
 * @author sky
 */
@Controller
@RequestMapping("/user")
public class UserController {
	@Resource(name = "userServiceImpl")
	private IUserService userService;


	/**
	 * 获取所有用户
	 *
	 * @return 所有用户JSON
	 */
	@RequestMapping(value = "/getAllUsers", method = RequestMethod.GET)
	public @ResponseBody List<User> getAllUsers(){
		return null;
	}


	/**
	 * 获取当前用户头像相对路径
	 *
	 * @return
	 */
	@LogAnnotation(type = "用户", action = "获取头像信息")
	@RequestMapping(value = "/getUserHeadImg", method = RequestMethod.GET)
	public @ResponseBody JSONObject getUserHeadImg(){
		String fspath = userService.getUserHeadImg(BaseUtil.getSessionUser().getId());

		JSONObject result = new JSONObject();
		result.put("imgpath", fspath);

		return result;
	}

	/**
	 * 获取用户信息
	 *
	 * @return
	 */
	@LogAnnotation(type = "用户", action = "获取用户信息")
	@RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
	public @ResponseBody User getUserInfo(){
		return userService.getUserInfo();
	}

	/**
	 * 更新用户信息
	 *
	 * @param 	user
	 * @return
	 */
	@LogAnnotation(type = "用户", action = "更新用户信息")
	@RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
	public @ResponseBody JSONObject updateUserInfo(@RequestBody User user){
		return BaseUtil.getResult(userService.updateUserInfo(user));
	}
}
