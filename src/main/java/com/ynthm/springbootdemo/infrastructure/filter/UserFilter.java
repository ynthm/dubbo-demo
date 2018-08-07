package com.ynthm.springbootdemo.infrastructure.filter;


import com.ynthm.springbootdemo.infrastructure.rbac.User;
import com.ynthm.springbootdemo.infrastructure.util.UserUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;

/**
 * 用户filter，设置当前用户和语言到threadlocal中。
 * 
 * @author : Ynthm
 *
 */
@Order(1) //@Order注解表示执行过滤顺序，值越小，越先执行
@WebFilter(filterName = "userFilter", urlPatterns = "/*")
public class UserFilter implements Filter {


	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		MDC.put("requestId", StringUtils.remove(UUID.randomUUID().toString(),"-"));

		fillUserInfo((HttpServletRequest) request);

		String username = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
		} else {
			username = principal.toString();
		}
		HttpSession httpSession = ((HttpServletRequest) request).getSession();
		httpSession.setAttribute("username",username);

		try {
			chain.doFilter(request, response);
		} finally {
			clearAllUserInfo();
		}
	}


	private void clearAllUserInfo() {
		UserUtil.clearAllUserInfo();
	}

	private void fillUserInfo(HttpServletRequest request) {
		// 用户信息
		User user = getUserFromSession(request);
		//FIXME User user = getUserFromSpringSecurity();

		if (user != null) {
			UserUtil.setUser(user);
		}

		// 语言信息
		String locale = getLocaleFromCookies(request);
		// Locale locale1 = LocaleContextHolder.getLocale();

		if (locale != null) {
			UserUtil.setLocale(locale);
		}
	}

	private String getLocaleFromCookies(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();

		if (cookies == null) {
			return null;
		}

		for (int i = 0; i < cookies.length; i++) {
			if (UserUtil.KEY_LANG.equals(cookies[i].getName())) {
				return cookies[i].getValue();
			}
		}

		return null;
	}

	private User getUserFromSession(HttpServletRequest request) {
		HttpSession session = request.getSession();

		//if (session == null) {
		//	return null;
		//}

		// 从session中获取用户信息放到工具类中
		return (User) session.getAttribute(UserUtil.KEY_USER);
	}

	@Override
	public void destroy() {

	}
}
