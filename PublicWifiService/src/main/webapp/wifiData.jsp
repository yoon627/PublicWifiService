<%@ page import="db.PublicWifiService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>와이파이 정보 불러오기</title>
</head>
<body>
	<%
		try{
			PublicWifiService publicWifiService = new PublicWifiService();
			int totalCnt = publicWifiService.init();
			out.write(totalCnt+"개의 WIFI 정보를 정상적으로 저장하였습니다.");
		}catch (Exception e){
			e.printStackTrace();
			out.write("정보 가져오기 실패");
		}
	%>
	<div><a href="index.jsp">홈으로 가기</a></div>
</body>
</html>