package com.sample.action.category;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.sample.action.Action;
import com.sample.dao.CategoryDao;
import com.sample.model.Category;

public class CategoryListAction implements Action{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// DB에서 실행결과 값(카테고리 리스트)을 받음
		CategoryDao dao = CategoryDao.getInstance();
		List<Category> categories = dao.findAll();
		
		// Gson 이용해서 json으로 변환해서 응답
		Gson gson = new Gson();
		response.setContentType("application/json; charset=UTF-8");
		String categoryJson = "";
		
		if(categories != null && categories.size() > 0) {
			categoryJson = gson.toJson(categories);
		} else {
			Category category = new Category();
			category.setId(0);
			category.setName("카테고리가 존재하지 않습니다.");
			categoryJson = gson.toJson(category);
		}
		
		PrintWriter out = response.getWriter();
		out.print(categoryJson);
		out.flush();
	}

}
