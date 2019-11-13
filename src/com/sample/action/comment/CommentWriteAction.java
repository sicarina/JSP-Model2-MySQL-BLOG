package com.sample.action.comment;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sample.action.Action;
import com.sample.dao.CommentDao;
import com.sample.model.Comment;
import com.sample.util.Script;

public class CommentWriteAction implements Action{

	@Override
	synchronized public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 요청에 같이 넘어온 데이터
		String content = request.getParameter("content");
		String boardId = request.getParameter("boardId");
		String commentId = request.getParameter("commentId");
		String userId = request.getParameter("userId");
		
		// 넘어온 데이터가 null 또는 공백일 경우 return
		if(content == null || content.equals("")) {
			return;
		} else if(boardId == null || boardId.equals("")) {
			return;
		} else if(commentId == null || commentId.equals("")) {
			return;
		} else if(userId == null || userId.equals("")) {
			return;
		}
		
		// DB에 저장하기 위해 comment모델에 변수 저장
		Comment comment = new Comment();
		comment.setBoardId(Integer.parseInt(boardId));
		comment.setContent(content);
		comment.setCommentId(Integer.parseInt(commentId));
		comment.setInsId(Integer.parseInt(userId));
		
		// DB에 저장하고 실행결과 값을 받음
		CommentDao dao = CommentDao.getInstance();
		int resultCnt = dao.save(comment);
		
		// 정상적으로 저장되었다면 json으로 변환해서 응답, 저장되지 않았을 경우 알림창
		if(resultCnt == 1) {
			Comment newComment = dao.findByMaxId();
			
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.S").create();
			String newCommentJson = gson.toJson(newComment);

			response.setContentType("application/json; charset=UTF-8");
			
			PrintWriter out = response.getWriter();
			out.print(newCommentJson);
			out.flush();
		} else {
			Script.back(response, "댓글 저장에 실패하였습니다.\\n다시 시도해 주세요.");
		}
	}
}
