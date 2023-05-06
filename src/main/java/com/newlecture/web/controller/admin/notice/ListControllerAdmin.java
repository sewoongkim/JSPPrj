package com.newlecture.web.controller.admin.notice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newlecture.web.Service.NoticeService;
import com.newlecture.web.entity.NoticeView;

@WebServlet("/admin/board/notice/list")
public class ListControllerAdmin extends HttpServlet{
		
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String[] openIds = request.getParameterValues("open-id");
		String[] delIds = request.getParameterValues("del-id");

		String cmd = request.getParameter("cmd");
		String ids_ = request.getParameter("ids");
		String[] ids = ids_.trim().split(" ");

		NoticeService service = new NoticeService();
		
		
		switch(cmd) {
		case "일괄공개": 
			for(String openId : openIds) 
				System.out.printf("open Id : %s\n", openId);
			
			List<String> oids = Arrays.asList(openIds);
			List<String> cids = new ArrayList(Arrays.asList(ids));
			cids.removeAll(oids);
	
			System.out.print("ids : ");
			System.out.println(Arrays.asList(ids));

			System.out.print("oids : ");
			System.out.println(oids);


			System.out.print("cids : ");
			System.out.println(cids);
			
//			for(int i=0; i<ids.length; i++ ) {
//				if (oids.contains(ids[i]))
//						pub-> 1;
//					else
//						pub-> 0;
//					
//			}
			service.pubNoticeAll(oids,cids);
//			service.closeNoticeList(clsIds);
			
			break;
		case "일괄삭제": 
			int[] ids1 = new int[delIds.length];
			for (int i=0; i<delIds.length; i++)
				ids1[i] = Integer.parseInt(delIds[i]);
			int result = service.deleteNoticeAll(ids1);
			break;
		}
		
		response.sendRedirect("list");
	}
	
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
		List<NoticeView> list = service.getNoticeList(field,query,page);
		
		int count = service.getNoticeCount(field,query);
		
		request.setAttribute("list",list);
		request.setAttribute("count",count);
		System.out.println(list);

		request
		.getRequestDispatcher("/WEB-INF/view3/admin/board/notice/list.jsp")
		.forward(request,response);	
	}
}
