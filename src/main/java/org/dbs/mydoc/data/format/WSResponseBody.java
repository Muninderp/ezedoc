
package org.dbs.mydoc.data.format;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@JsonAutoDetect(fieldVisibility = com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY)
@JsonInclude(Include.NON_NULL)
public class WSResponseBody {
	private WSMetaData metaData;
	private String status;
	private Object data;
	private Object message;
	private ErrorResource errors;

	public WSMetaData getMetaData() {
		return metaData;
	}

	public void setMetaData(WSMetaData metaData) {
		this.metaData = metaData;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public ErrorResource getErrors() {
		return errors;
	}

	public void setErrors(ErrorResource errors) {
		this.errors = errors;
	}

	public Object getMessage() {
		return message;
	}

	public void setMessage(Object message) {
		this.message = message;
	}
}
