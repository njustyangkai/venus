package com.venus.interceptor;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class SessionTimeoutInterceptor implements HandlerInterceptor
{
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2,
			Exception arg3)
	{

	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object arg2,
			ModelAndView arg3)
	{

	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2)
			throws IOException
	{

		String path = request.getServletPath();

		if ((!path.equals("/auth/login.do")) && (!path.equals("/auth/addStudent.do"))
				&& (!path.equals("/auth/isUsernameExist.do")) && (!path.equals("/auth/isLogin.do")))
		{
			String userId = (String) request.getSession().getAttribute("userId");

			if (StringUtils.isBlank(userId))
			{
				response.setHeader("sessionstatus", "timeout"); 
				return false;
			}

		}

		return true;
	}
}