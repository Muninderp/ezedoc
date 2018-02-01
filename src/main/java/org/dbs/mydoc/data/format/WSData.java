
package org.dbs.mydoc.data.format;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class WSData {

	/*
	 * Cache Attributes
	 */
	private String identifier;

	private Object value;

	private Long time;

	private String timeunit;

	/*
	 * Messaging/Notification Attributes
	 */

	private String exchangeName;

	private String routingKey;

	private List<MessageModel> messageobjects;

	/*
	 * Messaging/Notfication Attributes
	 */
	private int code;

	private String desc;

	private boolean isError;
	
	private Throwable cause;

	private String message;

	private String otp;

	private boolean otpMatch;
	
	/**
	 * for services which want to async communication using RabbitMQ
	 */
	private List<Object> genericObjects;
	
	/**
	 * @return the identifier
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * @param identifier
	 *            the identifier to set
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	/**
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * @return the time
	 */
	public Long getTime() {
		return time;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(Long time) {
		this.time = time;
	}

	/**
	 * @return the timeunit
	 */
	public String getTimeunit() {
		return timeunit;
	}

	/**
	 * @param timeunit
	 *            the timeunit to set
	 */
	public void setTimeunit(String timeunit) {
		this.timeunit = timeunit;
	}

	/**
	 * @return the exchangeName
	 */
	public String getExchangeName() {
		return exchangeName;
	}

	/**
	 * @param exchangeName
	 *            the exchangeName to set
	 */
	public void setExchangeName(String exchangeName) {
		this.exchangeName = exchangeName;
	}

	/**
	 * @return the routingKey
	 */
	public String getRoutingKey() {
		return routingKey;
	}

	/**
	 * @param routingKey
	 *            the routingKey to set
	 */
	public void setRoutingKey(String routingKey) {
		this.routingKey = routingKey;
	}

	/**
	 * @return the messageobjects
	 */
	public List<MessageModel> getMessageobjects() {
		return messageobjects;
	}

	/**
	 * @param messageobjects
	 *            the messageobjects to set
	 */
	public void setMessageobjects(List<MessageModel> messageobjects) {
		this.messageobjects = messageobjects;
	}

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc
	 *            the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * @return the isError
	 */
	public boolean isError() {
		return isError;
	}

	/**
	 * @param isError
	 *            the isError to set
	 */
	public void setError(boolean isError) {
		this.isError = isError;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the otp
	 */
	public String getOtp() {
		return otp;
	}

	/**
	 * @param otp
	 *            the otp to set
	 */
	public void setOtp(String otp) {
		this.otp = otp;
	}

	/**
	 * @return the otpMatch
	 */
	public boolean isOtpMatch() {
		return otpMatch;
	}

	/**
	 * @param otpMatch
	 *            the otpMatch to set
	 */
	public void setOtpMatch(boolean otpMatch) {
		this.otpMatch = otpMatch;
	}

	/**
	 * @return the cause
	 */
	public Throwable getCause() {
		return cause;
	}

	/**
	 * @param cause the cause to set
	 */
	public void setCause(Throwable cause) {
		this.cause = cause;
	}

	/**
	 * @return the genericObjects
	 */
	public List<Object> getGenericObjects() {
		return genericObjects;
	}

	/**
	 * @param genericObjects the genericObjects to set
	 */
	public void setGenericObjects(List<Object> genericObjects) {
		this.genericObjects = genericObjects;
	}

}
