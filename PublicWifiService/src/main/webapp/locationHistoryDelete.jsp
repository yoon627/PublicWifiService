<%@page import="db.PublicWifiService"%>
<%@page import="java.util.ArrayList"%>
<%@page import="db.LocationHistory"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
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
	<%
		PublicWifiService publicWifiService = new PublicWifiService();
		String strHistoryID = request.getParameter("HistoryID");
		publicWifiService.deleteHistory(Integer.parseInt(strHistoryID));
	%>
	<h1 style="margin:8px">위치 히스토리 목록</h1>
	<div style="margin:8px"><a href="index.jsp">홈</a> | <a href="locationHistory.jsp">위치 히스토리 목록</a> | <a href="wifiData.jsp">Open API 와이파이 정보 가져오기</a> | <a href="bookmarkWifiList.jsp">북마크 보기</a> | <a href="bookmark-group.jsp">북마크 그룹 관리</a></div>
	<table>
		<thead>
			<tr>
				<th>ID</th>
				<th>X좌표</th>
				<th>Y좌표</th>
				<th>조회일자</th>
				<th>비고</th>
			</tr>
		</thead>
		<tbody>
				<%
					ArrayList<LocationHistory> locationHistoryList = publicWifiService.showHistory();
					for(int i=0;i<locationHistoryList.size();i++){
						LocationHistory locationHistory = locationHistoryList.get(i);
						Date date = locationHistory.getCheckDate();
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
						String strDateTime = formatter.format(date);
				%>
				<tr>
					<td><%= locationHistory.getID() %></td>
					<td><%= locationHistory.getLAT()%></td>
					<td><%= locationHistory.getLNT()%></td>
					<td><%= strDateTime%></td>
					<td><input type="button" value="삭제" onclick="deleteHistory('<%= locationHistory.getHistoryID()%>')"/></td>
				</tr>
				<%} %>
		</tbody>
	</table>
	<%
		
	%>
</body>
<script>
	function deleteHistory(historyID){
		location.href="locationHistoryDelete.jsp?HistoryID="+historyID;
	}
</script>
</html>