package com.newlecture.web.controller.notice;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newlecture.web.Service.NoticeService;
import com.newlecture.web.entity.Notice;
import com.newlecture.web.entity.NoticeView;

@WebServlet("/notice/list")
public class ListController extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//list?f=title&q=a
		String field_ = request.getParameter("f");
		String query_ = request.getParameter("q");
		String page_ = request.getParameter("p");

		String field = "title";
		String query = "";
		int page = 1;
		
		if(field_ != null && !field_.equals(""))
			field = field_;
		if(query_ != null  && !query_.equals(""))
			query = query_;
		if(page_ != null  && !page_.equals(""))
			page = Integer.parseInt(page_);
		
		NoticeService service = new NoticeService();
		List<NoticeView> list = service.getNoticePubList(field,query,page);
		
		int count = service.getNoticeCount(field,query);
		
		request.setAttribute("list",list);
		request.setAttribute("count",count);
		
		request
		.getRequestDispatcher("/WEB-INF/view3/notice/list.jsp")
		.forward(request,response);	
	}
}
