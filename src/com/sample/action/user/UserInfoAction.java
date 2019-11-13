package com.sample.action.user;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sample.action.Action;
import com.sample.dao.UserDao;
import com.sample.model.User;
import com.sample.util.Script;

public class UserInfoAction implements Action{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 요청에 같이 넘어온 데이터
		String username = request.getParameter("username");
		
		// 넘어온 데이터가 null 또는 공백일 경우 처리
		if(username == null || username.equals("")) {
			return;
		}
		
		// DB에서 일치하는 사용자의 정보를 모두 가져와서 저장
		UserDao dao = UserDao.getInstance();
		User user = dao.findByUsername(username);
		
		HttpSession session = request.getSession();
		User userSession = (User)session.getAttribute("user");
		
		if(userSession.getUsername().equals(username)) {
			// 가져온 정보가 있으면 info 페이지로, 아니라면 이전 페이지로 이동
			if(user != null) {
				request.setAttribute("user", user);
				RequestDispatcher dis = request.getRequestDispatcher("/user/info.jsp");
				dis.forward(request, response);
			} else {
				Script.back(response, "정보를 가져오는데 실패했습니다.\\n다시 시도해 주세요.");
			}
		} else {
			session.invalidate();
			Script.location(response, "정상적이지 않은 접근입니다.\\n다시 로그인한 후 시도해 주세요.", "/blogNew/user/login.jsp");
		}
	}

}
