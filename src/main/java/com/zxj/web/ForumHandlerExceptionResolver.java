package com.zxj.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

public class ForumHandlerExceptionResolver extends SimpleMappingExceptionResolver {

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response,
											  Object handler, Exception ex) {
		request.setAttribute("ex", ex);
		ex.printStackTrace();
		return super.doResolveException(request, response, handler, ex);
	}
}
