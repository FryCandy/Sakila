package vo;
public class CustomerList {
	private int ID;
	private String name;
	private String address;
	private int zipCode;
	private long phone;
	private String city;
	private String country;
	private String notes;
	private int SID;
	
	
	@Override
	public String toString() {
		return "CustomerList [ID=" + ID + ", name=" + name + ", address=" + address + ", zipCode=" + zipCode
				+ ", phone=" + phone + ", city=" + city + ", country=" + country + ", notes=" + notes + ", SID=" + SID
				+ "]";
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getZipCode() {
		return zipCode;
	}
	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}
	public long getPhone() {
		return phone;
	}
	public void setPhone(long phone) {
		this.phone = phone;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public int getSID() {
		return SID;
	}
	public void setSID(int sID) {
		SID = sID;
	}
	


}
