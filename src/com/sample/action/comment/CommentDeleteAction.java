package com.sample.action.comment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sample.action.Action;
import com.sample.dao.CommentDao;
import com.sample.util.Script;

public class CommentDeleteAction implements Action{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 요청에 같이 넘어온 데이터
		BufferedReader in = request.getReader();
		String requestData = in.readLine();
		
		int commentId = Integer.parseInt(requestData);
		
		CommentDao dao = CommentDao.getInstance();
		int resultCnt = dao.delete(commentId);
		
		if(resultCnt == 1) {
			PrintWriter out = response.getWriter();
			out.print("ok");
			out.flush();
		} else {
			Script.back(response, "댓글이 존재하지 않습니다.");
		}
	}

}
