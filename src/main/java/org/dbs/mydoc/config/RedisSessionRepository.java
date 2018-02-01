
package org.dbs.mydoc.config;

import java.io.StringReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.module.SimpleModule;
import org.dbs.mydoc.data.format.WSBody;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import redis.clients.jedis.Jedis;

public class RedisSessionRepository {

	private static final Logger LOGGER = LogManager.getLogger(RedisSessionRepository.class);

	private static final ObjectMapper objectMapper = new ObjectMapper();

	private static SimpleModule module = new SimpleModule("Module", new Version(1, 0, 0, null));

	static {
		/*
		 * module.addDeserializer(new DateDeserializer()); module.addSerializer(new
		 * DateSerializer());
		 */
		// objectMapper.registerModule(module);
		// StdSerializerProvider sp = new StdSerializerProvider();
		// sp.setDefaultKeySerializer(new DateSerializer());
	}

	private static final String AUTH_API_KEY = "auth-api-key";
	private static final String SESSION_CONST = "SESSION:";
	private long SESSION_TIMEOUT = 0;
	private String SESSION_TIMEUNIT = null;

	private RestTemplate rest;

	private String getUrl;

	private String putUrl;

	private String deleteurl;

	private HttpMethod putMethod;

	private HttpMethod getMethod;

	private HttpMethod deleteMethod;

	private Jedis jedis;

	/**
	 * 
	 */

	@PostConstruct
	public void init() throws Exception {
		/*
		 * if(!(StringUtils.isEmpty(env.getProperty("session.timeout")))){
		 * SESSION_TIMEOUT = Long.parseLong(env.getProperty("session.timeout")); }
		 * SESSION_TIMEUNIT=env.getProperty("session.timeoutunit");
		 */
	}

	public RedisSessionRepository(RestTemplate restTemplate) {
		this.rest = restTemplate;
	}

	public void setUrltoPut(String url, HttpMethod method) {
		this.putUrl = url;
		this.putMethod = method;
	}

	public void setUrltoGet(String url, HttpMethod method) {
		this.getUrl = url;
		this.getMethod = method;
	}

	public void setUrltoDelete(String url, HttpMethod method) {
		this.deleteurl = url;
		this.deleteMethod = method;
	}

	/**
	 * Store objects into redis with an associated key
	 * 
	 * @param key
	 * @param obj
	 */
	public void setObject(String key, Object obj) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		WSBody sessionRequestBody = new WSBody();
		sessionRequestBody.getData().setIdentifier(SESSION_CONST + key);
		sessionRequestBody.getData().setValue(obj);
		sessionRequestBody.getData().setTime(SESSION_TIMEOUT);
		sessionRequestBody.getData().setTimeunit(SESSION_TIMEUNIT);
		HttpEntity<WSBody> sessionEntity = new HttpEntity<WSBody>(sessionRequestBody, headers);

		if (null == putUrl || putUrl.length() < 0) {
			LOGGER.error("No URL specified for put cache ");
		}

		try {
			jedis.sadd(key, (String) obj);
			boolean isString = obj instanceof String;
			if (!isString) {
				int uniqueCode = obj.hashCode();
				WSBody uniqueCodeRequestBody = new WSBody();
				uniqueCodeRequestBody.getData().setIdentifier(SESSION_CONST + uniqueCode);
				uniqueCodeRequestBody.getData().setValue(key);
				uniqueCodeRequestBody.getData().setTime(SESSION_TIMEOUT + 1);
				uniqueCodeRequestBody.getData().setTimeunit(SESSION_TIMEUNIT);
				HttpEntity<WSBody> uniqueSessionEntity = new HttpEntity<WSBody>(uniqueCodeRequestBody, headers);
				rest.exchange(putUrl, putMethod, uniqueSessionEntity, Map.class);
			}

		} catch (Exception e) {
			LOGGER.error(" Unable to store object in Redis :::::::::::::::::::::  ::::::::::::::::::: key: " + key, e);
		}

	}

	/**
	 * Retrieve Objects from Redis using specific key
	 * 
	 * @param key
	 * @return
	 */
	public Object getObject(String key) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		Object object = null;

		Map<String, Object> responseBody = null;
		Map<String, String> requestBody = new HashMap<String, String>();
		requestBody.put("identifier", SESSION_CONST + key);
		HttpEntity<?> entity = new HttpEntity(headers);
		if (null == getUrl || getUrl.length() < 0) {
			LOGGER.error("No URL specified for get cache ");
		}
		try {
			jedis.get(key);
			ResponseEntity<WSBody> result = rest.exchange(getUrl, getMethod, entity, WSBody.class, requestBody);
			object = result.getBody().getData().getValue();
			if (object != null) {
				setObject(key, object);
			}

		} catch (Exception e) {
			LOGGER.error("Unable to get object from Redis; key = " + SESSION_CONST + key, e);
		}

		return object;

	}

	/**
	 * Retrieve Objects from Redis using specific key
	 * 
	 * @param key
	 * @return
	 */
	public Object getObject(String key, Class requiredClass) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		Object object = null;

		Map<String, String> requestBody = new HashMap<String, String>();
		requestBody.put("identifier", SESSION_CONST + key);
		HttpEntity<?> entity = new HttpEntity(headers);
		if (null == getUrl || getUrl.length() < 0) {
			LOGGER.error("No URL specified for get cache ");
		}
		try {
			ResponseEntity<String> result = rest.exchange(getUrl, getMethod, entity, String.class, requestBody);

			JsonNode rootNode = objectMapper.readTree(new StringReader(result.getBody()));

			object = objectMapper.readValue(rootNode.path("data").path("value"),
					requiredClass.newInstance().getClass());
			if (object != null) {
				setObject(key, object);
			}
		} catch (Exception e) {
			LOGGER.error("Unable to get object from Redis, key = " + key, e);
		}

		return object;

	}

	protected String getHttpSessionKey(HttpServletRequest request) {
		String sessionKey = request.getHeader(AUTH_API_KEY);
		// return ApiTokenSecurity.decrypt(sessionKey);
		return sessionKey;
	}

	public String getHttpSessionKey(HttpServletResponse response) {
		String sessionKey = response.getHeader(AUTH_API_KEY);
		// return ApiTokenSecurity.decrypt(sessionKey);
		return sessionKey;
	}

	public void setHttpResponse(HttpServletResponse response, String attribute) {
		// String encryptKey = ApiTokenSecurity.encrypt(attribute);
		response.setHeader(AUTH_API_KEY, attribute);
	}

	public void removeObject(String key) {

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		Object object = null;

		Map<String, String> requestBody = new HashMap<String, String>();
		requestBody.put("identifier", SESSION_CONST + key);
		HttpEntity<?> entity = new HttpEntity(headers);
		if (null == deleteurl || deleteurl.length() < 0) {
			LOGGER.error("No URL specified for remove cache ");
		}
		try {
			ResponseEntity<WSBody> result = rest.exchange(deleteurl, deleteMethod, entity, WSBody.class, requestBody);
			object = result.getBody().getData().getValue();

		} catch (Exception e) {
			LOGGER.error("Unable to remove objects from cache, key = " + key, e);
		}
	}

	public void removeKey(String key) {

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		Object object = null;

		Map<String, String> requestBody = new HashMap<String, String>();
		requestBody.put("identifier", key);
		HttpEntity<?> entity = new HttpEntity(headers);
		if (null == deleteurl || deleteurl.length() < 0) {
			LOGGER.error("No URL specified for remove cache ");
		}
		try {
			ResponseEntity<WSBody> result = rest.exchange(deleteurl, deleteMethod, entity, WSBody.class, requestBody);
			object = result.getBody().getData().getValue();

		} catch (Exception e) {
			LOGGER.error("Unable to remove key from cache, key = " + key, e);
		}
	}
}
