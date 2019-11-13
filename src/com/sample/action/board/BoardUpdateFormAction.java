package com.sample.action.board;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sample.action.Action;
import com.sample.dao.BoardDao;
import com.sample.model.Board;
import com.sample.util.Script;

public class BoardUpdateFormAction implements Action{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 요청에 같이 넘어온 데이터
		String requestId = request.getParameter("id");
	
		// 넘어온 데이터가 null 또는 공백일 경우 처리
		if(requestId == null || requestId.equals("")) {
			return;
		}
		
		int id = Integer.parseInt(requestId);
		
		// DB에서 지정한 게시글을 가져와 수정폼에 넘김
		BoardDao dao = BoardDao.getInstance();
		Board board = dao.findById(id);
		
		if(board != null) {
			request.setAttribute("board", board);
			RequestDispatcher dis = request.getRequestDispatcher("/board/update.jsp");
			dis.forward(request, response);
		} else {
			Script.back(response, "해당하는 게시글이 존재하지 않습니다.\\n다시 시도해 주세요.");
		}
	}

}
