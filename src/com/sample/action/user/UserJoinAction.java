package com.sample.action.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sample.action.Action;
import com.sample.dao.UserDao;
import com.sample.mail.SendMail;
import com.sample.model.User;
import com.sample.util.SHA256;
import com.sample.util.Script;

public class UserJoinAction implements Action {
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 요청에 같이 넘어온 데이터
		String username = request.getParameter("username").trim();
		String rawPassword = request.getParameter("password").trim();
		String email = request.getParameter("email").trim();
		String address = request.getParameter("address").trim();
		
		// 넘어온 데이터가 null 또는 공백일 경우 return : 필수값만 체크
		if(username == null || username.equals("")) {
			return;
		} else if(rawPassword == null || rawPassword.equals("")) {
			return;
		} else if(email == null || email.equals("")) {
			return;
		}
		
		// 비밀번호 암호화
		String salt = SHA256.getSalt();
		String password = SHA256.getEncrypt(rawPassword, salt);
		
		// DB에 저장하기 위해 user모델에 변수 저장
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setSalt(salt);
		user.setEmail(email);
		user.setAddress(address);
		
		// DB에 저장하고 실행결과 값을 받음
		UserDao dao = UserDao.getInstance();
		int resultCnt = dao.save(user);
		
		// 정상적으로 저장되었다면 페이지 이동, 안되었으면 알림창과 함께 이전페이지로 이동
		if(resultCnt == 1) {
			// 회원가입을 위해 인증 메일 발송
			String resultMsg = SendMail.sendMail(email, salt, username);
			
			// 필요시 발송 결과에 따라 재발송이나 다른 처리 수행
			System.out.println("인증메일 발송 결과 : " + resultMsg);
		
			if(resultMsg.equals("success")) {
				// 회원가입 완료 메시지 및 페이지 이동
				Script.location(response, "회원가입이 완료되었습니다.\\n메일을 확인하여 본인인증을 완료해주시기 바랍니다.", "/blogNew/user/login.jsp");
			} else {
				Script.location(response, "회원가입이 완료되었습니다.", "/blogNew/user/login.jsp");
			}
		} else {
			Script.back(response, "회원가입이 실패하였습니다.\\n다시 시도해 주시기 바랍니다.");
		}
	}
}
