package com.sample.action.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sample.action.Action;
import com.sample.dao.UserDao;
import com.sample.model.User;
import com.sample.util.Script;

public class UserUpdateAction implements Action{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 요청에 같이 넘어온 데이터
		String id = request.getParameter("id").trim();
		String username = request.getParameter("username").trim();
		String email = request.getParameter("email").trim();
		String emailChk = request.getParameter("emailChk").trim();
		String address = request.getParameter("address").trim();
		
		// 넘어온 데이터가 null 또는 공백일 경우 return : 필수값만 체크
		if(id == null || id.equals("")) {
			return;
		} else if(username == null || username.equals("")) {
			return;
		} else if(email == null || email.equals("")) {
			return;
		} else if(emailChk == null || emailChk.equals("")) {
			return;
		}
		
		// DB에 저장하기 위해 user모델에 변수 저장
		User user = new User();
		user.setId(Integer.parseInt(id));
		user.setUsername(username);
		user.setEmail(email);
		user.setEmailChk(Boolean.parseBoolean(emailChk));
		user.setAddress(address);
		
		// DB에 저장하고 실행결과 값을 받음
		UserDao dao = UserDao.getInstance();
		int resultCnt = dao.update(user);
		
		// 정상적으로 저장되었다면 메인페이지로 이동, 안되었으면 알림창과 함께 이전페이지로 이동
		if(resultCnt == 1) {
			// 세션을 수정한 정보로 변경
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			
			// 메인페이지로 이동
			Script.location(response, "회원정보가 수정되었습니다.", "/blogNew/index.jsp");
		} else {
			Script.back(response, "회원정보 수정에 실패하였습니다.\\n다시 시도해 주세요.");
		}
	}

}
