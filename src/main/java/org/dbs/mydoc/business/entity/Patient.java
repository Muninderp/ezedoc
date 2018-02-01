package org.dbs.mydoc.business.entity;

public class Patient {

	private String firstName;
	private String lastName;
	private String mobileNumber;

	public Patient() {
		super();
	}

	public Patient(String firstName, String lastName, String mobileNumber) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.mobileNumber = mobileNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
}
