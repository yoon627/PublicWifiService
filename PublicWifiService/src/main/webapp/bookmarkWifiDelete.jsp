<%@page import="db.PublicWifiService"%>
<%@page import="db.Wifi"%>
<%@page import="db.Bookmark"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>와이파이 정보 구하기</title>
	<style>
		table {
			border-collapse: collapse;
			width: 100%;
		}
		tr {
			diplay: block;
			float:left;
			border:solid 1px white;
		}
		th {
			display:block;
			border:solid 1px #a0a0a0;
			border-color:white;
			background-color: green;
			color: white;
		}
		td { 
			display:block;
			border:solid 1px #a0a0a0;
			
		}
	</style>
</head>
<body>
	<% 
		request.setCharacterEncoding("UTF-8");
		PublicWifiService publicWifiService = new PublicWifiService();
		String id = request.getParameter("id");
		String bookmarkName = request.getParameter("bookmarkName");
		String wifiName = request.getParameter("wifiName");
		String regDate = request.getParameter("regDate");
	%>
	<h1 style="margin:8px">북마크 와이파이 삭제하기</h1>
	<div style="margin:8px"><a href="index.jsp">홈</a> | <a href="locationHistory.jsp">위치 히스토리 목록</a> | <a href="wifiData.jsp">Open API 와이파이 정보 가져오기</a> | <a href="bookmarkWifiList.jsp">북마크 보기</a> | <a href="bookmark-group.jsp">북마크 그룹 관리</a></div>
	<form action="bookmarkWifiDelete-submit.jsp?id=<%=id %>" method="post">
		<%	
			out.write("<table>");
			out.write("<tr>");
			out.write("<th>북마크 이름</th>");
			out.write("<th>와이파이명</th>");
			out.write("<th>등록일자</th>");
			out.write("</tr>");
			out.write("<tr>");
			out.write("<td>"+bookmarkName+"</td>");
			out.write("<td>"+wifiName+"</td>");
			out.write("<td>"+regDate+"</td>");
			out.write("</tr>");
			out.write("</table>");
		%>
		<button style="margin:8px" type="button" onclick="location.href='bookmarkWifiList.jsp'">돌아가기</button>
		<button style="margin:8px" type="submit">삭제</button>
	</form>
</body>
</html>