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
			background-color:green;
		}
		td{
			border:solid 1px #a0a0a0;
		}
	</style>
</head>
<body>
	<h1 style="margin:8px">북마크 추가하기</h1>
	<div style="margin:8px"><a href="index.jsp">홈</a> | <a href="locationHistory.jsp">위치 히스토리 목록</a> | <a href="wifiData.jsp">Open API 와이파이 정보 가져오기</a> | <a href="bookmarkWifiList.jsp">북마크 보기</a> | <a href="bookmark-group.jsp">북마크 그룹 관리</a></div>
		<form style="margin:8px" action="bookmark-group-add-submit.jsp" method="post">
		<table>
			<tr>
				<th>북마크 이름</th>
				<th>순서</th>
			</tr>
			<tr>
				<td style="padding:5px"><input style="width:95%" type="text" id="bookmarkName" name="bookmarkName" /></td>
				<td style="padding:5px"><input style="width:95%" type="text" id="order" name="order" /></td>
			</tr>			
		</table>
		<button type="submit">추가</button>
	</form>
</body>
</html>