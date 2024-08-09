<%@page import="db.PublicWifiService"%>
<%@page import="java.util.ArrayList"%>
<%@page import="db.Wifi"%>
<%@page import="db.BookmarkWifi"%>
<%@page import="java.text.SimpleDateFormat"%>
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
			margin: 5px;
		}
		th{
			border:solid 1px white;
		}
		td{
			border:solid 1px #a0a0a0;
		}
		thead{
			background-color: green;
			color: white;
		}
		tbody > tr:nth-child(even){
			background-color:#e9e9e9;
		}
		tbody > tr:nth-child(odd){
			background-color:white;
		}
	</style>
</head>
<body>
	<h1 style="margin:8px">북마크 와이파이 목록</h1>
	<div style="margin:8px"><a href="index.jsp">홈</a> | <a href="locationHistory.jsp">위치 히스토리 목록</a> | <a href="wifiData.jsp">Open API 와이파이 정보 가져오기</a> | <a href="bookmarkWifiList.jsp">북마크 보기</a> | <a href="bookmark-group.jsp">북마크 그룹 관리</a></div>
		<table>
		<thead>
			<tr>
				<th>ID</th>
				<th>북마크 이름</th>
				<th>와이파이명</th>
				<th>등록일자</th>
				<th>비고</th>
			</tr>
		</thead>
		<tbody>
				<%
					PublicWifiService publicWifiService = new PublicWifiService();
					ArrayList<BookmarkWifi> bookmarkWifiList = publicWifiService.showBookmarkWifi();
					if(bookmarkWifiList.size()==0){
						out.write("<tr>");
						out.write("<td colspan='5' align='center'>정보가 존재하지 않습니다.</td>");
						out.write("<tr>");
					}
					for(BookmarkWifi bookmarkWifi: bookmarkWifiList){
						out.write("<tr>");
						out.write("<td>"+bookmarkWifi.getId()+"</td>");
						out.write("<td>"+bookmarkWifi.getBookmarkName()+"</td>");
						out.write("<td>"+"<a href=\"wifiDetail.jsp?mgr_no="+bookmarkWifi.getWifiMgrNo()+"&distStr="+bookmarkWifi.getDist()+"\">"+ bookmarkWifi.getWifiName() +"</a>" + "</td>");
						java.util.Date regDate = bookmarkWifi.getRegDate()==null?null:bookmarkWifi.getRegDate();
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S");
						out.write("<td>"+formatter.format(regDate)+"</td>");
						out.write("<td align='center'>"+"<a href='bookmarkWifiDelete.jsp?id="+bookmarkWifi.getId()+"&bookmarkName="+bookmarkWifi.getBookmarkName()+"&wifiName="+bookmarkWifi.getWifiName()+"&regDate="+bookmarkWifi.getRegDate()+"'>삭제</a>"+"</td>");
						out.write("</tr>");
					}
				%>
		</tbody>
	</table>
</body>
</html>