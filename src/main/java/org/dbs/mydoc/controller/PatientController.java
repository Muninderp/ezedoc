package org.dbs.mydoc.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.dbs.mydoc.business.entity.Patient;
import org.dbs.mydoc.data.format.MyDocAPIResponseInfo;
import org.dbs.mydoc.persistence.document.DBPatient;
import org.dbs.mydoc.persistence.document.DBUser;
import org.dbs.mydoc.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PatientController {

	private static final Logger LOGGER = Logger.getLogger(PatientController.class);

	@Autowired
	private PatientRepository patientRepository;

	@ResponseBody
	@RequestMapping(value = "/mydoc/patient/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MyDocAPIResponseInfo> addPatient(@Valid @RequestBody Patient patient) {

		DBPatient dbPatient = new DBPatient();
		MyDocAPIResponseInfo myDocAPIResponseInfo = new MyDocAPIResponseInfo();
		if (patientExist(patient.getMobileNumber())) {
			myDocAPIResponseInfo.setCode(5001);
			myDocAPIResponseInfo.setDescription(
					"There is an patient register with that mobile Number : " + patient.getMobileNumber());
			myDocAPIResponseInfo.setData(null);
			return new ResponseEntity<MyDocAPIResponseInfo>(myDocAPIResponseInfo, HttpStatus.BAD_REQUEST);
		}
		try {
			dbPatient.setId(new ObjectId().getCounter());
			dbPatient.setFirstName(patient.getFirstName());
			dbPatient.setLastName(patient.getLastName());
			dbPatient.setLocation(patient.getLocation());
			dbPatient.setAddedBy(patient.getAddedBy());
			dbPatient.setMobileNumber(patient.getMobileNumber());
			patientRepository.save(dbPatient);
		} catch (Exception e) {
			LOGGER.error("Error in Saving Patient Details.   " + e.getStackTrace());
			myDocAPIResponseInfo.setCode(5001);
			myDocAPIResponseInfo.setDescription("Error in Saving Patient Details");
			myDocAPIResponseInfo.setData(null);
			return new ResponseEntity<MyDocAPIResponseInfo>(myDocAPIResponseInfo, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		myDocAPIResponseInfo.setCode(0);
		myDocAPIResponseInfo.setDescription("Patient Details Saved Successfully");
		myDocAPIResponseInfo.setData(dbPatient.getId());
		return new ResponseEntity<MyDocAPIResponseInfo>(myDocAPIResponseInfo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "getallpatient", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<DBPatient> getAll() {
		return patientRepository.findAll();
	}

	private boolean patientExist(String mobileNumber) {

		return patientRepository.findByMobileNumber(mobileNumber) != null;
	}

}
