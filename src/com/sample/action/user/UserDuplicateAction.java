package com.sample.action.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sample.action.Action;
import com.sample.dao.UserDao;
import com.sample.model.User;

public class UserDuplicateAction implements Action{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 요청에 같이 넘어온 데이터
		String username = request.getParameter("username");
		
		// 넘어온 데이터가 null 또는 공백일 경우 return
		if(username == null || username.equals("")) {
			return;
		}

		// DB에서 실행결과 값(회원정보)을 받음
		UserDao dao = UserDao.getInstance();
		User user = dao.findByUsername(username);
		
		// 페이지로 응답해 줄 내용 작성
		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = response.getWriter();
		if (user == null) {
			out.print("ok");
		} else {
			out.print("fail");
		}
		out.flush();
	}
}
