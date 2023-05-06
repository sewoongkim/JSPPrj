package com.newlecture.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/notice-reg1")
public class NoticeReg1 extends HttpServlet {
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/hrml; charset=UTF-8");

		PrintWriter out = response.getWriter();

		String title = request.getParameter("title") ;	
		String content = request.getParameter("content") ;	
		
		out.println(title);
		out.println(content);
		

	}

}