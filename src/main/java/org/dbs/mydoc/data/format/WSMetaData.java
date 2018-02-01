
package org.dbs.mydoc.data.format;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@JsonInclude(Include.NON_NULL)
public class WSMetaData {

	/**
	 * Get the below parameters from HttpServlet request object.
	 */
	private String clientIp;

	private String clientPort;

	private String clientHost;

	/**
	 * @return the clientIp
	 */
	public String getClientIp() {
		return clientIp;
	}

	/**
	 * @param clientIp
	 *            the clientIp to set
	 */
	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	/**
	 * @return the clientPort
	 */
	public String getClientPort() {
		return clientPort;
	}

	/**
	 * @param clientPort
	 *            the clientPort to set
	 */
	public void setClientPort(String clientPort) {
		this.clientPort = clientPort;
	}

	/**
	 * @return the clientHost
	 */
	public String getClientHost() {
		return clientHost;
	}

	/**
	 * @param clientHost
	 *            the clientHost to set
	 */
	public void setClientHost(String clientHost) {
		this.clientHost = clientHost;
	}

}
