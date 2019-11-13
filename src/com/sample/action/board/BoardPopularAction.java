package com.sample.action.board;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sample.action.Action;
import com.sample.dao.BoardDao;
import com.sample.model.Board;
import com.sample.util.Preview;

public class BoardPopularAction implements Action{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// DB에서 조회수가 높은 글 3개를 가져옴
		BoardDao dao = BoardDao.getInstance();
		List<Board> boards = dao.findOrderByReadCountDesc();
		
		// Gson 이용해서 json으로 변환해서 응답
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.S").create();
		response.setContentType("application/json; charset=UTF-8");
		String boardJson = "";
		
		if(boards.size() > 0) {
			// 미리보기 이미지 세팅
			Preview.setPreviewImg(boards);
			boardJson = gson.toJson(boards);
		} else {
			Board board = new Board();
			board.setId(0);
			board.setTitle("게시글이 존재하지 않습니다.");
			boardJson = gson.toJson(board);
		}
		
		PrintWriter out = response.getWriter();
		out.print(boardJson);
		out.flush();
	}

}
