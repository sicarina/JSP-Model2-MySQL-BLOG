package com.sample.mail;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sample.dao.MailDao;
import com.sample.model.User;
import com.sample.util.SHA256;

public class SendMail {
	public static String sendMail(String email, String salt, String username) {
		// DB에서 관리자의 메일을 가져옴
		MailDao dao = MailDao.getInstance();
		User user = dao.findAll();
		
		// 메일 발송에 필요한 변수
		String from = user.getEmail();
		String to = email;
		String code = SHA256.getEncrypt(to, salt);

		StringBuffer sb = new StringBuffer();
		sb.append("아래의 링크를 클릭하여 인증을 진행해 주시기 바랍니다.<br/>");
		sb.append("<a href='http://localhost:8000/blogNew/user?cmd=mail&username=" + username +"&code=" + code + "'>");
		sb.append("메일 인증하기");
		sb.append("</a>");

		String subject = "Sample Site 회원가입을 위한 인증 메일입니다.";
		String content = sb.toString();
		
		Properties p = new Properties();
		p.put("mail.smtp.user", from);
		p.put("mail.smtp.host", "smtp.googlemail.com");
		p.put("mail.smtp.port", "465"); //TLS 587, SSL 465
		p.put("mail.smtp.starttls.enable", "true");
		p.put("mail.smtp.auth", "true");
		p.put("mail.smtp.debug", "true");
		p.put("mail.smtp.socketFactory.port", "465"); 
		p.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		p.put("mail.smtp.sockerFactory.fallback", "false");

		try {
			// 관리자 이메일 인증
			Authenticator auth = new Gmail();
			Session ses = Session.getInstance(p, auth);
			ses.setDebug(true);
			
			// 메일 제목
			MimeMessage msg = new MimeMessage(ses);
			msg.setSubject(subject);
			
			// 메일 발송자
			Address fromAddr = new InternetAddress(from);
			msg.setFrom(fromAddr);
			
			// 메일 수신자 및 보낼 메일 내용
			Address toAddr = new InternetAddress(to);
			msg.addRecipient(Message.RecipientType.TO, toAddr);
			msg.setContent(content, "text/html; charset=UTF8");
			
			// 메일 발송
			Transport.send(msg);
			
			// 정상적으로 보냈음 확인하는 문자열 리턴
			return "success";
		} catch (Exception e) {
			return "fail";
		}
	}
}
