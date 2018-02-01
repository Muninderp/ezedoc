
package org.dbs.mydoc.data.format;

import java.io.Serializable;


public class SecurityToken implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7475212926995834921L;
	
	private String csrfToken;
	
	private String authToken;

	/**
	 * @return the csrfToken
	 */
	public String getCsrfToken() {
		return csrfToken;
	}
	

	/**
	 * @param csrfToken the csrfToken to set
	 */
	public void setCsrfToken(String csrfToken) {
		this.csrfToken = csrfToken;
	}

	/**
	 * @return the authToken
	 */
	public String getAuthToken() {
		return authToken;
	}

	/**
	 * @param authToken the authToken to set
	 */
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
	
	

}
