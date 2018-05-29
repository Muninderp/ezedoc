package org.dbs.mydoc.business.entity;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;

public class ApiError {

	private int code;
	private String message;
	private List<String> errors;

	public ApiError(int code, String message, List<String> errors) {
		super();
		this.code = code;
		this.message = message;
		this.errors = errors;
	}

	public ApiError(int code, String message, String errors) {
		super();
		this.code = code;
		this.message = message;
		this.errors = Arrays.asList(errors);
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

}
