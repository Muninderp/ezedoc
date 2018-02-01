
package org.dbs.mydoc.data.format;

import com.fasterxml.jackson.annotation.JsonAutoDetect;


@JsonAutoDetect(fieldVisibility = com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY)
public class WSBody {

	private WSMetaData metaData = new WSMetaData();
	
	private WSData data = new WSData();

	/**
	 * @return the metaData
	 */
	public WSMetaData getMetaData() {
		return metaData;
	}

	/**
	 * @param metaData the metaData to set
	 */
	public void setMetaData(WSMetaData metaData) {
		this.metaData = metaData;
	}

	/**
	 * @return the data
	 */
	public WSData getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(WSData data) {
		this.data = data;
	}
	
	
	
}
