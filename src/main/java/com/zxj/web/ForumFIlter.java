package com.zxj.web;

// 静态引入包：包含公共常量属性；
import static com.zxj.constants.CommonConstant.*;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import com.zxj.domain.User;

// request.getRequestURL()  返回全路径
// request.getRequestURI()  返回除去host（域名或者ip）部分的路径
// request.getContextPath() 返回工程名部分，如果工程映射为/，此处返回则为空
// request.getServletPath() 返回除去host和工程名部分的路径
// httpRequest.getQueryString() 获取查询参数
// Eg：
// request.getRequestURL()  http://localhost:8080/jqueryLearn/resources/request.jsp 
// request.getRequestURI()  /jqueryLearn/resources/request.jsp
// request.getContextPath() /jqueryLearn 
// request.getServletPath() /resources/request.jsp 

// 论坛过滤器：Web层的每个Controller都有可能涉及登录验证处理逻辑，如论坛中只有登录后才能发表新话题；
// 所以提供一个过滤器开进行处理；
public class ForumFIlter implements Filter {
	// 请求标识，防止一个请求同一个过滤器多次过滤；
	private static final String FILTERED_REQUEST = "@@session_context_filtered_request";

	/**
	 * 不需要登录即可访问的资源；<br/>
	 * inherent_escape_uris：固有的 避开 uri
	 */
	private static final String[] INHERENT_ESCAPE_URIS = { "/index.jsp", "/index.html", "login.jsp",
			"/login/doLogin.html", "register.jsp", "/register.html", 
			"/board/listBoardTopics-", "/board/listTopicPosts-" };

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	// 执行过滤；
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 保证该过滤器在一次请求中只被调用一次；
		// if request和标识都不为空，则说明该过滤器已经被调用过，直接将请求传递给下一个过滤器或者目标资源；
		if (request != null && request.getAttribute(FILTERED_REQUEST) != null) {
			chain.doFilter(request, response);
		} else {
			// 设置过滤标识；
			request.setAttribute(FILTERED_REQUEST, Boolean.TRUE);
			// 将ServletRequest转换为HttpServletRequest，后者继承自前者，都是接口，后者多了一些针对http协议的处理方法；
			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			// 从request域中获取User对象；
			User userContext = getSessionUser(httpServletRequest);
			// 若User对象为空说明未登录，并且当前URI是需要登录才能访问的地址，则组合url，
			// 然后保存到session中，用于登录之后，跳转到目标URL；然后转发到登录页面；
			// 否则，直接将请求传递给下一个过滤器或目标资源；
			if (userContext == null && !isURILogin(httpServletRequest.getRequestURI(), httpServletRequest)) {
				// 获取URL，将其转换成String类型；(是登录前的URL)
				String toUrl = httpServletRequest.getRequestURL().toString();
				// 若访问地址带参数，将url与参数进行连接；
				if (!StringUtils.isEmpty(httpServletRequest.getQueryString())) {
					toUrl += "?" + httpServletRequest.getQueryString();
				}
				// 将登录前的URL添加到session域中；
				httpServletRequest.getSession().setAttribute(LOGIN_TO_URL, toUrl);
				// 设置转发的视图；
				request.getRequestDispatcher("/login.jsp").forward(request, response);
				return;
			}  
			chain.doFilter(request, response);
		}
	}

	/**
	 * 从http的request域中获取User对象；
	 * 
	 * @param request
	 * @return
	 */
	protected User getSessionUser(HttpServletRequest request) {
		return (User) request.getSession().getAttribute(USER_CONTEXT);
	}

	/**
	 * 判断指定的URI是否需要登录 才能访问；<br/>
	 * true：不用登录直接访问； false：需要登录才能访问；
	 * 
	 * @param requestURI 指定的URI；
	 * @param request
	 * @return
	 */
	private boolean isURILogin(String requestURI, HttpServletRequest request) {
		// 若requestURI是项目根路径，或者是静态变量INHERENT_ESCAPE_URIS中指定的 不需要登录即可访问的资源，则返回true；
		// request.getContextPath()：获取项目的根路径；
		if (request.getContextPath().equalsIgnoreCase(requestURI)
				|| (request.getContextPath() + "/").equalsIgnoreCase(requestURI))
			return true;
		for (String uri : INHERENT_ESCAPE_URIS) {
			if (requestURI != null && requestURI.indexOf(uri) >= 0)
				return true;
		}
		return false;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
