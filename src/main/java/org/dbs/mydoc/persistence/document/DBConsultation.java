package org.dbs.mydoc.persistence.document;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class DBConsultation {

	@Id
	private String consultationId;

	private String doctorId;

	private String patientId;

	private String healthWorkerId;

	private String status;

	private List<String> documentUrl;

	private Timestamp consultationDate;

	private Timestamp updatedDate;

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

	public Timestamp getConsultationDate() {
		return consultationDate;
	}

	public void setConsultationDate(Timestamp consultationDate) {
		this.consultationDate = consultationDate;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

}