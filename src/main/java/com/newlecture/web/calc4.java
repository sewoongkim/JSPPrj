package com.newlecture.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oracle.truffle.api.impl.TruffleLocator.Response;

@WebServlet("/calc4")
public class calc4 extends HttpServlet {
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		ServletContext application = req.getServletContext();

		resp.setCharacterEncoding("UTF-8");;
		resp.setContentType("text/html; charset=UTF-8");
		
		String v_ = req.getParameter("v");
		String op = req.getParameter("operator");
		
		PrintWriter out = resp.getWriter();

		int v = 0;
		if(!v_.equals("")) v = Integer.parseInt(v_);
		
		if (op.equals("=")) {
			int x = (Integer)application.getAttribute("value");
			int y =  v;
			String operator = (String)application.getAttribute("op");
			int result = 0;
			
			if(operator.equals("+"))
				result = x+y;
			else
				result = x-y;
			
			out.printf("result is %d\n",  result);
			resp.getWriter().printf("result is %d\n", result);
		} 
		else {
			application.setAttribute("value",v);
			application.setAttribute("op", op);
		}
	}		
}
