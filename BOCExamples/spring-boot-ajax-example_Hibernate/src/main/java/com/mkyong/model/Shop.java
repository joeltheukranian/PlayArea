package com.mkyong.model;

public class Shop {

	String name;
	int userId = 1, version = 0;
	
	public Shop() { 
		this.setName("Blah");
	}

	public Shop(String newName) { 
		this.setName(newName);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String[] getStaffName() {
		return staffName;
	}
	public void setStaffName(String[] staffName) {
		this.staffName = staffName;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	String staffName[];

	//getter and setter methods

}