package com.newlecture.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet ("/hi")
public class example13 extends HttpServlet {

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		// TODO Auto-generated method stub
		// super.service(req, resp);
		
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/hrml; charset=UTF-8");

		PrintWriter out = resp.getWriter();

		int cnt = 100; 
		
		String cnt_ = req.getParameter("cnt") ;		
		
		if (cnt_ != null  && !cnt_.equals("")) 
			cnt =  Integer.parseInt(req.getParameter("cnt"));
		
		
		for(Integer i = 0; i < cnt ;  i++) 
			out.println((i+1) + " : Hello Servlet!!  안녕 <BR>");
	
	}
}
