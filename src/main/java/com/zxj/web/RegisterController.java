package com.zxj.web;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.zxj.domain.User;
import com.zxj.exception.UserExistException;
import com.zxj.service.UserService;

/**
 * 用户注册
 * 
 * @author zxj
 *
 */
@Controller
public class RegisterController extends BaseController {
	private UserService userService;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * 用户注册
	 * 
	 * @param request
	 * @param user 要进行注册的用户信息；
	 * @return
	 */
	// http的方法规范： 不管是添加、删除、更新，使用的url是一致的；
	// 如果进行删除，需要设置http的方法为delete，添加设置http的方法为post，查询get； 
	// 后台controller方法： 判断http方法，如果是delete，就执行删除，如果是post就执行添加操作；
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView register(HttpServletRequest request, User user) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("success");
		try {
			userService.register(user);
		} catch (UserExistException e) {
			mv.addObject("errorMsg", "用户名已经存在，请重新输入！");
			mv.setViewName("forward:register.jsp");
		}
		return mv;
	}

}
