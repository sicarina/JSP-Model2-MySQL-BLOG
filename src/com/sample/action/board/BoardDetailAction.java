package com.sample.action.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sample.action.Action;
import com.sample.dao.BoardDao;
import com.sample.dao.CommentDao;
import com.sample.model.Board;
import com.sample.model.Comment;
import com.sample.util.Preview;
import com.sample.util.Script;

public class BoardDetailAction implements Action{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 요청에 같이 넘어온 데이터
		String requestId = request.getParameter("id");
		
		// 넘어온 데이터가 null 또는 공백일 경우 처리
		if(requestId == null || requestId.equals("")) {
			return;
		}
		
		int id = Integer.parseInt(requestId);
		
		// DB에서 지정한 게시글을 받아 옴
		BoardDao dao = BoardDao.getInstance();
		Board board = dao.findById(id);
		
		// 해당 게시글의 댓글을 받아 옴
		CommentDao cDao = CommentDao.getInstance();
		List<Comment> comments = cDao.findByBoardId(id);
		
		// 글을 받아왔으면 조회수 증가 처리 및 해당 페이지 리턴, 글이 없을 경우 이전 페이지로 이동
		if(board != null) {
			int resultCnt = -1;

			// 새로고침시 조회수 증가를 막기 위해 쿠키 사용
			Cookie[] cookies = request.getCookies();
			Cookie readedCookie = null;
			
			// 저장된 쿠키가 있으면 readed 쿠키를 찾아서 저장 (readed:이미 접근한 글) 
			if(cookies != null && cookies.length > 0) {
				for(int i=0; i<cookies.length; i++) {
					if(cookies[i].getName().equals("readed")) {
						readedCookie = cookies[i];
						break;
					}
				}
			}
			
			// readed쿠키가 없으면 조회수 증가처리 및 쿠키 생성, 있으면 접근하지 않았던 글이면 조회수 증가, 접근했던 글이면 패스
			if(readedCookie == null) {
				Cookie newReadedCookie = new Cookie("readed", id+"");
				response.addCookie(newReadedCookie);
				
				resultCnt = dao.increaseReadCount(id);
			} else {
				Boolean checkNew = true;
				String cookieVal = readedCookie.getValue();
				String splitCookieVal[] = cookieVal.split("\\|");
				
				// readed쿠키가 있는 경우 |를 기준으로 문자열을 분리해 접근한 게시글 ID와 일치하는게 있는지 확인
				for(int i=0; i<splitCookieVal.length; i++) {
					if(splitCookieVal[i].equals(id+"")) {
						checkNew = false;
						break;
					}
				}
				
				// 접근한 게시글 ID와 일치하는게 없으면 쿠키에 해당 ID 저장하고 조회수 증가, 있으면 패스 
				if(checkNew == true) {
					String cookieNewVal = readedCookie.getValue() + "|" + id;
					readedCookie.setValue(cookieNewVal);

					response.addCookie(readedCookie);
					
					resultCnt = dao.increaseReadCount(id);
				} else {
					resultCnt = 1;
				}
			}
			
			// 쿠키 처리가 끝난 후 
			if(resultCnt == 1) {
				if(board != null) {
					// 유튜브 링크 게시글에서 바로보기 지원
					Preview.setPreviewYoutube(board);
				}
				
				request.setAttribute("board", board);
				request.setAttribute("comments", comments);
				RequestDispatcher dis = request.getRequestDispatcher("/board/detail.jsp");
				dis.forward(request, response);
			} else {
				Script.back(response, "게시글을 불러오는 데 실패하였습니다.\\n다시 시도해 주세요.");
			}
		} else {
			Script.back(response, "해당 게시글이 없습니다.");
		}
	}

}
