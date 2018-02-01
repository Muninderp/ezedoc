package org.dbs.mydoc.business.entity;

import javax.validation.constraints.NotNull;

public class User {

	@NotNull
	private String firstName;
	@NotNull
	private String lastName;
	@NotNull
	private String emailId;
	@NotNull
	private String mobileNumber;
	@NotNull
	private String userType;
	@NotNull
	private String practiceId;

	public User() {
		super();
	}

	public User(String firstName, String lastName, String emailId, String mobileNumber, String userType,
			String practiceId) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
		this.mobileNumber = mobileNumber;
		this.userType = userType;
		this.practiceId = practiceId;
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

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getPracticeId() {
		return practiceId;
	}

	public void setPracticeId(String practiceId) {
		this.practiceId = practiceId;
	}

}
