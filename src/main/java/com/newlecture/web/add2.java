package com.newlecture.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/add2")
public class add2 extends HttpServlet {
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/hrml; charset=UTF-8");

		PrintWriter out = resp.getWriter();

		String[] val_ = req.getParameterValues("val") ;	

		int result = 0;
		
		for(int i=0; i< val_.length; i++) {
			int  val = Integer.parseInt(val_[i]);
			result += val;
		}
		
		out.printf("result is %d\n", result);

	}
	
}
