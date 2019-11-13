package com.sample.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

import com.sample.dao.MailDao;
import com.sample.model.User;

public class Gmail extends Authenticator {
	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		// DB에서 관리자의 메일, 비밀번호를 가져옴
		MailDao dao = MailDao.getInstance();
		User user = dao.findAll();
		
		// 정상적으로 가져왔으면 메일과 비밀번호를 지정
		if(user != null) {
			return new PasswordAuthentication(user.getEmail(), user.getPassword());
		} else {
			return null;
		}
	}
}
