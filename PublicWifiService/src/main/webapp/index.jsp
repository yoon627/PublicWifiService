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
			border:solid 1px #e9e9e9;
		}
		td{
			border:solid 1px #a0a0a0;
		}
		thead{
			background-color: green;
			color: white;
		}
	</style>
</head>
<body>
	<h1 style="margin:8px">와이파이 정보 구하기</h1>
	<div style="margin:8px"><a href="index.jsp">홈</a> | <a href="locationHistory.jsp">위치 히스토리 목록</a> | <a href="wifiData.jsp">Open API 와이파이 정보 가져오기</a> | <a href="bookmarkWifiList.jsp">북마크 보기</a> | <a href="bookmark-group.jsp">북마크 그룹 관리</a></div>
	<form action="wifi20.jsp" method="post" style="margin:8px">
		LAT: <input type="text" id="lat" name="lat" value="0.0"/>
		LNT: <input type="text" id="lnt" name="lnt" value="0.0"/>
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
			<tr>
				<th colspan='17'>위치 정보를 입력한 후에 조회해 주세요.</th>
			</tr>
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