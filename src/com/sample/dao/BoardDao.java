package com.sample.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sample.model.Board;
import com.sample.util.DBClose;

public class BoardDao {
	private BoardDao(){}
	private static BoardDao instance = new BoardDao();
	
	public static BoardDao getInstance() {
		return instance;
	}
	
	private static Connection conn;
	private static PreparedStatement pstmt;
	private static ResultSet rs;
	
	// 글 쓰기
	public int save(Board board) {
		int resultCnt = -1;
		final String SQL = "INSERT INTO board VALUES(null, ?, ?, ?, 0, false, ?, now(), null, null)";
		conn = DBConn.getConnection();
		
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, board.getCategoryId());
			pstmt.setString(2, board.getTitle());
			pstmt.setString(3, board.getContent());
			pstmt.setInt(4, board.getInsId());
			resultCnt = pstmt.executeUpdate();
			
			return resultCnt;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt);
		}
		
		return resultCnt;
	}
	
	// 글 갯수
	public int findCountAll(int categoryId){
		final String SQL = "SELECT count(*) FROM board WHERE categoryId = ? AND delFg = false";
		conn = DBConn.getConnection();
		int resultCnt = -1;
		
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, categoryId);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				resultCnt = rs.getInt(1);
			}
			
			return resultCnt;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt, rs);
		}
		
		return resultCnt;
	}
	
	// 글 목록
	public List<Board> findAll(int categoryId, int page){
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT b.*, ifnull(u.username, '-') as username, profile ");
		sb.append("FROM board b ");
		sb.append("LEFT OUTER JOIN user u ON b.insId = u.id ");
		sb.append("WHERE b.categoryId = ? AND b.delFg = false ");
		sb.append("ORDER BY b.id DESC limit ?, 3");
		
		final String SQL = sb.toString();
		conn = DBConn.getConnection();
		
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, categoryId);
			pstmt.setInt(2, (page-1)*3);	// 1페이지면 0부터, 2페이지면 3부터 시작이므로 페이지-1 한 값에 3을 곱함
			rs = pstmt.executeQuery();
			
			List<Board> boards = new ArrayList<Board>();
			
			while(rs.next()) {
				Board board = new Board();
				board.setId(rs.getInt("id"));
				board.setTitle(rs.getString("title"));
				board.setContent(rs.getString("content"));
				board.setReadCount(rs.getInt("readCount"));
				board.setInsId(rs.getInt("insId"));
				board.setInsDt(rs.getTimestamp("insDt"));
				board.setUsername(rs.getString("username"));
				board.setProfile(rs.getString("profile"));
				
				boards.add(board);
			}
			
			return boards;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt, rs);
		}
		
		return null;
	}
	
	// 인기 목록
	public List<Board> findOrderByReadCountDesc(){
		final String SQL = "SELECT * FROM board WHERE delFg = false ORDER BY readCount DESC, insDt DESC limit 3";
		conn = DBConn.getConnection();
		
		try {
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			
			List<Board> boards = new ArrayList<Board>();
			
			while(rs.next()) {
				Board board = new Board();
				board.setId(rs.getInt("id"));
				board.setTitle(rs.getString("title"));
				board.setContent(rs.getString("content"));
				board.setInsDt(rs.getTimestamp("insDt"));
				
				boards.add(board);
			}
			
			return boards;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt, rs);
		}
		
		return null;
	}
	
	// 최근 목록
	public List<Board> findOrderByInsDtDesc(){
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT b.*, ifnull(u.username, '-') as username, profile ");
		sb.append("FROM board b ");
		sb.append("LEFT OUTER JOIN user u ON b.insId = u.id ");
		sb.append("WHERE b.delFg = false ");
		sb.append("ORDER BY insDt DESC limit 8");
		
		final String SQL = sb.toString();
		conn = DBConn.getConnection();
		
		try {
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			
			List<Board> boards = new ArrayList<Board>();
			
			while(rs.next()) {
				Board board = new Board();
				board.setId(rs.getInt("id"));
				board.setTitle(rs.getString("title"));
				board.setContent(rs.getString("content"));
				board.setReadCount(rs.getInt("readCount"));
				board.setInsDt(rs.getTimestamp("insDt"));
				board.setUsername(rs.getString("username"));
				board.setProfile(rs.getString("profile"));
				
				boards.add(board);
			}
			
			return boards;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt, rs);
		}
		
		return null;
	}
	
	// 글 상세
	public Board findById(int id) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT *, ifnull(u.username, '-') as username, profile ");
		sb.append("FROM board b ");
		sb.append("LEFT OUTER JOIN user u ON b.insId = u.id ");
		sb.append("WHERE b.id = ?");
		
		final String SQL = sb.toString();
		conn = DBConn.getConnection();
		
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				Board board = new Board();
				board.setId(rs.getInt("id"));
				board.setCategoryId(rs.getInt("categoryId"));
				board.setTitle(rs.getString("title"));
				board.setContent(rs.getString("content"));
				board.setReadCount(rs.getInt("readCount"));
				board.setInsId(rs.getInt("insId"));
				board.setInsDt(rs.getTimestamp("insDt"));
				board.setUsername(rs.getString("username"));
				board.setProfile(rs.getString("profile"));
				
				return board;
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt, rs);
		}
		
		return null;
	}
	
	// 조회수 증가
	public int increaseReadCount(int id) {
		int resultCnt = -1;
		
		final String SQL = "UPDATE board SET readCount = readCount+1 WHERE id = ?";
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
	
	// 글 삭제
	public int delete(int id) {
		int resultCnt = -1;
		
		final String SQL = "UPDATE board SET delFg = true WHERE id = ?";
		//final String SQL = "DELETE FROM board WHERE id = ?";
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
	
	// 글 수정
	public int update(Board board) {
		int resultCnt = -1;
		
		final String SQL = "UPDATE board SET categoryId = ?, title = ?, content = ?, updId = ?, updDt = now() WHERE id = ?";
		conn = DBConn.getConnection();
		
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, board.getCategoryId());
			pstmt.setString(2, board.getTitle());
			pstmt.setString(3, board.getContent());
			pstmt.setInt(4, board.getUpdId());
			pstmt.setInt(5, board.getId());
			resultCnt = pstmt.executeUpdate();
			
			return resultCnt;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt);
		}
		
		return resultCnt;
	}
	
	// 검색 글 조회
	public List<Board> findAll(int categoryId, int page, String search){
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT b.*, ifnull(u.username, '-') as username, profile ");
		sb.append("FROM board b ");
		sb.append("LEFT OUTER JOIN user u ON b.insId = u.id ");
		sb.append("WHERE b.delFg = false AND (b.title LIKE ? OR b.content LIKE ?) ");
		sb.append("ORDER BY b.id DESC limit ?, 3");
		
		final String SQL = sb.toString();
		conn = DBConn.getConnection();
		
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, "%" + search + "%");
			pstmt.setString(2, "%" + search + "%");
			pstmt.setInt(3, (page-1)*3);	// 1페이지면 0부터, 2페이지면 3부터 시작이므로 페이지-1 한 값에 3을 곱함
			rs = pstmt.executeQuery();
			
			List<Board> boards = new ArrayList<Board>();
			
			while(rs.next()) {
				Board board = new Board();
				board.setId(rs.getInt("id"));
				board.setTitle(rs.getString("title"));
				board.setContent(rs.getString("content"));
				board.setReadCount(rs.getInt("readCount"));
				board.setInsId(rs.getInt("insId"));
				board.setInsDt(rs.getTimestamp("insDt"));
				board.setUsername(rs.getString("username"));
				board.setProfile(rs.getString("profile"));
				
				boards.add(board);
			}
			
			return boards;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt, rs);
		}
		
		return null;
	}
	
	// 글 갯수 : 검색결과
	public int findCountAll(int categoryId, String search){
		final String SQL = "SELECT count(*) FROM board WHERE delFg = false AND (title LIKE ? AND content LIKE ?)";
		conn = DBConn.getConnection();
		int resultCnt = -1;
		
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, "%" + search + "%");
			pstmt.setString(2, "%" + search + "%");
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				resultCnt = rs.getInt(1);
			}
			
			return resultCnt;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt, rs);
		}
		
		return resultCnt;
	}
}
