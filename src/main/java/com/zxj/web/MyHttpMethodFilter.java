package com.zxj.web;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.filter.HiddenHttpMethodFilter;

public class MyHttpMethodFilter extends HiddenHttpMethodFilter {
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String method = httpServletRequest.getMethod();
		System.out.println("method1......" + method);
		if (method.equalsIgnoreCase("delete") || method.equalsIgnoreCase("put")) {
			method = "POST";
		}
		System.out.println("method2......" + method);
		httpServletRequest = new HttpMethodRequestWrapper(request, method);
		filterChain.doFilter(httpServletRequest, response);
	}

	private static class HttpMethodRequestWrapper extends HttpServletRequestWrapper {
		private final String method;

		public HttpMethodRequestWrapper(HttpServletRequest request, String method) {
			super(request);
			this.method = method;
		}

		@Override
		public String getMethod() {
			return this.method;
		}
	}

}
