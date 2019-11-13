package com.sample.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sample.action.Action;
import com.sample.dao.BoardDao;
import com.sample.model.Board;
import com.sample.model.User;
import com.sample.util.Script;

public class BoardUpdateAction implements Action{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 요청에 같이 넘어온 데이터
		String requestId = request.getParameter("id");
		String categoryId = request.getParameter("category");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		// 넘어온 데이터가 null 또는 공백일 경우 return : 필수 값만 체크
		if(requestId == null || requestId.equals("")) {
			return;
		} else if(categoryId == null || categoryId.equals("")) {
			return;
		} else if(title == null || title.equals("")) {
			return;
		}
		
		// 세션에 저장된 유저 아이디 가져오기
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		int userId = user.getId();
		
		// DB에 저장하기 위해 board모델에 변수 저장
		Board board = new Board();
		board.setId(Integer.parseInt(requestId));
		board.setCategoryId(Integer.parseInt(categoryId));
		board.setTitle(title);
		board.setContent(content);
		board.setUpdId(userId);
		
		// DB에 저장하고 실행결과 값을 받음
		BoardDao dao = BoardDao.getInstance();
		int resultCnt = dao.update(board);
		
		// 정상적으로 저장되었다면 상세 페이지로 이동, 안되었으면 알림창과 함께 이전페이지로 이동
		if(resultCnt == 1) {
			Script.location(response, "글을 수정하였습니다.", "/blogNew/board?cmd=detail&id="+requestId);
		} else {
			Script.back(response, "글 수정에 실패하였습니다.\\n다시 시도해 주세요.");
		}
	}

}
