<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!--  --------------------------------------------------------------  -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<%
	int num = 0;
	String result;	
String num_ = request.getParameter("n");
	if (num_ != null && !num_.equals(""))
		num = Integer.parseInt(num_);
	
	pageContext.setAttribute("aa","hello");
	
	if (num%2 != 0)
		result = "홀수";
	else
		result = "짝수";
%>
<body>
	<%= result %> 입니다. <br > 
	<%if(num%2 != 0) {%>
	홀수입니다.
	<%} else {%>
	짝수입니다.
	<% } %> 
	<%=request.getAttribute("result") %> 입니다.
	${result} <br > 
	${names[0]} <br >
	${notice.title} <br >
	${aa} <br >
	${header.accept} <br >
	${pageContext.request.method}
	${param.n > "3"  }
</body>
</html>