package com.newlecture.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/calc2")
public class calc2 extends HttpServlet {
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		ServletContext application =  req.getServletContext();

		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/hrml; charset=UTF-8");

		PrintWriter out = resp.getWriter();

		String v_ = req.getParameter("v") ;	
		String op  = req.getParameter("operator") ;
		
		int v = 0;
		if (v_ != null && !v_.equals(""))  v = Integer.parseInt(v_);
		
		// 값을 계산
		if (op.equals("=")) {
				int x = (Integer)application.getAttribute("value");
				int y = v;
				String operator = (String)application.getAttribute("op");
				int result =0;
				if (operator.equals("+"))
					result = x+y;
				else
					result = x-y;
				out.printf("result is %d\n",  result);
		}
		//값을 저장
		else {
			application.setAttribute("value", v);
			application.setAttribute("op", op);
		}
	}
}
