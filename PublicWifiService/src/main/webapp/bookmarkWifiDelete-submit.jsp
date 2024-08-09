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
		String id = request.getParameter("id");
		PublicWifiService publicWifiService = new PublicWifiService();
		publicWifiService.deleteBookmarkWifi(Integer.parseInt(id));
	%>
	<script>
		alert("북마크를 삭제하였습니다.");
		location.href="bookmarkWifiList.jsp";
	</script>
</body>
</html>