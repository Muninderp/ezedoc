
package org.dbs.mydoc.data.format;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class FieldErrorResource {
	private String field;
	private String message;

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}