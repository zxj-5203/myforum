package com.zxj.web;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.zxj.constants.CommonConstant;
import com.zxj.domain.User;
import com.zxj.service.UserService;

/**
 * LoginController：通过调用service层的UserService类来完成用户的登录和注销的业务操作；
 * 
 * @author zxj
 *
 */
@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {
	private UserService userService;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * 用户登录；
	 * 
	 * @param request
	 * @param user
	 * @return
	 */
	@RequestMapping("/doLogin")
	public ModelAndView login(HttpServletRequest request, User user) {
		User userDb = userService.getUserByUserName(user.getUserName()); // 根据传入的User对象的名字，从数据库中获取User对象；
		ModelAndView mv = new ModelAndView(); // 创建MV，指定视图：若登录失败，则页面转发到登录页面；
		mv.setViewName("forward:/login.jsp"); // 通过forward进行页面转发，浏览器地址栏url不变，request可以共享；

		if (userDb == null) {
			mv.addObject("errorMsg", "用户名不存在！");
		} else if (!userDb.getPassword().equals(user.getPassword())) {
			mv.addObject("errorMsg", "用户密码不正确！");
		}else if (userDb.getLocked() == User.USER_LOCK) {
			mv.addObject("errorMsg", "用户已经被锁定，不能登录！");
		} else {  // 用户登录
			userDb.setLastIp(request.getRemoteAddr());
			userDb.setLastVisit(new Date());
			userService.loginSuccess(userDb); // 积分+5，新增登录日志；
			setSessionUser(request, userDb);  // 将用户新增到session域中；

			// 在当前会话中获取用户登录之前的URL，然后从当前会话移除；
			// 判断URL是否存在，(这个URL在过滤器中设置)，若存在，直接跳转到这个URL，否则跳转到主页；
			String toUrl = (String) request.getSession().getAttribute(CommonConstant.LOGIN_TO_URL);
			request.getSession().removeAttribute(CommonConstant.LOGIN_TO_URL);
			System.out.println("....."+toUrl);
			// 若当前会话中没有保存登录前的URL，则页面跳转到主页，否则重定向到URL；
			if (StringUtils.isEmpty(toUrl)) {
				toUrl = "/index.html";
			} 
			mv.setViewName("redirect:" + toUrl); // redirect：重定向后重新进行request（request无法共享）；
		}
		return mv;
	}

	/**
	 * 用户注销：将user对象从session域中移除，然后重定向到主页；
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/doLogout")
	public String logout(HttpSession session) {
		session.removeAttribute(CommonConstant.USER_CONTEXT);
		return "forward:/index.jsp";
	}
}
