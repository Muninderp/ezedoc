package org.dbs.mydoc.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.dbs.mydoc.business.entity.LoginDetails;
import org.dbs.mydoc.business.entity.User;
import org.dbs.mydoc.data.format.MyDocAPIResponseInfo;
import org.dbs.mydoc.persistence.document.DBUser;
import org.dbs.mydoc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

	private static final Logger LOGGER = Logger.getLogger(UserController.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

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
			dbUser.setPassword(passwordEncoder.encode(user.getPassword()));
			dbUser.setMobileNumber(user.getMobileNumber());
			dbUser.setPracticeId(user.getPracticeId());
			dbUser.setUserType(user.getUserType());
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
						myDocAPIResponseInfo.setData(dbUser.getId());
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
	public ResponseEntity<MyDocAPIResponseInfo> logout(@Valid String sessionID) {
		DBUser dbUser = null;
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

	private boolean userExist(String mobileNumber) {

		return userRepository.findByMobileNumber(mobileNumber) != null;
	}
}
