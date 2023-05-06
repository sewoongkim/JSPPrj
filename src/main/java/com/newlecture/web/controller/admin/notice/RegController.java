package com.newlecture.web.controller.admin.notice;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.newlecture.web.Service.NoticeService;
import com.newlecture.web.entity.Notice;

@MultipartConfig(
		fileSizeThreshold=1024*1024,
		maxFileSize = 1024*1024*50,
		maxRequestSize=1024*1024*50*5
		)
@WebServlet("/admin/board/notice/reg")
public class RegController extends HttpServlet {

	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String title = request.getParameter("title"); 
		String content = request.getParameter("content"); 
		String isOpen = request.getParameter("open"); 
		String fileNames = "";
		
		Collection<Part> parts =  request.getParts();
		StringBuilder builder = new StringBuilder();
		
		for (Part p : parts) {

			if (!p.getName().equals("file")) continue;
			if(p.getSize() == 0) continue;
			
			Part filePart = p;
			InputStream fis = filePart.getInputStream();

			String realpath = request.getServletContext().getRealPath("/upload");
			
			System.out.println(realpath);
			
			File path = new File(realpath);
			if (!path.exists())
					path.mkdirs();
			
			String fileName = filePart.getSubmittedFileName();
			
			builder.append(fileName);
			builder.append(",");
			
			String filepath = realpath  +File.separator  + fileName;
			
			FileOutputStream fos = new FileOutputStream(filepath);
			
			byte[] buf = new byte[1024];
			
			int size = 0;
			
			while((size=fis.read(buf)) != -1)
				 fos.write(buf,0,size);
			
			fos.close();
			fis.close();
		}
		
		builder.delete(builder.length()-1,builder.length());

		boolean  pub = false;
		if (isOpen != null) 
			 pub = true;
		
		Notice notice = new Notice();
		
		notice.setTitle(title);
		notice.setContext(content);
		notice.setPub(pub);
		notice.setwriteId("sewkim00");
		notice.setFiles(builder.toString());
		
		NoticeService service = new NoticeService();
		service.insertNotice(notice);
		
		System.out.println("title : " +  title);
		System.out.println("content : " +  content);
		System.out.println("isOpen : " + isOpen);
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		PrintWriter out = response.getWriter();
		out.printf("title : %s<br>", title);
		out.printf("content : %s<br>", content);
		out.printf("isOpen : %s<br>", isOpen);

		response.sendRedirect("list");
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request
		.getRequestDispatcher("/WEB-INF/view3/admin/board/notice/reg.jsp")
		.forward(request,response);	
		
	}
}
