package model;

import java.sql.Date;
import java.util.Calendar;

public class AuditTrailEntry {
	private int id;
	private Date dateAdded;
	private String message;
	
	public AuditTrailEntry(){
		id = 0;
		dateAdded = new Date(Calendar.getInstance().getTime().getTime());
		message = "";
	}

	//getters
	public int getId() { return id;}

	public Date getDateAdded() { return dateAdded; }

	public String getMessage() { return message; }

	//setters
	public void setId(int id) { this.id = id; }

	public void setDateAdded(Date dateAdded) { this.dateAdded = dateAdded; }

	public void setMessage(String message) { this.message = message; }
}
