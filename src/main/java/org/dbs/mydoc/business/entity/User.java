package org.dbs.mydoc.business.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class User {

	@NotNull(message = "First Name should not be Empty")
	private String firstName;
	@NotNull(message = "Last Name should not be Empty")
	private String lastName;
	@NotNull(message = "Email Id should not be Empty")
	@Email(message = "Invalid Email Id")
	private String emailId;
	@NotNull(message = "Password Field Should not be Empty.")
	@Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%!]).{6,10})", message = "Length should be 6 to 10 and should contain 1 digit, 1 lowercase , 1 upperCase and special character @ ,#,$, %,!")
	private String password;
	@NotNull
	@Pattern(regexp = "(^$|[0-9]{10})", message = "mobile number should be of 10 digits")
	private String mobileNumber;
	@NotNull
	private String userType;
	@NotNull
	@NotEmpty(message = "Practice Id should not be Empty")
	private String practiceId;
	@NotNull
	@NotEmpty
	@Pattern(regexp = "[0-9]+", message = "Age should conatin number only")
	private String age;
	@NotNull
	@NotEmpty(message = "Gender should not be Empty")
	private String gender;
	@NotNull(message = "Notification ID should not be Empty")
	private String notificationId;
	@NotNull(message = "Specilaisation should not be Empty")
	private String specialization;

	public User() {
		super();
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(String notificationId) {
		this.notificationId = notificationId;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

}