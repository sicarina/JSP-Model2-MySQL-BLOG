package com.sample.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.sample.model.Category;
import com.sample.util.DBClose;

public class CategoryDao {
	private CategoryDao(){}
	private static CategoryDao instance = new CategoryDao();
	
	public static CategoryDao getInstance() {
		return instance;
	}
	
	private static Connection conn;
	private static PreparedStatement pstmt;
	private static ResultSet rs;
	
	// 카테고리 전체 조회
	public List<Category> findAll() {
		final String SQL = "SELECT * FROM category";
		conn = DBConn.getConnection();
		
		try {
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			
			List<Category> categories = new ArrayList<Category>();
			
			while(rs.next()) {
				Category category = new Category();
				category.setId(rs.getInt("id"));
				category.setName(rs.getString("name"));
				
				categories.add(category);
			}
			
			return categories;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt, rs);
		}
		
		return null;
	}
	
	// 카테고리명 조회
	public String findById(int id) {
		final String SQL = "SELECT name FROM category WHERE id = ?";
		conn = DBConn.getConnection();
		
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				String name = rs.getString("name");

				return name;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt, rs);
		}
		
		return null;
	}
}
