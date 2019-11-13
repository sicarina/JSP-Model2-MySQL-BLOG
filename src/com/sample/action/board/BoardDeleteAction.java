package com.sample.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sample.action.Action;
import com.sample.dao.BoardDao;
import com.sample.util.Script;

public class BoardDeleteAction implements Action{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 요청에 같이 넘어온 데이터
		String requestId = request.getParameter("id");
	
		// 넘어온 데이터가 null 또는 공백일 경우 처리
		if(requestId == null || requestId.equals("")) {
			return;
		}
		
		int id = Integer.parseInt(requestId);
		
		// DB에서 지정한 게시글을 삭제
		BoardDao dao = BoardDao.getInstance();
		int resultCnt = dao.delete(id);
		
		if(resultCnt == 1) {
			Script.location(response, "글 삭제에 성공하였습니다.\\n메인페이지로 이동합니다.", "/blogNew/index.jsp");
		} else {
			Script.back(response, "글 삭제에 실패하였습니다.\\n다시 시도해 주세요.");
		}
	}

}
