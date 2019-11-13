package com.sample.action.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sample.action.Action;
import com.sample.dao.UserDao;
import com.sample.mail.SendMail;
import com.sample.model.User;

public class UserSendAction implements Action{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 요청에 같이 넘어온 데이터
		String username = request.getParameter("username").trim();
		String email = request.getParameter("email").trim();
		
		// 넘어온 데이터가 null 또는 공백일 경우 return : 필수값만 체크
		if(username == null || username.equals("")) {
			return;
		} else if(email == null || email.equals("")) {
			return;
		}
		
		// DB에서 실행결과 값(회원정보)을 받음 : salt값 가져오기
		UserDao dao = UserDao.getInstance();
		User user = dao.findByUsername(username);
		
		// 인증 메일 발송
		String resultMsg = SendMail.sendMail(email, user.getSalt(), username);
		
		// 페이지로 응답해 줄 내용 작성
		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		if(resultMsg.equals("success")) {
			out.print("success");
		} else {
			out.print("fail");
		}
		out.flush();
	}

}
