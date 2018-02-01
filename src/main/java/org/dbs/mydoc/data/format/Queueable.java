
package org.dbs.mydoc.data.format;



import java.io.Serializable;



public interface Queueable extends Serializable {

	public void setMessage(String messageContent);
	
	public void setType(MessageObjectType messageType);

	public void setDestination(String destination);
	
    public void setMailBodyValue(Object mailBodyValue);
    
    public void setSubject(String subject);
    
    public void setImgList(String imgList);
    
	public String getMessage();
	
	public String getImgList();
	
	public String getDestination();

	public MessageObjectType getType();

    public Object getMailBodyValue();
    
    public String getSubject();
}
