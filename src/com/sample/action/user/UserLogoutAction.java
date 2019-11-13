package com.sample.action.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sample.action.Action;

public class UserLogoutAction implements Action{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 세션을 가져와서 세션을 무효화
		HttpSession session = request.getSession();
		session.invalidate();
		
		// 메인 페이지로 이동
		response.sendRedirect("/blogNew/index.jsp");
	}

}
