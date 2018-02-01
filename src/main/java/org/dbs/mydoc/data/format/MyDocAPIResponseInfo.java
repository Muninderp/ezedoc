package org.dbs.mydoc.data.format;

public class MyDocAPIResponseInfo {

	private int code;
	private String description;
	private Object data;

	public MyDocAPIResponseInfo() {
		super();
	}

	public MyDocAPIResponseInfo(int code, String description, Object data) {
		super();
		this.code = code;
		this.description = description;
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
