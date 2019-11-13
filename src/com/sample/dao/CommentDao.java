package com.sample.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sample.model.Comment;
import com.sample.util.DBClose;

public class CommentDao {
	private CommentDao(){}
	private static CommentDao instance = new CommentDao();
	
	public static CommentDao getInstance() {
		return instance;
	}
	
	private static Connection conn;
	private static PreparedStatement pstmt;
	private static ResultSet rs;

	// 댓글 저장
	public int save(Comment comment) {
		int resultCnt = -1;
		
		final String SQL = "INSERT INTO comment VALUES(null, ?, ?, ?, false, ?, now(), null, null)";
		conn = DBConn.getConnection();
		
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, comment.getBoardId());
			pstmt.setString(2, comment.getContent());
			pstmt.setInt(3, comment.getCommentId());
			pstmt.setInt(4, comment.getInsId());
			resultCnt = pstmt.executeUpdate();
			
			return resultCnt;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt);
		}
		
		return resultCnt;
	}
	
	// 가장 최근에 작성한 댓글 가져오기 : 방금 작성한 댓글의 아이디를 받아와서 해당 댓글 정보를 화면에 뿌려주는 용도
	public Comment findByMaxId() {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT c.*, ifnull(u.username, '-') as username, u.profile ");
		sb.append("FROM comment c ");
		sb.append("LEFT OUTER JOIN user u on c.insId = u.id ");
		sb.append("WHERE c.id = (SELECT max(id) FROM comment)");
		
		final String SQL = sb.toString();
		conn = DBConn.getConnection();
		
		try {
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				Comment comment = new Comment();
				comment.setId(rs.getInt("id"));
				comment.setBoardId(rs.getInt("boardId"));
				comment.setContent(rs.getString("content"));
				comment.setCommentId(rs.getInt("commentId"));
				comment.setDelFg(rs.getBoolean("delFg"));
				comment.setInsId(rs.getInt("insId"));
				comment.setInsDt(rs.getTimestamp("insDt"));
				comment.setUsername(rs.getString("username"));
				comment.setProfile(rs.getString("profile"));
				
				return comment;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt, rs);
		}
		
		return null;
	}
	
	// 댓글 가져오기 : 게시글 기준
	public List<Comment> findByBoardId(int boardId){
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT c.*, ifnull(u.username, '-') as username, u.profile ");
		sb.append("FROM comment c ");
		sb.append("LEFT OUTER JOIN user u on c.insId = u.id ");
		sb.append("WHERE boardId = ? AND commentId = 0");
		
		final String SQL = sb.toString();
		conn = DBConn.getConnection();
		
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, boardId);
			rs = pstmt.executeQuery();
			
			List<Comment> comments = new ArrayList<Comment>();
			
			while(rs.next()) {
				Comment comment = new Comment();
				comment.setId(rs.getInt("id"));
				comment.setBoardId(rs.getInt("boardId"));
				comment.setContent(rs.getString("content"));
				comment.setCommentId(rs.getInt("commentId"));
				comment.setDelFg(rs.getBoolean("delFg"));
				comment.setInsId(rs.getInt("insId"));
				comment.setInsDt(rs.getTimestamp("insDt"));
				comment.setUsername(rs.getString("username"));
				comment.setProfile(rs.getString("profile"));
				
				comments.add(comment);
			}
			
			return comments;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt, rs);
		}
		
		return null;
	}
	
	// 댓글 삭제
	public int delete(int id) {
		int resultCnt = -1;
		
		//final String SQL = "UPDATE comment SET delFg = true WHERE id = ?";
		final String SQL = "DELETE FROM comment WHERE id = ?";
		conn = DBConn.getConnection();
		
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, id);
			resultCnt = pstmt.executeUpdate();
			
			return resultCnt;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt);
		}
		
		return resultCnt;
	}
	
	// 댓글 가져오기 : 댓글 아이디 기준
	public Comment findById(int id){
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT c.*, ifnull(u.username, '-') as username, u.profile ");
		sb.append("FROM comment c ");
		sb.append("LEFT OUTER JOIN user u on c.insId = u.id ");
		sb.append("WHERE c.id = ?");
		
		final String SQL = sb.toString();
		conn = DBConn.getConnection();
		
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				Comment comment = new Comment();
				comment.setId(rs.getInt("id"));
				comment.setContent(rs.getString("content"));
				comment.setInsDt(rs.getTimestamp("insDt"));
				comment.setUsername(rs.getString("username"));
				comment.setProfile(rs.getString("profile"));
				
				return comment;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt, rs);
		}
		
		return null;
	}
	
	// 댓글 수정
	public int update(Comment comment) {
		int resultCnt = -1;
		
		final String SQL = "UPDATE comment SET content = ?, updId = ?, updDt = now() WHERE id = ?";
		conn = DBConn.getConnection();
		
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, comment.getContent());
			pstmt.setInt(2, comment.getUpdId());
			pstmt.setInt(3, comment.getId());
			resultCnt = pstmt.executeUpdate();
			
			return resultCnt;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt);
		}
		
		return resultCnt;
	}
	
	// 댓글 가져오기 : 상위댓글 기준
	public List<Comment> findByCommentId(int commentId){
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT c.*, ifnull(u.username, '-') as username, u.profile ");
		sb.append("FROM comment c ");
		sb.append("LEFT OUTER JOIN user u on c.insId = u.id ");
		sb.append("WHERE commentId = ?");
		
		final String SQL = sb.toString();
		conn = DBConn.getConnection();
		
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, commentId);
			rs = pstmt.executeQuery();
			
			List<Comment> comments = new ArrayList<Comment>();
			
			while(rs.next()) {
				Comment comment = new Comment();
				comment.setId(rs.getInt("id"));
				comment.setBoardId(rs.getInt("boardId"));
				comment.setContent(rs.getString("content"));
				comment.setCommentId(rs.getInt("commentId"));
				comment.setDelFg(rs.getBoolean("delFg"));
				comment.setInsId(rs.getInt("insId"));
				comment.setInsDt(rs.getTimestamp("insDt"));
				comment.setUsername(rs.getString("username"));
				comment.setProfile(rs.getString("profile"));
				
				comments.add(comment);
			}
			
			return comments;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt, rs);
		}
		
		return null;
	}
}
