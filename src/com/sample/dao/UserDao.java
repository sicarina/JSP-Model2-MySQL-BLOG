package com.sample.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.sample.model.User;
import com.sample.util.DBClose;

public class UserDao {
	private UserDao(){}
	private static UserDao instance = new UserDao();
	
	public static UserDao getInstance() {
		return instance;
	}
	
	private static Connection conn;
	private static PreparedStatement pstmt;
	private static ResultSet rs;
	
	// 회원 정보 저장 : 회원가입시 시용
	public int save(User user) {
		int resultCnt = -1;
		final String SQL = "INSERT INTO user VALUES(null, ?, ?, ?, ?, false, ?, '/blogNew/img/user.png', now())";
		conn = DBConn.getConnection();
		
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getSalt());
			pstmt.setString(4, user.getEmail());
			pstmt.setString(5, user.getAddress());
			resultCnt = pstmt.executeUpdate();
			
			return resultCnt;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt);
		}
		
		return resultCnt;
	}
	
	// 회원 정보 가져오기 : ID중복체크, 로그인/비밀번호변경(솔트), 회원정보확인, 메일인증 확인에 사용
	public User findByUsername(String username) {
		final String SQL = "SELECT * FROM user WHERE username = ?";
		conn = DBConn.getConnection();
		
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, username);
			rs = pstmt.executeQuery();

			if(rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setSalt(rs.getString("salt"));
				user.setEmail(rs.getString("email"));
				user.setEmailChk(rs.getBoolean("emailChk"));
				user.setAddress(rs.getString("address"));
				user.setProfile(rs.getString("profile"));
				
				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt, rs);
		}
		
		return null;
	}
	
	// 이메일체크 필드 수정 : 이메일 인증시 사용
	public int updateEmailChk(String username) {
		int resultCnt = -1;
		final String SQL = "UPDATE user SET emailChk = true WHERE username = ?";
		conn = DBConn.getConnection();
		
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, username);
			resultCnt = pstmt.executeUpdate();
			
			return resultCnt;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt);
		}
		
		return resultCnt;
	}
	
	// 회원정보 가져오기 : 로그인시 시용
	public int findByUsernameAndPassword(User user) {
		int resultCnt = -1;
		final String SQL = "SELECT count(*) FROM user WHERE username = ? AND password = ?";
		conn = DBConn.getConnection();
		
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getPassword());
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				resultCnt = rs.getInt(1);
			}
			
			return resultCnt;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt, rs);
		}
		
		return resultCnt;
	}
	
	// 회원정보 변경
	public int update(User user) {
		int resultCnt = -1;
		final String SQL = "UPDATE user SET email = ?, emailChk = ?, address = ? WHERE id = ?";
		conn = DBConn.getConnection();
		
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, user.getEmail());
			pstmt.setBoolean(2, user.isEmailChk());
			pstmt.setString(3, user.getAddress());
			pstmt.setInt(4, user.getId());
			resultCnt = pstmt.executeUpdate();
			
			return resultCnt;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt);
		}
		
		return resultCnt;
	}
	
	// 비밀번호 변경
	public int updatePassword(User user) {
		int resultCnt = -1;
		final String SQL = "UPDATE user SET password = ? WHERE id = ?";
		conn = DBConn.getConnection();
		
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, user.getPassword());
			pstmt.setInt(2, user.getId());
			resultCnt = pstmt.executeUpdate();
			
			return resultCnt;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt);
		}
		
		return resultCnt;
	}
	
	// 프로필 사진 변경
	public int updateProfile(User user) {
		int resultCnt = -1;
		final String SQL = "UPDATE user SET profile = ? WHERE id = ?";
		conn = DBConn.getConnection();
		
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, user.getProfile());
			pstmt.setInt(2, user.getId());
			resultCnt = pstmt.executeUpdate();
			
			return resultCnt;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt);
		}
		
		return resultCnt;
	}
}
