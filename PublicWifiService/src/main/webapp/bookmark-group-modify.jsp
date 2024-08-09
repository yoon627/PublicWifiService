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
		String id = request.getParameter("id");
		String beforeName = request.getParameter("name");
		String beforeOrder = request.getParameter("order");
	%>
	<h1 style="margin:8px">북마크 삭제하기</h1>
	<div style="margin:8px"><a href="index.jsp">홈</a> | <a href="locationHistory.jsp">위치 히스토리 목록</a> | <a href="wifiData.jsp">Open API 와이파이 정보 가져오기</a> | <a href="bookmarkWifiList.jsp">북마크 보기</a> | <a href="bookmark-group.jsp">북마크 그룹 관리</a></div>
		<form style="margin:8px" action="bookmark-group-modify-submit.jsp?id=<%=id %>" method="post">
			<table>
				<tr>
					<th>북마크 이름</th>
					<th>순서</th>
				</tr>
				<tr>
					<td><input type="text" id="bookmarkName" name="bookmarkName" value="<%=beforeName %>"/></td>
					<td><input type="text" id="order" name="order" value="<%=beforeOrder %>"/></td>
				</tr>			
			</table>
			<button type="button" onclick="location.href='bookmark-group.jsp'">돌아가기</button>
			<button type="submit">수정</button>
		</form>

</body>
</html>