<%@page import="db.PublicWifiService"%>
<%@page import="java.util.ArrayList"%>
<%@page import="db.Wifi"%>
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
	<%
		String lat = request.getParameter("lat");
		String lnt = request.getParameter("lnt");
	%>
	<h1 style="margin:8px">와이파이 정보 구하기</h1>
	<div style="margin:8px"><a href="index.jsp">홈</a> | <a href="locationHistory.jsp">위치 히스토리 목록</a> | <a href="wifiData.jsp">Open API 와이파이 정보 가져오기</a> | <a href="bookmarkWifiList.jsp">북마크 보기</a> | <a href="bookmark-group.jsp">북마크 그룹 관리</a></div>
	<form action="wifi20.jsp" method="post" style="margin:8px">
		LAT: <input type="text" id="lat" name="lat" value="<%=lat%>"/>
		LNT: <input type="text" id="lnt" name="lnt" value="<%=lnt%>"/>
		<button onclick="getLocation()" type="button">내 위치 가져오기</button>
		<button type="submit">근처 Wifi 정보 보기</button>
	</form>
	<table>
		<thead>
			<tr>
				<th>거리(Km)</th>
				<th>관리번호</th>
				<th>자치구</th>
				<th>와이파이명</th>
				<th>도로명주소</th>
				<th>상세주소</th>
				<th>설치위치(층)</th>
				<th>설치유형</th>
				<th>설치기관</th>
				<th>서비스구분</th>
				<th>망종류</th>
				<th>설치년도</th>
				<th>실내외구분</th>
				<th>WIFI접속환경</th>
				<th>X좌표</th>
				<th>Y좌표</th>
				<th>작업일자</th>
			</tr>
		</thead>
		<tbody>
				<%
					PublicWifiService publicWifiService = new PublicWifiService();
					publicWifiService.saveHistory(Float.parseFloat(lat),Float.parseFloat(lnt));
					ArrayList<Wifi> wifiList = publicWifiService.getList(Float.parseFloat(lat), Float.parseFloat(lnt));
					for(Wifi wifi: wifiList){
						double dist = 6371*Math.acos(Math.cos(Math.toRadians(Float.parseFloat(lat)))*Math.cos(Math.toRadians(wifi.getLAT()))*Math.cos(Math.toRadians(wifi.getLNT()-Float.parseFloat(lnt)))+Math.sin(Math.toRadians(wifi.getLAT()))*Math.sin(Math.toRadians(Float.parseFloat(lat))));
						String mgr_no = wifi.getMGR_NO()==null?" ":wifi.getMGR_NO();
						String wrdofc = wifi.getWRDOFC()==null?" ":wifi.getWRDOFC();
						String main_nm = wifi.getMAIN_NM()==null?" ":wifi.getMAIN_NM();
						String adres1 = wifi.getADRES1()==null?" ":wifi.getADRES1();
						String adres2 = wifi.getADRES2()==null?" ":wifi.getADRES2();
						String instl_floor = wifi.getINSTL_FLOOR()==null?" ":wifi.getINSTL_FLOOR();
						String instl_ty = wifi.getINSTL_TY()==null?" ":wifi.getINSTL_TY();
						String instl_mby = wifi.getINSTL_MBY()==null?" ":wifi.getINSTL_MBY();
						String svc_se = wifi.getSVC_SE()==null?" ":wifi.getSVC_SE();
						String cmcwr = wifi.getCMCWR()==null?" ":wifi.getCMCWR();
						int cnstc_year = wifi.getCNSTC_YEAR();
						String inout_door = wifi.getINOUT_DOOR()==null?" ":wifi.getINOUT_DOOR();
						String remars3 = wifi.getREMARS3()==null?" ":wifi.getREMARS3();
						float wifiLat = wifi.getLAT();
						float wifiLnt = wifi.getLNT();
						java.util.Date work_dttm = wifi.getWORK_DTTM()==null?null:wifi.getWORK_DTTM();
						out.write("<tr>");
						out.write("<td>"+Math.round(dist*10000)/10000.0+"</td>");
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
					}
				%>
		</tbody>
	</table>
</body>


<script>
	function getLocation(){
	    navigator.geolocation.getCurrentPosition( function(position){ //OK
	        var lat= position.coords.latitude;
	        var lnt= position.coords.longitude;
	        
            document.getElementById('lat').value = lat;
            document.getElementById('lnt').value = lnt;
	    } ,
	    function(error){ //error
	        switch(error.code){
	            case error.PERMISSION_DENIED:
	                str="사용자 거부";
	                break;
	            case error.POSITION_UNAVAILABLE:
	                str="지리정보 없음";
	                break;
	            case error.TIMEOUT:
	                str="시간 초과";
	                break;
	            case error.UNKNOWN_ERROR:
	                str="알수없는 에러";
	                break;
	        }
	        console.log(str);
	    });
	}
	
</script>

</html>