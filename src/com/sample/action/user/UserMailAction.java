package com.sample.action.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sample.action.Action;
import com.sample.dao.UserDao;
import com.sample.model.User;
import com.sample.util.SHA256;
import com.sample.util.Script;

public class UserMailAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 요청에 같이 넘어온 데이터
		String username = request.getParameter("username").trim();
		String code = request.getParameter("code").trim();
		
		// 넘어온 데이터가 null 또는 공백일 경우 return
		if(username == null || username.equals("")) {
			return;
		} else if(code == null || code.equals("")) {
			return;
		}
		
		// DB에서 회원정보를 받음
		UserDao dao = UserDao.getInstance();
		User user = dao.findByUsername(username);
		
		// 회원정보에 저장된 이메일과 salt값을 암호화
		String dbEncryptCode = SHA256.getEncrypt(user.getEmail(), user.getSalt());
		
		// DB 회원정보 암호화한 값과 이메일에서 넘어온 암호화된 값을 비교
		if(dbEncryptCode.equals(code)){
			int resultCnt = dao.updateEmailChk(username);
			
			if(resultCnt == 1) {
				Script.location(response, "이메일 인증에 성공하였습니다.", "/blogNew/user/login.jsp");
			} else {
				Script.location(response, "이메일 인증에 실패하였습니다.", "/blogNew/error/mailAuth.jsp?msg=db");
			}
		} else{
			Script.location(response, "이메일 인증에 실패하였습니다.", "/blogNew/error/mailAuth.jsp?msg=code");
		}
	}
}
