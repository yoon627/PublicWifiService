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
		String id = request.getParameter("id");
		String name = request.getParameter("bookmarkName");
		String order = request.getParameter("order");
		PublicWifiService publicWifiService = new PublicWifiService();
		publicWifiService.modifyBookmarkGroup(Integer.parseInt(id),name,Integer.parseInt(order));
	%>
	<script>
		alert("북마크 그룹 정보를 수정하였습니다.");
		location.href="bookmark-group.jsp";
	</script>
</body>
</html>