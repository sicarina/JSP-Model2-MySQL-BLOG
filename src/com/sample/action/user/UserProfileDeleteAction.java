package com.sample.action.user;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sample.action.Action;
import com.sample.dao.UserDao;
import com.sample.model.User;

public class UserProfileDeleteAction implements Action{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader in = request.getReader();
		String requestId = in.readLine();
		
		// 넘어온 데이터가 null 또는 공백일 경우 return
		if(requestId == null || requestId.equals("")) {
			return;
		}
		
		// DB에 저장하기 위해 user모델에 변수 저장
		User user = new User();
		user.setId(Integer.parseInt(requestId));
		user.setProfile("/blogNew/img/user.png");
		
		// DB에 저장하고 실행결과 값을 받음
		UserDao dao = UserDao.getInstance();
		int resultCnt = dao.updateProfile(user);
		
		// 정상적으로 저장되었다면 메인페이지로 이동, 안되었으면 알림창과 함께 이전페이지로 이동
		if(resultCnt == 1) {
			// 세션을 수정한 정보로 변경
			HttpSession session = request.getSession();
			User userSession = (User) session.getAttribute("user");
			userSession.setProfile("/blogNew/img/user.png");
			session.setAttribute("user", userSession);
		} else {
			//Script.back(response, "프로필 사진 수정에 실패하였습니다.\\n다시 시도해 주세요.");
		}
	}

}
