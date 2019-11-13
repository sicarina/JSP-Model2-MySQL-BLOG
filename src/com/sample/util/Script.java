package com.sample.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class Script {
	public static void back(HttpServletResponse response, String msg) {
		response.setContentType("text/html; charset=UTF-8");
		
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println("<script>");
			out.println("alert('" + msg + "');");
			out.println("history.back();");
			out.println("</script>");
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void location(HttpServletResponse response, String msg, String url) {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println("<script>");
			out.println("alert('" + msg + "');");
			out.println("location.href = '" + url + "'");
			out.println("</script>");
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
