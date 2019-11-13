package com.sample.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sample.action.Action;
import com.sample.action.board.BoardFactory;


@WebServlet("/board")
public class BoardCtr extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BoardCtr() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String cmd = request.getParameter("cmd");

		if (cmd == null || cmd.equals("")) {
			cmd="list";
		}

		Action action = BoardFactory.getAction(cmd);
		if (action != null) {
			action.execute(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
