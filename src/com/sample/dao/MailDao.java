package com.sample.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.sample.model.User;
import com.sample.util.DBClose;

public class MailDao {
	private MailDao(){}
	private static MailDao instance = new MailDao();
	
	public static MailDao getInstance() {
		return instance;
	}
	
	private static Connection conn;
	private static PreparedStatement pstmt;
	private static ResultSet rs;
	
	// 메일 정보 가져오기
	public User findAll() {
		final String SQL = "SELECT * FROM adminInfo";
		conn = DBConn.getConnection();
		
		try {
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();

			if(rs.next()) {
				User user = new User();
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				
				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt, rs);
		}
		
		return null;
	}
}
