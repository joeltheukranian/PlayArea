package demo;

public class Shop {

	String name;
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
	String staffName[];

	//getter and setter methods

}