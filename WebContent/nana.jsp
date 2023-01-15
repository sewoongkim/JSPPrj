<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	String cnt_ = request.getParameter("cnt");
	int cnt = 100;
	if (cnt_ != null && !cnt_.equals(""))
		cnt = Integer.parseInt(cnt_);
%>
	<% for (int i=0;  i<cnt;  i++){ %>
		안녕하세요 Servlet !! <br >
	<% } %>
</body>
</html>