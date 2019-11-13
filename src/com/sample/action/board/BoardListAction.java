package com.sample.action.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sample.action.Action;
import com.sample.dao.BoardDao;
import com.sample.dao.CategoryDao;
import com.sample.model.Board;
import com.sample.util.Preview;

public class BoardListAction implements Action{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 요청에 같이 넘어온 데이터
		String strCategoryId = request.getParameter("categoryId");
		String strPage = request.getParameter("page");
		
		// 넘어온 데이터가 null 또는 공백일 경우 처리
		if(strCategoryId == null || strCategoryId.equals("")) {
			return;
		} else if(strPage == null || strPage.equals("")) {
			return;
		}
		
		int categoryId = Integer.parseInt(strCategoryId);
		int page = Integer.parseInt(strPage);
		
		// DB연결
		BoardDao dao = BoardDao.getInstance();
		
		List<Board> boards;
		String categoryName;
		int boardCnt;
		
		// 검색 내용 분기처리(search하면 categoryId = 0)	// && (request.getParameter("search") == null || request.getParameter("search").equals(""))
		if(categoryId != 0) {
			// DB에서 해당 카테고리의 글을 모두 받아 오고 검색내용은 없으므로 null
			boards = dao.findAll(categoryId, page);
			request.setAttribute("search", null);
			
			// 화면에 띄워 줄 카테고리 이름을 가져옴
			categoryName = CategoryDao.getInstance().findById(categoryId);
			
			// DB에서 해당 카테고리의 글 갯수를 받아 옴
			boardCnt = dao.findCountAll(categoryId);
		} else {
			// 검색 내용 가져오기
			String search = request.getParameter("search");
			request.setAttribute("search", search);
			// DB에서 카테고리 상관없이 모든 글을 검색해서 가져옴
			boards = dao.findAll(categoryId, page, search);

			// 화면에 띄워 줄 카테고리 이름을 가져옴
			if(request.getParameter("search") == null || request.getParameter("search").equals("")) {
				categoryName = "검색결과(모든 글)";
			} else {
				categoryName = "'" + search + "'으로 검색한 결과";
			}
			
			// DB에서 해당 카테고리의 글 갯수를 받아 옴
			boardCnt = dao.findCountAll(categoryId, search);
		}
		
		// 페이징을 위해 마지막 페이지를 구함
		int maxPage = boardCnt / 3;
		
		if(boardCnt % 3 != 0) {
			maxPage++;
		}
		
		if(maxPage <= 0) {
			maxPage = 1;
		}
		
		if(page <= 0) {
			page = 1;
		} else if (page > maxPage) {
			page = maxPage;
		}

		// 들고온 게시글이 있으면 미리보기 내용 세팅
		if(boards != null) {
			Preview.setPreviewImg(boards);
			Preview.setPreviewContent(boards);
		}
		
		// 글과 마지막 페이지, 카테고리명을 요청에 담아 넘김
		request.setAttribute("boards", boards);
		request.setAttribute("maxPage", maxPage);
		request.setAttribute("categoryName", categoryName);
		
		RequestDispatcher dis = request.getRequestDispatcher("/board/list.jsp");
		dis.forward(request, response);
	}

}
