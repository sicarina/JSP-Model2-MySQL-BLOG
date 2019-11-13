package com.sample.action.comment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.sample.action.Action;
import com.sample.dao.CommentDao;
import com.sample.model.Comment;

public class CommentViewAction implements Action{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 요청에 같이 넘어온 데이터
		BufferedReader in = request.getReader();
		String requestId = in.readLine();
		
		// 넘어온 데이터가 null 또는 공백일 경우 return
		if(requestId == null || requestId.equals("")) {
			return;
		}
		
		// DB에서 실행결과 값을 받음
		CommentDao dao = CommentDao.getInstance();
		Comment comment = dao.findById(Integer.parseInt(requestId));

		// Gson 이용해서 json으로 변환해서 응답
		Gson gson = new Gson();
		response.setContentType("application/json; charset=UTF-8");
		String commentJson = gson.toJson(comment);
		
		PrintWriter out = response.getWriter();
		out.print(commentJson);
		out.flush();
	}

}
