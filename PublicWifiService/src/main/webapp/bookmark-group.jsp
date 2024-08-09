<%@page import="java.util.ArrayList"%>
<%@page import="db.Bookmark"%>
<%@page import="db.PublicWifiService"%>
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
	<h1 style="margin:8px">북마크 그룹 관리</h1>
	<div style="margin:8px"><a href="index.jsp">홈</a> | <a href="locationHistory.jsp">위치 히스토리 목록</a> | <a href="wifiData.jsp">Open API 와이파이 정보 가져오기</a> | <a href="bookmarkWifiList.jsp">북마크 보기</a> | <a href="bookmark-group.jsp">북마크 그룹 관리</a></div>
	<button style="margin:5px" onclick="location.href='bookmark-group-add.jsp'">북마크 그룹 이름 추가</button>
		<table>
		<thead>
			<tr>
				<th>ID</th>
				<th>북마크 이름</th>
				<th>순서</th>
				<th>등록일자</th>
				<th>수정일자</th>
				<th>비고</th>
			</tr>
		</thead>
		<tbody>
		
				<%
					PublicWifiService publicWifiService = new PublicWifiService();
					ArrayList<Bookmark> bookmarkList = publicWifiService.showBookmarkGroup();
					if(bookmarkList.size()==0){
						out.write("<tr>");
						out.write("<td colspan='6' align='center'>정보가 존재하지 않습니다.</td>");
						out.write("<tr>");
					}else{
						for(Bookmark bookmark: bookmarkList){
							out.write("<tr>");
							out.write("<td>"+bookmark.getId()+"</td>");
							out.write("<td>"+bookmark.getName()+"</td>");
							out.write("<td>"+bookmark.getOrder()+"</td>");
							java.util.Date regDate = bookmark.getRegDate()==null?null:bookmark.getRegDate();
							SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S");
							out.write("<td>"+formatter.format(regDate)+"</td>");
							String mDate = bookmark.getModDate()==null?" ":formatter.format(bookmark.getModDate());
							out.write("<td>"+mDate+"</td>");
							out.write("<td align='center'>"+"<a href='bookmark-group-modify.jsp?id="+bookmark.getId()+"&name="+bookmark.getName()+"&order="+bookmark.getOrder()+"'>수정</a>"+","+"<a href='bookmark-group-delete.jsp?id="+bookmark.getId()+"&name="+bookmark.getName()+"&order="+bookmark.getOrder()+"'>삭제</a>"+"</td>");
							out.write("</tr>");
						}
					}

				%>
		</tbody>
	</table>
</body>
</html>