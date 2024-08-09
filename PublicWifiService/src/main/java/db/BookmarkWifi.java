package db;

import java.util.Date;

public class BookmarkWifi {
	private int id;
	private String bookmarkName;
	private String wifiMgrNo;
	private String wifiName;
	private Date regDate;
	private float dist;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBookmarkName() {
		return bookmarkName;
	}
	public void setBookmarkName(String bookmarkName) {
		this.bookmarkName = bookmarkName;
	}
	public String getWifiMgrNo() {
		return wifiMgrNo;
	}
	public void setWifiMgrNo(String wifiMgrNo) {
		this.wifiMgrNo = wifiMgrNo;
	}
	public String getWifiName() {
		return wifiName;
	}
	public void setWifiName(String wifiName) {
		this.wifiName = wifiName;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	public float getDist() {
		return dist;
	}
	public void setDist(float dist) {
		this.dist = dist;
	}
	
}
