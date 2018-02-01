
package org.dbs.mydoc.data.format;


public class MessageModel implements Queueable{
  private static final long serialVersionUID = 1L;

  private String message;
  private String destination;
  private MessageObjectType messageType;
  private Object mailBodyValue;
  private String subject;
  private String imgList;
  // Comma separated absolute path to the attachements.
  // For eg:- /var/attachment/1.zip,/var/attachment/2.txt.
  private String attachments;

  /**
   * @return the message
   */
  @Override
  public String getMessage() {
    return message;
  }
  /**
   * @param message the message to set
   */
  @Override
  public void setMessage(String message) {
    this.message = message;
  }
  /**
   * @return the destination
   */
  @Override
  public String getDestination() {
    return destination;
  }

  /**
   * @param destination the destination to set
   */
  @Override
  public void setDestination(String destination) {
    this.destination = destination;
  }

  @Override
  public MessageObjectType getType() {
    return messageType;
  }

  @Override
  public void setType(MessageObjectType messageType) {
    this.messageType = messageType;
  }

  @Override
  public void setMailBodyValue(Object mailBodyValue) {
    this.mailBodyValue = mailBodyValue;

  }

  @Override
  public Object getMailBodyValue() {
    return mailBodyValue;
  }

  @Override
  public String getSubject() {
    return subject;
  }

  @Override
  public void setSubject(String subject) {
    this.subject = subject;
  }

  @Override
  public String getImgList() {
    return imgList;
  }

  @Override
  public void setImgList(String imgList) {
    this.imgList = imgList;
  }

  public String getAttachments() {
    return attachments;
  }

  public void setAttachments(String attachments) {
    this.attachments = attachments;
  }

}