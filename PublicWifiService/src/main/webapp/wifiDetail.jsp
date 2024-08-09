<%@page import="db.PublicWifiService"%>
<%@page import="db.Wifi"%>
<%@page import="db.Bookmark"%>
<%@page import="java.util.ArrayList"%>
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
		PublicWifiService publicWifiService = new PublicWifiService();
		String mgr_no = request.getParameter("mgr_no");
		String distStr = request.getParameter("distStr");
		float dist = Float.parseFloat(distStr);
		Wifi wifi = publicWifiService.getWifiDetail(mgr_no);
	%>
	<h1 style="margin:8px">와이파이 상세 정보</h1>
	<div style="margin:8px"><a href="index.jsp">홈</a> | <a href="locationHistory.jsp">위치 히스토리 목록</a> | <a href="wifiData.jsp">Open API 와이파이 정보 가져오기</a> | <a href="bookmarkWifiList.jsp">북마크 보기</a> | <a href="bookmark-group.jsp">북마크 그룹 관리</a></div>
	<form style="margin:8px" action="bookmarkWifiAdd-submit.jsp?distStr=<%=distStr %>&mgr_no=<%=mgr_no %>&wifiName=<%=wifi.getMAIN_NM() %>" method="post">
		<select name="bookmarks" id="bookmarks">
			<option value="select">북마크 그룹 이름 선택</option>
			<%
				ArrayList<Bookmark> bookmarkList = publicWifiService.showBookmarkGroup();
				for(Bookmark bookmark: bookmarkList){
					out.write("<option value=\'"+bookmark.getName()+"\'>"+bookmark.getName()+"</option>");
				}
			%>
		</select>
		<button type="submit">북마크 추가</button>
	</form>
	<%	
		mgr_no = wifi.getMGR_NO()==null?"&nbsp":wifi.getMGR_NO();
		String wrdofc = wifi.getWRDOFC()==null?"&nbsp":wifi.getWRDOFC();
		String main_nm = wifi.getMAIN_NM()==null?"&nbsp":wifi.getMAIN_NM();
		String adres1 = wifi.getADRES1()==null?"&nbsp":wifi.getADRES1();
		String adres2 = wifi.getADRES2()==null?"&nbsp":wifi.getADRES2();
		String instl_floor = wifi.getINSTL_FLOOR()==null?"&nbsp":wifi.getINSTL_FLOOR();
		String instl_ty = wifi.getINSTL_TY()==null?"&nbsp":wifi.getINSTL_TY();
		String instl_mby = wifi.getINSTL_MBY()==null?"&nbsp":wifi.getINSTL_MBY();
		String svc_se = wifi.getSVC_SE()==null?"&nbsp":wifi.getSVC_SE();
		String cmcwr = wifi.getCMCWR()==null?"&nbsp":wifi.getCMCWR();
		int cnstc_year = wifi.getCNSTC_YEAR();
		String inout_door = wifi.getINOUT_DOOR()==null?"&nbsp":wifi.getINOUT_DOOR();
		String remars3 = wifi.getREMARS3()==null?"&nbsp":wifi.getREMARS3();
		float wifiLat = wifi.getLAT();
		float wifiLnt = wifi.getLNT();
		java.util.Date work_dttm = wifi.getWORK_DTTM()==null?null:wifi.getWORK_DTTM();

		out.write("<table>");
		out.write("<tr>");
		out.write("<th>거리(Km)</th>");
		out.write("<th>관리번호</th>");
		out.write("<th>자치구</th>");
		out.write("<th>와이파이명</th>");
		out.write("<th>도로명주소</th>");
		out.write("<th>상세주소</th>");
		out.write("<th>설치위치(층)</th>");
		out.write("<th>설치유형</th>");
		out.write("<th>설치기관</th>");
		out.write("<th>서비스구분</th>");
		out.write("<th>망종류</th>");
		out.write("<th>설치년도</th>");
		out.write("<th>실내외구분</th>");
		out.write("<th>WIFI접속환경</th>");
		out.write("<th>X좌표</th>");
		out.write("<th>Y좌표</th>");
		out.write("<th>작업일자</th>");
		out.write("</tr>");
		out.write("<tr>");
		out.write("<td>"+dist+"</td>");
		out.write("<td>"+mgr_no+"</td>");
		out.write("<td>"+wrdofc+"</td>");
		out.write("<td>"+"<a href=\"wifiDetail.jsp?mgr_no="+mgr_no+"&distStr="+dist+"\">"+ main_nm +"</a>" + "</td>");
		out.write("<td>"+adres1+"</td>");
		out.write("<td>"+adres2+"</td>");
		out.write("<td>"+instl_floor+"</td>");
		out.write("<td>"+instl_ty+"</td>");
		out.write("<td>"+instl_mby+"</td>");
		out.write("<td>"+svc_se+"</td>");
		out.write("<td>"+cmcwr+"</td>");
		out.write("<td>"+cnstc_year+"</td>");
		out.write("<td>"+inout_door+"</td>");
		out.write("<td>"+remars3+"</td>");
		out.write("<td>"+wifiLat+"</td>");
		out.write("<td>"+wifiLnt+"</td>");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S");
		out.write("<td>"+formatter.format(work_dttm)+"</td>");
		out.write("</tr>");
		out.write("</table>");
	%>
</body>
</html>