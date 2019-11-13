package com.sample.action.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sample.action.Action;
import com.sample.dao.UserDao;
import com.sample.model.User;
import com.sample.util.SHA256;
import com.sample.util.Script;

public class UserLoginAction implements Action {
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 요청에 같이 넘어온 데이터
		String username = request.getParameter("username").trim();
		String rawPassword = request.getParameter("password").trim();
		String rememberMe = request.getParameter("rememberMe");
		
		// 넘어온 데이터가 null 또는 공백일 경우 return : 필수값만 체크
		if(username == null || username.equals("")) {
			return;
		} else if(rawPassword == null || rawPassword.equals("")) {
			return;
		}
		
		// DB에서 회원정보를 받음
		UserDao dao = UserDao.getInstance();
		User user = dao.findByUsername(username);
		
		// 비밀번호와 salt값을 암호화
		String password = SHA256.getEncrypt(rawPassword, user.getSalt());
		
		// 로그인 시도한 유저 객체를 만들어 정보 저장
		User loginUser = new User();
		loginUser.setUsername(username);
		loginUser.setPassword(password);
		
		// DB에 로그인 시도한 유저의 정보를 넘겨 일치하는 사용자 있는지 확인
		int resultCnt = dao.findByUsernameAndPassword(loginUser);
		
		// 일치하는 유저가 있다면 쿠키 처리 및 세션저장 후 페이지 이동, 없으면 알림창과 함께 이전페이지로 이동
		if(resultCnt == 1) {
			// rememberMe가 체크 되어 있을 경우 쿠키 저장, 체크 되지 않았을 경우 쿠키 삭제
			if(rememberMe != null) {
				Cookie c = new Cookie("username", username);
				c.setMaxAge(6000);
				response.addCookie(c);
			} else {
				Cookie c = new Cookie("username", null);
				c.setMaxAge(0);
				response.addCookie(c);
			}
			
			// DB에서 일치하는 사용자의 정보를 모두 가져와서 저장
			User returnUser = dao.findByUsername(username);
			
			// 세션에 가져온 사용자 정보 저장
			HttpSession session = request.getSession();
			session.setAttribute("user", returnUser);
			
			// 메인 페이지로 이동
			response.sendRedirect("/blogNew/index.jsp");
		} else if(resultCnt == 0) {
			// 입력한 정보와 동일한 사용자가 없는 경우
			Script.back(response, "로그인 실패 : 일치하는 사용자 정보가 없습니다.\\n다시 시도해 주세요.");
		} else {
			// DB연결에 실패한 경우
			Script.back(response, "로그인 실패 : 연결에 실패하였습니다.\\n다시 시도해 주세요.");
		}
	}
}
