package db;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Date;

public class PublicWifiService {
	//와이파이 초기 등록
	public int init() {
		
        String dbURL = "jdbc:sqlite://C:\\Users\\user\\Desktop\\ZerobaseStudy\\DB\\sqlite-tools-win-x64-3460000/publicWifiInfo.db";
        
        Connection connection = null;
        
        int endNum = 0;
        try{
        	Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(dbURL);
            Statement statement = connection.createStatement();
            
            //기존에 있는 테이블 삭제
            statement.executeUpdate("drop table if exists wifi");
            statement.executeUpdate("drop table if exists bookmarkList");
            statement.executeUpdate("drop table if exists locationHistory");
            statement.executeUpdate("drop table if exists bookmarkWifiList");
            
            //wifi 테이블 생성
            statement.executeUpdate(""
            		+ "CREATE TABLE 'wifi'\r\n"
            		+ "(\r\n"
            		+ "    'MGR_NO'      VARCHAR(20) NOT NULL PRIMARY KEY,\r\n"
            		+ "    'WRDOFC'      VARCHAR(20) NULL,\r\n"
            		+ "    'MAIN_NM'     VARCHAR(20) NULL,\r\n"
            		+ "    'ADRES1'      VARCHAR(20) NULL,\r\n"
            		+ "    'ADRES2'      VARCHAR(20) NULL,\r\n"
            		+ "    'INSTL_FLOOR' VARCHAR(5)  NULL,\r\n"
            		+ "    'INSTL_TY'    VARCHAR(20) NULL,\r\n"
            		+ "    'INSTL_MBY'   VARCHAR(20) NULL,\r\n"
            		+ "    'SVC_SE'      VARCHAR(20) NULL,\r\n"
            		+ "    'CMCWR'       VARCHAR(20) NULL,\r\n"
            		+ "    'CNSTC_YEAR'  INT         NULL,\r\n"
            		+ "    'INOUT_DOOR'  VARCHAR(2)  NULL,\r\n"
            		+ "    'REMARS3'     VARCHAR(20) NULL,\r\n"
            		+ "    'LAT'         FLOAT       NULL,\r\n"
            		+ "    'LNT'         FLOAT       NULL,\r\n"
            		+ "    'WORK_DTTM'   DATETIME    NULL\r\n"
            		+ ");\r\n"
            		+ "CREATE TABLE 'bookmarkList'\r\n"
            		+ "(\r\n"
            		+ "    'id'      INTEGER NULL PRIMARY KEY AUTOINCREMENT, -- id\r\n"
            		+ "    'bookmarkName'    VARCHAR(20) NULL, -- name\r\n"
            		+ "    'ord'   INT         NULL, -- order\r\n"
            		+ "    'regDate' DATETIME    NULL, -- regDate\r\n"
            		+ "    'modDate' DATETIME    NULL  -- modDate\r\n"
            		+ ");\r\n"
            		+ "CREATE TABLE 'locationHistory'\r\n"
            		+ "(\r\n"
            		+ "    'HistoryID'        INTEGER  NULL PRIMARY KEY AUTOINCREMENT, "
            		+ "    'LAT'         FLOAT    NULL, -- x\r\n"
            		+ "    'LNT'         FLOAT    NULL, -- y\r\n"
            		+ "    'checkDate' DATETIME NULL  -- checkDate\r\n"
            		+ ");\r\n"
            		+ " CREATE TABLE 'bookmarkWifiList' (\r\n"
            		+ "	'bookmarkWifiId'  INTEGER NULL PRIMARY KEY AUTOINCREMENT , \r\n"
            		+ "	'bookmarkName' VARCHAR(20) NULL , \r\n"
            		+ "    'MGR_NO' VARCHAR(20) NULL , \r\n"
            		+ "	'wifiName' VARCHAR(20) NULL, \r\n"
            		+ "	'regDate' DATETIME    NULL, \r\n "
            		+ "  'dist' FLOAT NULL, "
            		+ " FOREIGN KEY ('MGR_NO') REFERENCES wifi('MGR_NO'), "
            		+ " FOREIGN KEY ('bookmarkName') REFERENCES bookmarkList('bookmarkName') "
            		+ ");"
            		+ "");
            
            //서울시 api요청해서 데이터 파싱해서 디비에 넣어주기
            HashMap<String,Integer> map = new HashMap<>();
            map.put("<row>",0);
            map.put("<X_SWIFI_MGR_NO>",1);
            map.put("<X_SWIFI_WRDOFC>",2);
            map.put("<X_SWIFI_MAIN_NM>",3);
            map.put("<X_SWIFI_ADRES1>",4);
            map.put("<X_SWIFI_ADRES2>",5);
            map.put("<X_SWIFI_INSTL_FLOOR>",6);
            map.put("<X_SWIFI_INSTL_TY>",7);
            map.put("<X_SWIFI_INSTL_MBY>",8);
            map.put("<X_SWIFI_SVC_SE>",9);
            map.put("<X_SWIFI_CMCWR>",10);
            map.put("<X_SWIFI_CNSTC_YEAR>",11);
            map.put("<X_SWIFI_INOUT_DOOR>",12);
            map.put("<X_SWIFI_REMARS3>",13);
            map.put("<LAT>",14);
            map.put("<LNT>",15);
            map.put("<WORK_DTTM>",16);
            map.put("</row>",17);

            int startNum = 1;
            endNum = startNum+999;
            int maxNum = Integer.MAX_VALUE;
            while (startNum <= maxNum) {
                String apiURLString = "http://openapi.seoul.go.kr:8088/707673524677686435304c756f7778/xml/TbPublicWifiInfo/"+startNum+"/"+endNum;
                try{
                    @SuppressWarnings("deprecation")
					URL apiURL = new URL(apiURLString);
                    HttpURLConnection conn = (HttpURLConnection) apiURL.openConnection();
                    conn.setRequestMethod("GET");
                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                            String line;
                            line = br.readLine();//<?xml version="1.0" encoding="UTF-8"?>
                            line = br.readLine();//<TbPublicWifiInfo>
                            line = br.readLine();//<list_total_count>24578</list_total_count>
                            maxNum = Integer.parseInt(line.replaceAll("<[^>]*>", ""));
                            endNum = Math.min(endNum, maxNum);
                            line = br.readLine();//<RESULT>
                            line = br.readLine();//<CODE>INFO-000</CODE>
                            line = br.readLine();//<MESSAGE>정상 처리되었습니다</MESSAGE>
                            if(!line.replaceAll("<[^>]*>", "").equals("정상 처리되었습니다")) {
                            	System.out.println("API 데이터를 불러오는데 오류가 있습니다.");
                            	break;
                            }
                            line = br.readLine();//</RESULT>

                            PreparedStatement preparedStatement = null;
                            String sql = " insert into wifi (MGR_NO, WRDOFC, MAIN_NM, ADRES1, ADRES2, INSTL_FLOOR, INSTL_TY, INSTL_MBY, SVC_SE, CMCWR, CNSTC_YEAR, INOUT_DOOR, REMARS3, LAT, LNT, WORK_DTTM)" +
                                    " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); ";
                            for (int i = startNum; i <= endNum; i++) {
                            	while((line = br.readLine())!=null) {
                            		StringBuilder tag = new StringBuilder();
                            		StringBuilder data = new StringBuilder();
                            		int idx = 0;
                        			char c = ' ';
                        			while(idx<line.length() && (c=line.charAt(idx))!='>') {
                        				tag.append(c);
                        				idx++;
                        			}
                        			tag.append(c);
                        			idx++;
                        			String strTag = tag.toString();
                        			if(idx==line.length()) {
                        				if(map.containsKey(strTag)){
                            				int action = map.get(strTag);
                            				if(action==0) {
                            		            preparedStatement = connection.prepareStatement(sql);
                            		            preparedStatement.setString(1, null);
                            		            preparedStatement.setString(2, null);
                            		            preparedStatement.setString(3, null);
                            		            preparedStatement.setString(4, null);
                            		            preparedStatement.setString(5, null);
                            		            preparedStatement.setString(6, null);
                            		            preparedStatement.setString(7, null);
                            		            preparedStatement.setString(8, null);
                            		            preparedStatement.setString(9, null);
                            		            preparedStatement.setString(10, null);
                            		            preparedStatement.setInt(11, 0);
                            		            preparedStatement.setString(12, null);
                            		            preparedStatement.setString(13, null);
                            		            preparedStatement.setFloat(14, 0);
                            		            preparedStatement.setFloat(15, 0);
                            				}else if(action==17) {
                            		            int affectedRows = preparedStatement.executeUpdate();
                            		            if(affectedRows <= 0) {
                            		                System.out.println("저장 실패");
                            		            }
                            				}
                        				}
                        			}else {
                        				if(map.containsKey(strTag)) {
                        					while(idx<line.length() && (c=line.charAt(idx))!='<') {
                        						data.append(c);
                        						idx++;
                            				}
                                			int action = map.get(strTag);
                                			String strData = data.toString();
                                			if(action==1) {
                                				preparedStatement.setString(1, strData);
                                			}else if(action==2) {
                                				preparedStatement.setString(2, strData);
                                			}else if(action==3) {
                                				preparedStatement.setString(3, strData);
                                			}else if(action==4) {
                                				preparedStatement.setString(4, strData);
                                			}else if(action==5) {
                                				preparedStatement.setString(5, strData);
                                			}else if(action==6) {
                                				preparedStatement.setString(6, strData);
                                			}else if(action==7) {
                                				preparedStatement.setString(7, strData);
                                			}else if(action==8) {
                                				preparedStatement.setString(8, strData);
                                			}else if(action==9) {
                                				preparedStatement.setString(9, strData);
                                			}else if(action==10) {
                                				preparedStatement.setString(10, strData);
                                			}else if(action==11) {
                                				preparedStatement.setInt(11, Integer.parseInt(strData));
                                			}else if(action==12) {
                                				preparedStatement.setString(12, strData);
                                			}else if(action==13) {
                                				preparedStatement.setString(13, strData);
                                			}else if(action==14) {
                                				preparedStatement.setFloat(14, Float.parseFloat(strData));
                                			}else if(action==15) {
                                				preparedStatement.setFloat(15, Float.parseFloat(strData));
                                			}else if(action==16) {
                                				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
												try {
													java.util.Date date = formatter.parse(strData);
													java.util.Date newDate = new Date(date.getTime()+6*3600*1000+14*60*1000);
													preparedStatement.setTimestamp(16, new java.sql.Timestamp(newDate.getTime()));
												} catch (ParseException e) {
													preparedStatement.setDate(16, null);
												}
                                			}
                        				}

                        			}                        			
                            	}
                            }
                        }
                    } else {
                        System.out.println(conn.getResponseCode());
                    }
                }catch(MalformedURLException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(endNum);
                startNum = endNum + 1;
                endNum = Math.min(startNum+999, maxNum);
            }
            System.out.println("저장 완료!");
        }catch(ClassNotFoundException|SQLException e){
            System.out.println(e.getMessage());
        }finally {
        	try {
            	if(connection != null && !connection.isClosed()) {
            		connection.close();
            	}
        		
        	}catch(SQLException e) {
        		System.out.println(e.getMessage());
        	}
        }
        return endNum;
	}
	
	
	//거리순으로 20개 공공와이파이 가져오기
	public ArrayList<Wifi> getList(float lat,float lnt){

		ArrayList<Wifi> list = new ArrayList<>();
        
		String url = "jdbc:sqlite://C:\\Users\\user\\Desktop\\ZerobaseStudy\\DB\\sqlite-tools-win-x64-3460000/publicWifiInfo.db";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        
        try{
        	Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);
            
            String sql = " select *\r\n "
            		+ " from wifi as w\r\n "
            		+ " where w.LAT!=0 and w.LNT!=0\r\n "
            		+ " order by 6371*acos(cos(radians(?))*cos(radians(w.LAT))*cos(radians(w.LNT-?))+sin(radians(w.LAT))*sin(radians(?))) ASC\r\n "
            		+ " limit 20; ";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setFloat(1, lat);
            preparedStatement.setFloat(2, lnt);
            preparedStatement.setFloat(3, lat);
            
            rs = preparedStatement.executeQuery();
            
            while(rs.next()) {
            	Wifi wifi = new Wifi();
            	wifi.setMGR_NO(rs.getString("MGR_NO"));
            	wifi.setWRDOFC(rs.getString("WRDOFC"));
            	wifi.setMAIN_NM(rs.getString("MAIN_NM"));
            	wifi.setADRES1(rs.getString("ADRES1"));
            	wifi.setADRES2(rs.getString("ADRES2"));
            	wifi.setINSTL_FLOOR(rs.getString("INSTL_FLOOR"));
            	wifi.setINSTL_TY(rs.getString("INSTL_TY"));
            	wifi.setINSTL_MBY(rs.getString("INSTL_MBY"));
            	wifi.setSVC_SE(rs.getString("SVC_SE"));
            	wifi.setCMCWR(rs.getString("CMCWR"));
            	wifi.setCNSTC_YEAR(rs.getInt("CNSTC_YEAR"));
            	wifi.setINOUT_DOOR(rs.getString("INOUT_DOOR"));
            	wifi.setREMARS3(rs.getString("REMARS3"));
            	wifi.setLAT(rs.getFloat("LAT"));
            	wifi.setLNT(rs.getFloat("LNT"));
            	wifi.setWORK_DTTM(rs.getDate("WORK_DTTM"));            
            	list.add(wifi);
            }
        }catch(ClassNotFoundException|SQLException e){
            System.out.println(e.getMessage());
        }finally {
        	
        	try {
        		if(preparedStatement != null && !preparedStatement.isClosed()) {
        			preparedStatement.close();
        		}
        	}catch(SQLException e) {
        		System.out.println(e.getMessage());
        	}
        	
        	try {
            	if(connection != null && !connection.isClosed()) {
            		connection.close();
            	}
        		
        	}catch(SQLException e) {
        		System.out.println(e.getMessage());
        	}
        }
		return list;
	}
	
	//위치 히스토리 저장
	public void saveHistory(float lat,float lnt) {
        
		String url = "jdbc:sqlite://C:\\Users\\user\\Desktop\\ZerobaseStudy\\DB\\sqlite-tools-win-x64-3460000/publicWifiInfo.db";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try{
        	Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);
            
            String sql = " insert into locationHistory (LAT, LNT, checkDate)" + 
					" values (?,?,?); ";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setFloat(1,lat);
            preparedStatement.setFloat(2,lnt);
            java.util.Date date = new Date();
            preparedStatement.setDate(3,new java.sql.Date(date.getTime()));
            
            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows <= 0) {
            	System.out.println("저장 실패");
            }
            
        }catch(ClassNotFoundException|SQLException e){
            System.out.println(e.getMessage());
        }finally {
        	
        	try {
        		if(preparedStatement != null && !preparedStatement.isClosed()) {
        			preparedStatement.close();
        		}
        	}catch(SQLException e) {
        		System.out.println(e.getMessage());
        	}
        	
        	try {
            	if(connection != null && !connection.isClosed()) {
            		connection.close();
            	}
        		
        	}catch(SQLException e) {
        		System.out.println(e.getMessage());
        	}
        }
	}
	
	//위치 히스토리 불러오기
	public ArrayList<LocationHistory> showHistory(){
		ArrayList<LocationHistory> list = new ArrayList<>();
		
		String url = "jdbc:sqlite://C:\\Users\\user\\Desktop\\ZerobaseStudy\\DB\\sqlite-tools-win-x64-3460000/publicWifiInfo.db";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        
        try{
        	Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);
            
            String sql = " select row_number() over () as ID,*\r\n "
            		+ " from locationHistory as w\r\n "
            		+ " order by ID desc ";
            preparedStatement = connection.prepareStatement(sql);
            
            rs = preparedStatement.executeQuery();
            
            while(rs.next()) {
            	LocationHistory locationHistory = new LocationHistory();
            	locationHistory.setID(rs.getInt("ID"));
            	locationHistory.setHistoryID(rs.getInt("HistoryID"));
            	locationHistory.setLAT(rs.getFloat("LAT"));
            	locationHistory.setLNT(rs.getFloat("LNT"));
				locationHistory.setCheckDate((java.util.Date)rs.getDate("checkDate"));
            	list.add(locationHistory);
            }
        }catch(ClassNotFoundException|SQLException e){
            System.out.println(e.getMessage());
        }finally {
        	
        	try {
        		if(preparedStatement != null && !preparedStatement.isClosed()) {
        			preparedStatement.close();
        		}
        	}catch(SQLException e) {
        		System.out.println(e.getMessage());
        	}
        	
        	try {
            	if(connection != null && !connection.isClosed()) {
            		connection.close();
            	}
        		
        	}catch(SQLException e) {
        		System.out.println(e.getMessage());
        	}
        }
		return list;
	}
	
	//위치 히스토리 삭제
	public void deleteHistory(int HistoryID) {
		String url = "jdbc:sqlite://C:\\Users\\user\\Desktop\\ZerobaseStudy\\DB\\sqlite-tools-win-x64-3460000/publicWifiInfo.db";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try{
        	Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);
            
            String sql = " delete from locationHistory "
            		+ " where HistoryID = ? ";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, HistoryID);
            
            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows <= 0) {
            	System.out.println("삭제 실패");
            }
            
        }catch(ClassNotFoundException|SQLException e){
            System.out.println(e.getMessage());
        }finally {
        	
        	try {
        		if(preparedStatement != null && !preparedStatement.isClosed()) {
        			preparedStatement.close();
        		}
        	}catch(SQLException e) {
        		System.out.println(e.getMessage());
        	}
        	
        	try {
            	if(connection != null && !connection.isClosed()) {
            		connection.close();
            	}
        		
        	}catch(SQLException e) {
        		System.out.println(e.getMessage());
        	}
        }
	}
	
	//와이파이 상세정보 불러오기
	public Wifi getWifiDetail(String MGR_NO) {
		String url = "jdbc:sqlite://C:\\Users\\user\\Desktop\\ZerobaseStudy\\DB\\sqlite-tools-win-x64-3460000/publicWifiInfo.db";
		Wifi wifi = new Wifi();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        
        try{
        	Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);
            
            String sql = " select *\r\n "
            		+ " from wifi \r\n "
            		+ " where MGR_NO=? ";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, MGR_NO);

            rs = preparedStatement.executeQuery();
            
            while(rs.next()) {
            	wifi.setMGR_NO(rs.getString("MGR_NO"));
            	wifi.setWRDOFC(rs.getString("WRDOFC"));
            	wifi.setMAIN_NM(rs.getString("MAIN_NM"));
            	wifi.setADRES1(rs.getString("ADRES1"));
            	wifi.setADRES2(rs.getString("ADRES2"));
            	wifi.setINSTL_FLOOR(rs.getString("INSTL_FLOOR"));
            	wifi.setINSTL_TY(rs.getString("INSTL_TY"));
            	wifi.setINSTL_MBY(rs.getString("INSTL_MBY"));
            	wifi.setSVC_SE(rs.getString("SVC_SE"));
            	wifi.setCMCWR(rs.getString("CMCWR"));
            	wifi.setCNSTC_YEAR(rs.getInt("CNSTC_YEAR"));
            	wifi.setINOUT_DOOR(rs.getString("INOUT_DOOR"));
            	wifi.setREMARS3(rs.getString("REMARS3"));
            	wifi.setLAT(rs.getFloat("LAT"));
            	wifi.setLNT(rs.getFloat("LNT"));
            	wifi.setWORK_DTTM(rs.getDate("WORK_DTTM"));
            }
        }catch(ClassNotFoundException|SQLException e){
            System.out.println(e.getMessage());
        }finally {
        	
        	try {
        		if(preparedStatement != null && !preparedStatement.isClosed()) {
        			preparedStatement.close();
        		}
        	}catch(SQLException e) {
        		System.out.println(e.getMessage());
        	}
        	
        	try {
            	if(connection != null && !connection.isClosed()) {
            		connection.close();
            	}
        		
        	}catch(SQLException e) {
        		System.out.println(e.getMessage());
        	}
        }
		return wifi; 
	}

	//북마크 그룹 생성
	public void saveBookmarkGroup(String bookmarkName, int order) {
		
		String url = "jdbc:sqlite://C:\\Users\\user\\Desktop\\ZerobaseStudy\\DB\\sqlite-tools-win-x64-3460000/publicWifiInfo.db";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try{
        	Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);
            
            String sql = " insert into bookmarkList (bookmarkName, ord, regDate)" + 
					" values (?,?,?); ";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,bookmarkName);
            preparedStatement.setInt(2,order);
            java.util.Date date = new Date();
            preparedStatement.setDate(3,new java.sql.Date(date.getTime()));
            
            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows <= 0) {
            	System.out.println("저장 실패");
            }
            
        }catch(ClassNotFoundException|SQLException e){
            System.out.println(e.getMessage());
        }finally {
        	
        	try {
        		if(preparedStatement != null && !preparedStatement.isClosed()) {
        			preparedStatement.close();
        		}
        	}catch(SQLException e) {
        		System.out.println(e.getMessage());
        	}
        	
        	try {
            	if(connection != null && !connection.isClosed()) {
            		connection.close();
            	}
        		
        	}catch(SQLException e) {
        		System.out.println(e.getMessage());
        	}
        }
	};
	
	//북마크 그룹 수정
	public void modifyBookmarkGroup(int id,String name, int order) {
		String url = "jdbc:sqlite://C:\\Users\\user\\Desktop\\ZerobaseStudy\\DB\\sqlite-tools-win-x64-3460000/publicWifiInfo.db";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try{
        	Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);
            
            String sql = " update bookmarkList set bookmarkName = ?, ord  = ?, modDate = ? "
            		+ " where id = ? ";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, order);
            java.util.Date date = new Date();
            preparedStatement.setDate(3,new java.sql.Date(date.getTime()));
            preparedStatement.setInt(4, id);
            
            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows <= 0) {
            	System.out.println("수정 실패");
            }
            
        }catch(ClassNotFoundException|SQLException e){
            System.out.println(e.getMessage());
        }finally {
        	
        	try {
        		if(preparedStatement != null && !preparedStatement.isClosed()) {
        			preparedStatement.close();
        		}
        	}catch(SQLException e) {
        		System.out.println(e.getMessage());
        	}
        	
        	try {
            	if(connection != null && !connection.isClosed()) {
            		connection.close();
            	}
        		
        	}catch(SQLException e) {
        		System.out.println(e.getMessage());
        	}
        }
	}
	
	//북마크 그룹 삭제
	public void deleteBookmarkGroup(int id) {
		String url = "jdbc:sqlite://C:\\Users\\user\\Desktop\\ZerobaseStudy\\DB\\sqlite-tools-win-x64-3460000/publicWifiInfo.db";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try{
        	Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);
            
            String sql = " delete from bookmarkList "
            		+ " where id = ? ";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            
            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows <= 0) {
            	System.out.println("삭제 실패");
            }
            
        }catch(ClassNotFoundException|SQLException e){
            System.out.println(e.getMessage());
        }finally {
        	
        	try {
        		if(preparedStatement != null && !preparedStatement.isClosed()) {
        			preparedStatement.close();
        		}
        	}catch(SQLException e) {
        		System.out.println(e.getMessage());
        	}
        	
        	try {
            	if(connection != null && !connection.isClosed()) {
            		connection.close();
            	}
        		
        	}catch(SQLException e) {
        		System.out.println(e.getMessage());
        	}
        }
	}
	
	//북마크 그룹 불러오기
	public ArrayList<Bookmark> showBookmarkGroup(){
		ArrayList<Bookmark> list = new ArrayList<>();
		
		String url = "jdbc:sqlite://C:\\Users\\user\\Desktop\\ZerobaseStudy\\DB\\sqlite-tools-win-x64-3460000/publicWifiInfo.db";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        
        try{
        	Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);
            
            String sql = " select * \r\n "
            		+ " from bookmarkList \r\n "
            		+ " order by ord asc ";
            preparedStatement = connection.prepareStatement(sql);
            
            rs = preparedStatement.executeQuery();
            
            while(rs.next()) {
            	Bookmark bookmark  = new Bookmark();
            	bookmark.setId(rs.getInt("id"));
            	bookmark.setName(rs.getString("bookmarkName"));
            	bookmark.setOrder(rs.getInt("ord"));
            	bookmark.setRegDate(rs.getDate("regDate"));
            	bookmark.setModDate(rs.getDate("modDate"));
            	list.add(bookmark);
            }
        }catch(ClassNotFoundException|SQLException e){
            System.out.println(e.getMessage());
        }finally {
        	
        	try {
        		if(preparedStatement != null && !preparedStatement.isClosed()) {
        			preparedStatement.close();
        		}
        	}catch(SQLException e) {
        		System.out.println(e.getMessage());
        	}
        	
        	try {
            	if(connection != null && !connection.isClosed()) {
            		connection.close();
            	}
        		
        	}catch(SQLException e) {
        		System.out.println(e.getMessage());
        	}
        }
		return list;
	}
	
	//북마크 와이파이 추가
	public void saveBookmarkWifi(String bookmarkName, String wifiName, float dist, String mgr_no) {
		String url = "jdbc:sqlite://C:\\Users\\user\\Desktop\\ZerobaseStudy\\DB\\sqlite-tools-win-x64-3460000/publicWifiInfo.db";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try{
        	Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);
            
            String sql = " insert into bookmarkWifiList (bookmarkName, MGR_NO, wifiName, regDate, dist)" + 
					" values (?,?,?,?,?); ";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,bookmarkName);
            preparedStatement.setString(2,mgr_no);
            preparedStatement.setString(3,wifiName);
            java.util.Date date = new Date();
            preparedStatement.setDate(4,new java.sql.Date(date.getTime()));
            preparedStatement.setFloat(5,dist);
            
            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows <= 0) {
            	System.out.println("저장 실패");
            }
            
        }catch(ClassNotFoundException|SQLException e){
            System.out.println(e.getMessage());
        }finally {
        	
        	try {
        		if(preparedStatement != null && !preparedStatement.isClosed()) {
        			preparedStatement.close();
        		}
        	}catch(SQLException e) {
        		System.out.println(e.getMessage());
        	}
        	
        	try {
            	if(connection != null && !connection.isClosed()) {
            		connection.close();
            	}
        		
        	}catch(SQLException e) {
        		System.out.println(e.getMessage());
        	}
        }
	}
	
	//북마크 와이파이 삭제
	public void deleteBookmarkWifi(int id) {
		String url = "jdbc:sqlite://C:\\Users\\user\\Desktop\\ZerobaseStudy\\DB\\sqlite-tools-win-x64-3460000/publicWifiInfo.db";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try{
        	Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);
            
            String sql = " delete from bookmarkWifiList "
            		+ " where bookmarkWifiId = ? ";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            
            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows <= 0) {
            	System.out.println("삭제 실패");
            }
            
        }catch(ClassNotFoundException|SQLException e){
            System.out.println(e.getMessage());
        }finally {
        	
        	try {
        		if(preparedStatement != null && !preparedStatement.isClosed()) {
        			preparedStatement.close();
        		}
        	}catch(SQLException e) {
        		System.out.println(e.getMessage());
        	}
        	
        	try {
            	if(connection != null && !connection.isClosed()) {
            		connection.close();
            	}
        		
        	}catch(SQLException e) {
        		System.out.println(e.getMessage());
        	}
        }
	}
	
	//북마크 와이파이 불러오기
	public ArrayList<BookmarkWifi> showBookmarkWifi(){
		ArrayList<BookmarkWifi> list = new ArrayList<>();
		
		String url = "jdbc:sqlite://C:\\Users\\user\\Desktop\\ZerobaseStudy\\DB\\sqlite-tools-win-x64-3460000/publicWifiInfo.db";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        
        try{
        	Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);
            
            String sql = " select * \r\n "
            		+ " from bookmarkWifiList \r\n "
            		+ " order by bookmarkWifiId asc ";
            preparedStatement = connection.prepareStatement(sql);
            
            rs = preparedStatement.executeQuery();
            
            while(rs.next()) {
            	BookmarkWifi bookmarkWifi  = new BookmarkWifi();
            	bookmarkWifi.setId(rs.getInt("bookmarkWifiId"));
            	bookmarkWifi.setBookmarkName(rs.getString("bookmarkName"));
            	bookmarkWifi.setWifiMgrNo(rs.getString("MGR_NO"));
            	bookmarkWifi.setWifiName(rs.getString("wifiNAme"));
            	bookmarkWifi.setRegDate((java.util.Date)rs.getDate("regDate"));
            	bookmarkWifi.setDist(rs.getFloat("dist"));
            	list.add(bookmarkWifi);
            }
        }catch(ClassNotFoundException|SQLException e){
            System.out.println(e.getMessage());
        }finally {
        	
        	try {
        		if(preparedStatement != null && !preparedStatement.isClosed()) {
        			preparedStatement.close();
        		}
        	}catch(SQLException e) {
        		System.out.println(e.getMessage());
        	}
        	
        	try {
            	if(connection != null && !connection.isClosed()) {
            		connection.close();
            	}
        		
        	}catch(SQLException e) {
        		System.out.println(e.getMessage());
        	}
        }
		
		return list;
	}
}
