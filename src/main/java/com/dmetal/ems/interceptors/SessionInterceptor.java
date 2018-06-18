package com.dmetal.ems.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * ������������session��֤
 * @author NiCo
 *
 */
public class SessionInterceptor implements HandlerInterceptor{

	public boolean preHandle(
			HttpServletRequest request, 
			HttpServletResponse response, 
			Object handler)
			throws Exception {
		//�Ȼ��session����
		HttpSession session=request.getSession();
		Object obj=session.getAttribute("user");
		if(obj==null) {
			//�����session�������Ҳ�����Ӧ����
			//˵��û�е�¼���ض��򵽵�½ҳ��
			response.sendRedirect("toLogin.do");
			return false;
			
		}
		//�����¼,�����������
		return true;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}
	
}	
