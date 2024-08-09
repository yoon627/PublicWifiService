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
		String mgr_no = request.getParameter("mgr_no");
		String distStr = request.getParameter("distStr");
		String bookmarkName = request.getParameter("bookmarks");
		String wifiName = request.getParameter("wifiName");
		float dist = Float.parseFloat(distStr);
		PublicWifiService publicWifiService = new PublicWifiService();
		publicWifiService.saveBookmarkWifi(bookmarkName,wifiName, dist, mgr_no);
	%>
	<script>
		alert("북마크를 추가하였습니다.");
		location.href="bookmarkWifiList.jsp";
	</script>
</body>
</html>