package org.dbs.mydoc.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.EnumUtils;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.dbs.mydoc.business.entity.ConsultationDetail;
import org.dbs.mydoc.business.entity.LoginDetails;
import org.dbs.mydoc.business.entity.User;
import org.dbs.mydoc.constant.ErrorConstant;
import org.dbs.mydoc.constant.Specilization;
import org.dbs.mydoc.constant.UserType;
import org.dbs.mydoc.data.format.MyDocAPIResponseInfo;
import org.dbs.mydoc.persistence.document.DBConsultation;
import org.dbs.mydoc.persistence.document.DBPatient;
import org.dbs.mydoc.persistence.document.DBUser;
import org.dbs.mydoc.repository.ConsultationRepository;
import org.dbs.mydoc.repository.PatientRepository;
import org.dbs.mydoc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

	private static final Logger LOGGER = Logger.getLogger(UserController.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Autowired
	private ConsultationRepository consultationRepository;

	private HashOperations hashOperations;

	@ResponseBody
	@RequestMapping(value = "/mydoc/users/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MyDocAPIResponseInfo> registerUser(@Valid @RequestBody User user) {
		DBUser dbUser = new DBUser();
		MyDocAPIResponseInfo myDocAPIResponseInfo = new MyDocAPIResponseInfo();
		if (userExist(user.getMobileNumber())) {
			myDocAPIResponseInfo.setCode(5001);
			myDocAPIResponseInfo
					.setDescription("There is an account with that mobile Number : " + user.getMobileNumber());
			myDocAPIResponseInfo.setData(null);
			return new ResponseEntity<MyDocAPIResponseInfo>(myDocAPIResponseInfo, HttpStatus.BAD_REQUEST);
		}
		try {
			dbUser.setId(new ObjectId().getCounter());
			dbUser.setFirstName(user.getFirstName());
			dbUser.setLastName(user.getLastName());
			dbUser.setEmailId(user.getEmailId());
			if (!EnumUtils.isValidEnum(Specilization.class, user.getSpecialization())) {
				LOGGER.error("Specialisation type Does not Exist");
				myDocAPIResponseInfo.setCode(5001);
				myDocAPIResponseInfo.setDescription("Specialization type Does not Exist");
				myDocAPIResponseInfo.setData(null);
				return new ResponseEntity<MyDocAPIResponseInfo>(myDocAPIResponseInfo, HttpStatus.BAD_REQUEST);
			}
			dbUser.setSpecialization(user.getSpecialization());
			dbUser.setPassword(passwordEncoder.encode(user.getPassword()));
			dbUser.setMobileNumber(user.getMobileNumber());
			dbUser.setPracticeId(user.getPracticeId());
			dbUser.setUserType(user.getUserType());
			dbUser.setNotificationId(user.getNotificationId());
			userRepository.save(dbUser);

		} catch (Exception e) {
			LOGGER.error("Error in Saving User Details.   " + e.getStackTrace());
			myDocAPIResponseInfo.setCode(5001);
			myDocAPIResponseInfo.setDescription("Error in Saving User Details");
			myDocAPIResponseInfo.setData(null);
			return new ResponseEntity<MyDocAPIResponseInfo>(myDocAPIResponseInfo, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		myDocAPIResponseInfo.setCode(0);
		myDocAPIResponseInfo.setDescription("User Details Saved Successfully");
		myDocAPIResponseInfo.setData(dbUser.getId());
		return new ResponseEntity<MyDocAPIResponseInfo>(myDocAPIResponseInfo, HttpStatus.OK);
	}

	/// mydoc/users/login

	@ResponseBody
	@RequestMapping(value = "mydoc/users/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MyDocAPIResponseInfo> login(@Valid @RequestBody LoginDetails loginDetails) {
		DBUser dbUser = null;
		MyDocAPIResponseInfo myDocAPIResponseInfo = new MyDocAPIResponseInfo();
		try {

			if (loginDetails.getMobileNumber() != null) {
				dbUser = userRepository.findByMobileNumber(loginDetails.getMobileNumber());

				if (dbUser != null) {
					if (passwordEncoder.matches(loginDetails.getPassword(), dbUser.getPassword())) {
						myDocAPIResponseInfo.setCode(0);
						myDocAPIResponseInfo.setDescription("User Succesfully Logged In");
						myDocAPIResponseInfo.setData(dbUser);

						return new ResponseEntity<MyDocAPIResponseInfo>(myDocAPIResponseInfo, HttpStatus.OK);

					} else {
						myDocAPIResponseInfo.setCode(4001);
						myDocAPIResponseInfo.setDescription("Incorrect Password");
						myDocAPIResponseInfo.setData(null);
						return new ResponseEntity<MyDocAPIResponseInfo>(myDocAPIResponseInfo, HttpStatus.OK);

					}
				} else {
					myDocAPIResponseInfo.setCode(5001);
					myDocAPIResponseInfo.setDescription(
							"There is no account with that mobile Number : " + loginDetails.getMobileNumber());
					myDocAPIResponseInfo.setData(null);
					return new ResponseEntity<MyDocAPIResponseInfo>(myDocAPIResponseInfo, HttpStatus.BAD_REQUEST);

				}

			} else {
				myDocAPIResponseInfo.setCode(5001);
				myDocAPIResponseInfo
						.setDescription("There is an account with that mobile Number : \" + user.getMobileNumber()");
				myDocAPIResponseInfo.setData(null);
				return new ResponseEntity<MyDocAPIResponseInfo>(myDocAPIResponseInfo, HttpStatus.BAD_REQUEST);

			}

		} catch (Exception e) {
			LOGGER.error("Error in Fetching User Details.   " + e.getStackTrace());
			myDocAPIResponseInfo.setCode(5001);
			myDocAPIResponseInfo.setDescription("Error in Fetching User Details");
			myDocAPIResponseInfo.setData(null);
			return new ResponseEntity<MyDocAPIResponseInfo>(myDocAPIResponseInfo, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	// mydoc/users/logout

	@ResponseBody
	@RequestMapping(value = "mydoc/users/logout", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MyDocAPIResponseInfo> logout(@Valid String sessionID, HttpServletRequest request) {

		hashOperations = redisTemplate.opsForHash();
		Long l = hashOperations.delete("auth-api-key", request.getHeader("auth-api-key"));
		MyDocAPIResponseInfo myDocAPIResponseInfo = new MyDocAPIResponseInfo();
		myDocAPIResponseInfo.setCode(0);
		myDocAPIResponseInfo.setData("Success");
		return new ResponseEntity<MyDocAPIResponseInfo>(myDocAPIResponseInfo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "getallusers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<DBUser> getAll() {
		return userRepository.findAll();
	}

	@ResponseBody
	@RequestMapping(value = "mydoc/users/updateNotificationId", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MyDocAPIResponseInfo> updateNotificationId(@RequestBody HashMap<String, String> requestData) {
		DBUser dbUser = null;
		String mobileNumber = requestData.get("mobileNumber");
		String notificationId = requestData.get("notificationId");
		MyDocAPIResponseInfo myDocAPIResponseInfo = new MyDocAPIResponseInfo();
		try {
			dbUser = userRepository.findByMobileNumber(mobileNumber);

			if (dbUser == null) {
				myDocAPIResponseInfo.setCode(ErrorConstant.BAD_REQUEST);
				myDocAPIResponseInfo.setDescription("There is no account with that mobile Number : " + mobileNumber);
				myDocAPIResponseInfo.setData(null);
				return new ResponseEntity<MyDocAPIResponseInfo>(myDocAPIResponseInfo, HttpStatus.BAD_REQUEST);
			} else {
				dbUser.setNotificationId(notificationId);
				userRepository.save(dbUser);
			}
		} catch (Exception e) {
			myDocAPIResponseInfo.setCode(5000);
			myDocAPIResponseInfo.setDescription("Error in Processing Request");
			myDocAPIResponseInfo.setData(null);
			return new ResponseEntity<MyDocAPIResponseInfo>(myDocAPIResponseInfo, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		myDocAPIResponseInfo.setCode(0);
		myDocAPIResponseInfo.setDescription("Notification id Updated Succesfully");
		myDocAPIResponseInfo.setData(dbUser.getId());
		return new ResponseEntity<MyDocAPIResponseInfo>(myDocAPIResponseInfo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "mydoc/users/getNotificationId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MyDocAPIResponseInfo> getNotificationId(@RequestParam("mobileNumber") String mobileNumber) {
		DBUser dbUser = null;
		String notificationId = null;
		MyDocAPIResponseInfo myDocAPIResponseInfo = new MyDocAPIResponseInfo();
		try {
			dbUser = userRepository.findByMobileNumber(mobileNumber);
			if (dbUser == null) {
				myDocAPIResponseInfo.setCode(ErrorConstant.BAD_REQUEST);
				myDocAPIResponseInfo.setDescription("There is no account with that mobile Number : " + mobileNumber);
				myDocAPIResponseInfo.setData(null);
				return new ResponseEntity<MyDocAPIResponseInfo>(myDocAPIResponseInfo, HttpStatus.BAD_REQUEST);
			} else {
				notificationId = dbUser.getNotificationId();
			}
		} catch (Exception e) {
			myDocAPIResponseInfo.setCode(5000);
			myDocAPIResponseInfo.setDescription("Error in Processing Request");
			myDocAPIResponseInfo.setData(null);
			return new ResponseEntity<MyDocAPIResponseInfo>(myDocAPIResponseInfo, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		myDocAPIResponseInfo.setCode(0);
		myDocAPIResponseInfo.setDescription("Success");
		HashMap<String, String> result = new HashMap<String, String>();
		result.put("notificationId", notificationId);
		myDocAPIResponseInfo.setData(result);
		return new ResponseEntity<MyDocAPIResponseInfo>(myDocAPIResponseInfo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "mydoc/users/getDoctorsBySpeciality", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MyDocAPIResponseInfo> getDoctorsBySpeciality(
			@RequestParam("specialization") String specialization) {
		List<DBUser> dbUsers = null;
		MyDocAPIResponseInfo myDocAPIResponseInfo = new MyDocAPIResponseInfo();
		try {
			if (!EnumUtils.isValidEnum(Specilization.class, specialization)) {
				LOGGER.error("Specialisation type Does not Exist");
				myDocAPIResponseInfo.setCode(5001);
				myDocAPIResponseInfo.setDescription("Specialization type Does not Exist");
				myDocAPIResponseInfo.setData(null);
				return new ResponseEntity<MyDocAPIResponseInfo>(myDocAPIResponseInfo, HttpStatus.BAD_REQUEST);
			}
			dbUsers = userRepository.findBySpecialization(specialization);

		} catch (Exception e) {
			myDocAPIResponseInfo.setCode(5000);
			myDocAPIResponseInfo.setDescription("Error in Processing Request");
			myDocAPIResponseInfo.setData(null);
			return new ResponseEntity<MyDocAPIResponseInfo>(myDocAPIResponseInfo, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		myDocAPIResponseInfo.setCode(0);
		myDocAPIResponseInfo.setDescription("Success");
		myDocAPIResponseInfo.setData(dbUsers);
		return new ResponseEntity<MyDocAPIResponseInfo>(myDocAPIResponseInfo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "mydoc/users/createConsultation", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MyDocAPIResponseInfo> createConsultation(
			@Valid @RequestBody ConsultationDetail consultationDetail) {
		DBConsultation dbConsultation = null;
		MyDocAPIResponseInfo myDocAPIResponseInfo = new MyDocAPIResponseInfo();
		try {
			dbConsultation = new DBConsultation();
			dbConsultation.setDoctorId(consultationDetail.getDoctorId());
			dbConsultation.setHealthWorkerId(consultationDetail.getHealthWorkerId());
			dbConsultation.setPatientId(consultationDetail.getPatientId());
			dbConsultation.setStatus("active");
			dbConsultation.setConsultationDate(new Date());
			dbConsultation.setUpdatedDate(new Date());
			consultationRepository.save(dbConsultation);

		} catch (Exception e) {
			myDocAPIResponseInfo.setCode(5000);
			myDocAPIResponseInfo.setDescription("Error in Processing Request");
			myDocAPIResponseInfo.setData(null);
			return new ResponseEntity<MyDocAPIResponseInfo>(myDocAPIResponseInfo, HttpStatus.INTERNAL_SERVER_ERROR);

		}
		myDocAPIResponseInfo.setCode(0);
		myDocAPIResponseInfo.setDescription("Consultation Details Save Succesfully");
		myDocAPIResponseInfo.setData(dbConsultation.getConsultationId());
		return new ResponseEntity<MyDocAPIResponseInfo>(myDocAPIResponseInfo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "mydoc/users/getConsultationDetailsForUser", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MyDocAPIResponseInfo> getConsulationDetailsForUser(
			@RequestParam("mobileNumber") String mobileNumber) {
		DBUser dbUser = null;
		List<DBConsultation> consultations = null;
		List<ConsultationDetail> consultationDetails = new ArrayList();
		MyDocAPIResponseInfo myDocAPIResponseInfo = new MyDocAPIResponseInfo();
		try {
			dbUser = userRepository.findByMobileNumber(mobileNumber);
			if (dbUser == null) {
				myDocAPIResponseInfo.setCode(ErrorConstant.BAD_REQUEST);
				myDocAPIResponseInfo.setDescription("There is no account with that mobile Number : " + mobileNumber);
				myDocAPIResponseInfo.setData(null);
				return new ResponseEntity<MyDocAPIResponseInfo>(myDocAPIResponseInfo, HttpStatus.BAD_REQUEST);
			} else {
				if (dbUser.getUserType().equals(UserType.doctor.toString())) {

					consultations = consultationRepository.findByDoctorId(dbUser.getPracticeId());
				} else {
					consultations = consultationRepository.findByHealthWorkerId(dbUser.getPracticeId());
				}
			}

			for (DBConsultation consultation : consultations) {
				DBPatient patient = patientRepository.findById(consultation.getPatientId());
				ConsultationDetail consultationDetail = new ConsultationDetail();
				consultationDetail.setConsultationId(consultation.getConsultationId());
				consultationDetail.setConsultationDate(consultation.getConsultationDate());
				consultationDetail.setUpdatedDate(consultation.getUpdatedDate());
				consultationDetail.setDoctorId(consultation.getDoctorId());
				consultationDetail.setDocumentUrl(consultation.getDocumentUrl());
				consultationDetail.setHealthWorkerId(consultation.getHealthWorkerId());
				consultationDetail.setPatientId(consultation.getPatientId());
				consultationDetail.setStatus(consultation.getStatus());
				consultationDetail.setPatient(patient);
				consultationDetails.add(consultationDetail);
			}

		} catch (Exception e) {
			myDocAPIResponseInfo.setCode(5000);
			myDocAPIResponseInfo.setDescription("Error in Processing Request");
			myDocAPIResponseInfo.setData(null);
			return new ResponseEntity<MyDocAPIResponseInfo>(myDocAPIResponseInfo, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		myDocAPIResponseInfo.setCode(0);
		myDocAPIResponseInfo.setDescription("Success");
		myDocAPIResponseInfo.setData(consultationDetails);
		return new ResponseEntity<MyDocAPIResponseInfo>(myDocAPIResponseInfo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "mydoc/users/updateConsultationDetail", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MyDocAPIResponseInfo> updateConsulationDetailsForUser(
			@RequestBody HashMap<String, String> requestData) {
		MyDocAPIResponseInfo myDocAPIResponseInfo = new MyDocAPIResponseInfo();
		String consultationId = requestData.get("consultationId");
		DBConsultation dbConsultation = consultationRepository.findByConsultationId(consultationId);
		try {

			if (dbConsultation == null) {
				myDocAPIResponseInfo.setCode(ErrorConstant.BAD_REQUEST);
				myDocAPIResponseInfo.setDescription("There is no record with that consultation Number");
				myDocAPIResponseInfo.setData(null);
				return new ResponseEntity<MyDocAPIResponseInfo>(myDocAPIResponseInfo, HttpStatus.BAD_REQUEST);
			} else {
				dbConsultation.setUpdatedDate(new Date());
			}
		} catch (Exception e) {
			myDocAPIResponseInfo.setCode(5000);
			myDocAPIResponseInfo.setDescription("Error in Processing Request");
			myDocAPIResponseInfo.setData(null);
			return new ResponseEntity<MyDocAPIResponseInfo>(myDocAPIResponseInfo, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		myDocAPIResponseInfo.setCode(0);
		myDocAPIResponseInfo.setDescription("Success");
		myDocAPIResponseInfo.setData(dbConsultation);
		return new ResponseEntity<MyDocAPIResponseInfo>(myDocAPIResponseInfo, HttpStatus.OK);
	}

	private boolean userExist(String mobileNumber) {
		return userRepository.findByMobileNumber(mobileNumber) != null;
	}
}