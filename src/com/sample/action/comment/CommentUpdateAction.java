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

public class CommentUpdateAction implements Action{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 요청에 같이 넘어온 데이터
		String id = request.getParameter("id");
		String content = request.getParameter("content");
		String userId = request.getParameter("userId");
		
		// 넘어온 데이터가 null 또는 공백일 경우 return
		if(id == null || id.equals("")) {
			return;
		} else if(content == null || content.equals("")) {
			return;
		} else if(userId == null || userId.equals("")) {
			return;
		}
		
		// DB에 저장하기 위해 comment모델에 변수 저장
		Comment comment = new Comment();
		comment.setId(Integer.parseInt(id));
		comment.setContent(content);
		comment.setUpdId(Integer.parseInt(userId));
		
		// DB에 저장하고 실행결과 값을 받음
		CommentDao dao = CommentDao.getInstance();
		int resultCnt = dao.update(comment);
		
		// 정상적으로 저장되었다면 json으로 변환해서 응답, 저장되지 않았을 경우 알림창
		if(resultCnt == 1) {
			Comment updateComment = dao.findById(Integer.parseInt(id));
			
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.S").create();
			String updateCommentJson = gson.toJson(updateComment);

			response.setContentType("application/json; charset=UTF-8");
			
			PrintWriter out = response.getWriter();
			out.print(updateCommentJson);
			out.flush();
		} else {
			Script.back(response, "댓글 수정에 실패하였습니다.\\n다시 시도해 주세요.");
		}
	}

}
