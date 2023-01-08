package com.newlecture.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/add")
public class add extends HttpServlet {
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/hrml; charset=UTF-8");

		PrintWriter out = resp.getWriter();

		String val1_ = req.getParameter("val1") ;	
		String val2_ = req.getParameter("val2") ;	
		
		int val1 = 0;
		int val2 = 0;

		if (val1_ != null && !val1_.equals(""))  val1 = Integer.parseInt(val1_);
		if (val2_ != null && !val2_.equals(""))  val2 = Integer.parseInt(val2_);
		
		int result = val1 + val2;
		// out.println("val1+val2 : "  + (val1 + val2));
		
		out.printf("result is %d\n", result);

	}
	
}
