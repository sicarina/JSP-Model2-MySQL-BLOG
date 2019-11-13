package com.sample.action.comment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sample.action.Action;
import com.sample.dao.CommentDao;
import com.sample.model.Comment;

public class CommentViewAllAction implements Action{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 요청에 같이 넘어온 데이터
		BufferedReader in = request.getReader();
		String requestId = in.readLine();
		
		// 넘어온 데이터가 null 또는 공백일 경우 처리
		if(requestId == null || requestId.equals("")) {
			return;
		}
		
		int commentId = Integer.parseInt(requestId);
		
		// 댓글을 받아 옴
		CommentDao dao = CommentDao.getInstance();
		List<Comment> comments = dao.findByCommentId(commentId);
		
		// Gson 이용해서 json으로 변환해서 응답
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.S").create();
		String commentJson = "";
		
		// 댓글이 있으면 댓글리스트를, 없으면 댓글ID에 0을 보냄
		if(comments.size() > 0) {
			commentJson = gson.toJson(comments);
		} else {
			Comment comment = new Comment();
			comment.setId(0);
			commentJson = gson.toJson(comment);
		}

		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(commentJson);
		out.flush();
	}

}
