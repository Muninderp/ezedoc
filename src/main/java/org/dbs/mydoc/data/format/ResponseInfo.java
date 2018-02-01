
package org.dbs.mydoc.data.format;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY)
public class ResponseInfo {
	
	private int code;
	
	private String desc;
	
	private boolean isError;
	
	private Throwable cause;
	
	private String message;
	
	private Object data;
	
    private SecurityToken token;


	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param code the code to set
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
	 * @param desc the desc to set
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
	 * @param isError the isError to set
	 */
	public void setError(boolean isError) {
		this.isError = isError;
	}

	public Throwable getCause() {
		return cause;
	}

	public void setCause(Throwable cause) {
		this.cause = cause;
	}

	/**
	 * @return the data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(Object data) {
		this.data = data;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the token
	 */
	public SecurityToken getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(SecurityToken token) {
		this.token = token;
	}
	
}
