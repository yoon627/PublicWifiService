package db;

import java.util.Date;

public class LocationHistory {
	private int ID;
	private int HistoryID;
	private float LAT;
	private float LNT;
	private Date checkDate;
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getHistoryID() {
		return HistoryID;
	}
	public void setHistoryID(int historyID) {
		HistoryID = historyID;
	}
	public float getLAT() {
		return LAT;
	}
	public void setLAT(float lAT) {
		LAT = lAT;
	}
	public float getLNT() {
		return LNT;
	}
	public void setLNT(float lNT) {
		LNT = lNT;
	}
	public Date getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
}
