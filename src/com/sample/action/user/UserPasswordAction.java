package com.sample.action.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sample.action.Action;
import com.sample.dao.UserDao;
import com.sample.model.User;
import com.sample.util.SHA256;
import com.sample.util.Script;

public class UserPasswordAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 요청에 같이 넘어온 데이터
		String username = request.getParameter("username").trim();
		String password = request.getParameter("password").trim();
		String newPassword = request.getParameter("newPassword").trim();
		
		// 넘어온 데이터가 null 또는 공백일 경우 return
		if(username == null || username.equals("")) {
			return;
		} else if(password == null || password.equals("")) {
			return;
		} else if(newPassword == null || newPassword.equals("")) {
			return;
		}
		
		// DB에서 회원정보를 받음
		UserDao dao = UserDao.getInstance();
		User user = dao.findByUsername(username);
		
		// 비밀번호와 salt값을 암호화해서 확인
		String passwordEncrypt = SHA256.getEncrypt(password, user.getSalt());
		
		// 비밀번호 변경하려는 유저 객체를 만들어 정보 저장
		User changeUser = new User();
		changeUser.setUsername(username);
		changeUser.setPassword(passwordEncrypt);
		
		// DB에 비밀번호 변경하려는 유저의 정보를 넘겨 일치하는 사용자 있는지 확인
		int resultCnt = dao.findByUsernameAndPassword(changeUser);
		
		// 일치하는 사용자가 있다면 비밀번호 변경 후 로그아웃시켜 메인페이지로 이동, 없으면 알림창과 함께 이전페이지로 이동
		if(resultCnt == 1) {
			// 비밀번호 변경하려는 유저의 새 비밀번호를 담아 정보 저장
			User changeNewUser = new User();
			changeNewUser.setId(user.getId());
			changeNewUser.setPassword(SHA256.getEncrypt(newPassword, user.getSalt()));
			
			int resultCnt2 = dao.updatePassword(changeNewUser);
			
			// 비밀번호가 정상적으로 변경되면 세션무효화 및 페이지 이동, 아니면 알림창과 함께 이전페이지로 이동
			if(resultCnt2 == 1) {
				// 세션을 가져와서 세션을 무효화
				HttpSession session = request.getSession();
				session.invalidate();
				
				// 메인 페이지로 이동
				Script.location(response, "비밀번호 변경이 완료되었습니다.\\n다시 로그인하시기 바랍니다.", "/blogNew/index.jsp");
			} else {
				Script.back(response, "비밀번호 변경 실패 : 연결에 실패하였습니다.\\n다시 시도해 주세요.");
			}
		} else if(resultCnt == 0) {
			// 입력한 정보와 동일한 사용자가 없는 경우
			Script.back(response, "비밀번호 변경 실패 : 현재 비밀번호가 정확하지 않습니다.\\n다시 시도해 주세요.");
		} else {
			// DB연결에 실패한 경우
			Script.back(response, "비밀번호 변경 실패 : 연결에 실패하였습니다.\\n다시 시도해 주세요.");
		}
	}

}
