<%@page import="db.PublicWifiService"%>
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
		request.setCharacterEncoding("UTF-8");
		String bookmarkName = request.getParameter("bookmarkName");
		String strOrder = request.getParameter("order");
		int order = Integer.parseInt(strOrder);
		PublicWifiService publicWifiService = new PublicWifiService();
		publicWifiService.saveBookmarkGroup(bookmarkName,order);
	%>
	<script>
		alert("북마크 그룹 정보를 추가하였습니다.");
		location.href="bookmark-group.jsp";
	</script>
</body>
</html>