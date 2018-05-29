package org.dbs.mydoc.business.entity;

import java.util.List;

import javax.validation.constraints.NotNull;

public class ConsultationDetail {

	private String consultationId;

	@NotNull
	private String doctorId;

	@NotNull
	private String patientId;

	@NotNull
	private String healthWorkerId;

	private String status;

	private List<String> documentUrl;

	private String consultationDate;

	private String updatedDate;

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

	public String getConsultationDate() {
		return consultationDate;
	}

	public void setConsultationDate(String consultationDate) {
		this.consultationDate = consultationDate;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

}