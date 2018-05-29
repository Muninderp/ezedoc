package org.dbs.mydoc.business.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;

public class LoginDetails {

	@NotNull
	@Pattern(regexp = "(^$|[0-9]{10})", message = "mobile number should be of 10 digits")
	private String mobileNumber;

	private String emailId;
	@NotNull
	private String password;

	public LoginDetails() {
		super();
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
