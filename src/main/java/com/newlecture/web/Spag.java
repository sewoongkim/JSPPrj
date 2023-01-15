package com.newlecture.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/spag")
public class Spag extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		int num = 0; 
		String result = "";
		String num_ = req.getParameter("n");
		if (num_  != null  && !num_.equals(""))
			num = Integer.parseInt(num_);
		
		if(num%2 != 0)
			result = "홀수";
		else
			result = "짝수";
		
		//redirect
		//forward 
		
		req.setAttribute("result", result);
		RequestDispatcher disPatcher
			= req.getRequestDispatcher("spag.jsp");
		disPatcher.forward(req,resp);
		
		// super.doGet(req, resp);
	}
}
