package com.zxj.web;

import javax.servlet.http.HttpServletRequest;
import org.springframework.util.Assert;
import com.zxj.constants.CommonConstant;
import com.zxj.domain.User;

public class BaseController {
	protected static final String ERROR_MSG_KEY = "errorMsg";

	/**
	 * 获取保存在Session中的用户对象；
	 * 
	 * @param request
	 * @return
	 */
	protected User getSessionUser(HttpServletRequest request) {
		return (User) request.getSession().getAttribute(CommonConstant.USER_CONTEXT);
	}

	/**
	 * 将User对象保存到ession中；
	 * 
	 * @param request
	 * @param user
	 */
	protected void setSessionUser(HttpServletRequest request, User user) {
		request.getSession().setAttribute(CommonConstant.USER_CONTEXT, user);
	}

	/**
	 * 获取基于应用程序的url绝对路径；
	 * 
	 * @param request
	 * @param url 以"/"打头的URL地址；
	 * @return 基于应用程序的url绝对路径；
	 */
	public final String getAppbaseUrl(HttpServletRequest request, String url) {
		Assert.hasLength(url, "url不能为空！");
		Assert.isTrue(url.startsWith("/"), "URL必须以/开头！");
		return request.getContextPath() + url;
	}
}
