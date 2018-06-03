package org.dbs.mydoc.business.entity;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.dbs.mydoc.persistence.document.DBPatient;

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

	private Date consultationDate;

	private Date updatedDate;

	private DBPatient patient;

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

	public DBPatient getPatient() {
		return patient;
	}

	public void setPatient(DBPatient patient) {
		this.patient = patient;
	}

}