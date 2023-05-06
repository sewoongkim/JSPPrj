package com.newlecture.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		
		// List list = new ArrayList(){"1","","test"};
		
		String[] names = {"newlec","dragon"};
		
		Map<String, Object> notice = new HashMap<String, Object>();
		notice.put("id",1);
		notice.put("title","좋아요");

		req.setAttribute("notice",notice);		
		
		req.setAttribute("names",names);		
		
		req.setAttribute("result", result);
		
		RequestDispatcher disPatcher
			= req.getRequestDispatcher("spag.jsp");
		
		disPatcher.forward(req,resp);
		
		// super.doGet(req, resp);
	}
}
