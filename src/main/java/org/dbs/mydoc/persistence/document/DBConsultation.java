package org.dbs.mydoc.persistence.document;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Document
public class DBConsultation {

	@Id
	private String consultationId;

	private String doctorId;

	private String patientId;

	private String healthWorkerId;

	private String status;

	private List<String> documentUrl;
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private Date consultationDate;
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private Date updatedDate;
	
	private String notificationFlag; 

	public String getConsultationId() {
		return consultationId;
	}

	public void setConsultationId(String consultationId) {
		this.consultationId = consultationId;
	}

	public String getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public String getHealthWorkerId() {
		return healthWorkerId;
	}

	public void setHealthWorkerId(String healthWorkerId) {
		this.healthWorkerId = healthWorkerId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<String> getDocumentUrl() {
		return documentUrl;
	}

	public void setDocumentUrl(List<String> documentUrl) {
		this.documentUrl = documentUrl;
	}

	public Date getConsultationDate() {
		return consultationDate;
	}

	public void setConsultationDate(Date consultationDate) {
		this.consultationDate = consultationDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getNotificationFlag() {
		return notificationFlag;
	}

	public void setNotificationFlag(String notificationFlag) {
		this.notificationFlag = notificationFlag;
	}

		
}