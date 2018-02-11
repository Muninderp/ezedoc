package org.dbs.mydoc.config;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.regex.Pattern;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.header.writers.HstsHeaderWriter;
import org.springframework.security.web.header.writers.XContentTypeOptionsHeaderWriter;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter.XFrameOptionsMode;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.session.web.http.HttpSessionStrategy;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration
@EnableWebSecurity
@EnableRedisHttpSession
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static final Logger LOGGER = LogManager.getLogger(SecurityConfig.class);

	private static final String X_CSRF_TOKEN = "X-CSRF-TOKEN";
	private static final String EXT_TOKEN = "EXT-TOKEN";
	private static final String EXT_TOKEN_VAL = "EXT756837465836642387587218u6098347968573";
	private final RequestMatcher requireCsrfProtectionMatcher = new DefaultRequiresCsrfMatcher();
	private final AccessDeniedHandler accessDeniedHandler = new AccessDeniedHandlerImpl();

	private String disable_csrf_flag;

	private final Keys keys = new Keys();
	
	
	@Autowired
	private Environment env;

	
	
	  @Bean
	    public HttpSessionStrategy httpSessionStrategy() {
	        return new HeaderHttpSessionStrategy();
	    }

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("admin").password("password").roles("ADMIN");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		
		/* http
         .authorizeRequests()
         .anyRequest().authenticated()
         .and()
         .requestCache()
         .requestCache(new NullRequestCache())
         .and()
         .httpBasic();*/
		
		/*http.exceptionHandling().accessDeniedPage("/403");
		http.authorizeRequests().antMatchers("/login**").permitAll();

		http.formLogin().loginProcessingUrl("/login");
		http.logout().logoutSuccessUrl("/logout");
		// header protection
		http.headers().addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsMode.SAMEORIGIN))
				.addHeaderWriter(new XContentTypeOptionsHeaderWriter())
				.addHeaderWriter(new XXssProtectionHeaderWriter()).addHeaderWriter(new HstsHeaderWriter());

		try {
			disable_csrf_flag = "false";

			LOGGER.warn("global csrf filter disabled flag = " + disable_csrf_flag);
		} catch (Exception e) {
		}
		// xsrf protection
		if (disable_csrf_flag != null && disable_csrf_flag.equalsIgnoreCase("true")) {
			LOGGER.warn("Skipping CSRF token check");
			http.csrf().disable();
		} else {
			http.csrf().disable().addFilterBefore(securityFilter(), CsrfFilter.class);
		}
		http.addFilterAfter(sessionFilter(), CsrfFilter.class);*/
	}

	private Filter sessionFilter() {
		return new OncePerRequestFilter() {

			@Override
			protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
					FilterChain filterChain) throws ServletException, IOException {

				LOGGER.debug("Printing the request::::::::::");
				printRequest(request);

				String apiKey = null;
				Object obj = null;
				if (request.getRequestURI().contains("/login") || request.getRequestURI().contains("/forgotpassword")
						|| (/*
							 * redisSessionRepository.getHttpSessionKey(request) == null &&
							 */ request.getRequestURI().contains("/register"))
						|| request.getRequestURI().contains("/reset/")
						|| request.getRequestURI().contains("/verifyaccount")
						|| request.getRequestURI().contains("/validatemail/")
						|| request.getRequestURI().contains("/signup/checkexistingemail")
						|| request.getRequestURI().contains("/signup/verifymobilenumber")
						|| request.getRequestURI().contains("/forcelogout")) {
					apiKey = keys.generateApiKey();
					obj = apiKey;
				} else {

					apiKey =  null;// TODO : Need to change to get From Redis //
													// redisSessionRepository.getHttpSessionKey(request);

					LOGGER.debug("Security Config request uri: " + request.getRequestURI() + " apikey from redis: "
							+ apiKey);

					if (apiKey == null || apiKey.equals("null")) {
						accessDeniedHandler.handle(request, response,
								new AccessDeniedException("Missing or non-matching apikey"));
						return;
					} else {
						obj = new Object();// ;
						if (obj == null) {
							accessDeniedHandler.handle(request, response,
									new AccessDeniedException("Session has expired."));
						}
					}
					//redisSessionRepository.setObject(apiKey, obj);
				}

				// redisSessionRepository.setHttpResponse(response, apiKey);
				response.setHeader("auth-api-key", apiKey);

				filterChain.doFilter(request, response);

			}

			private void printRequest(HttpServletRequest request) {
				LOGGER.debug("inside filter: request uri= " + request.getRequestURI());
				Enumeration<String> headerNames = request.getHeaderNames();
				while (headerNames.hasMoreElements()) {
					String headerName = (String) headerNames.nextElement();
					LOGGER.debug("Header Name - " + headerName + ", Value - " + request.getHeader(headerName));
				}
				Enumeration<String> params = request.getParameterNames();
				while (params.hasMoreElements()) {
					String paramName = (String) params.nextElement();
					LOGGER.debug("Parameter Name - " + paramName + ", Value - " + request.getParameter(paramName));
				}
			}
		};
	}

	private Filter securityFilter() {
		return new OncePerRequestFilter() {

			@Override
			protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
					FilterChain filterChain) throws ServletException, IOException {

				HttpHeaders headers = new HttpHeaders();
				headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug(
							"SecurityFilter Intercepts " + request.getRequestURI() + " method " + request.getMethod());
				}
				String csrfToken = request.getHeader(X_CSRF_TOKEN);
				if (request.getRequestURI().contains("/login") || request.getRequestURI().contains("/forgotpassword")
						|| request.getRequestURI().contains("/api/")
						|| request.getRequestURI().contains("/verifyaccount")
						|| request.getRequestURI().contains("/validatemail/")
						|| request.getRequestURI().contains("/signup/verifymobilenumber")
						|| request.getRequestURI().contains("/signup/checkexistingemail")
						|| request.getRequestURI().contains("/reset/") || request.getRequestURI().contains("/logout")
						|| request.getRequestURI().contains("/encbene")
						|| request.getRequestURI().contains("/forcelogout")
						|| request.getRequestURI().contains("/closingBal")) {
					// Generate Csrf Token
					csrfToken = keys.generateCrsfToken();
				} else {
					String redisToken = (String) "123456";// redisSessionRepository.getObject(csrfToken);

					if (null == redisToken) {
						if (null == disable_csrf_flag) {
							disable_csrf_flag = "false";// env.getProperty("global.csrf.filter.disabled");
						}
						if (disable_csrf_flag.equalsIgnoreCase("true")) {
							LOGGER.warn("***** Skipping CSRF token check *****");
							filterChain.doFilter(request, response);
							return;
						}
						accessDeniedHandler.handle(request, response,
								new AccessDeniedException("Missing or non-matching CSRF-token"));// Need to check that Logic for CSRF Regenration.Need to generate it for evry request.
						return;
					} else if (request.getHeader(EXT_TOKEN) != null
							&& request.getHeader(EXT_TOKEN).equals(EXT_TOKEN_VAL)) {
						csrfToken = redisToken;
					} else {
						if (requireCsrfProtectionMatcher.matches(request)) {
							// delete old csrf token
							// redisSessionRepository.removeObject(csrfToken);
							// create new csrf token
							csrfToken = keys.generateCrsfToken();
						}

					}
				}
				// redisSessionRepository.setObject(csrfToken, csrfToken);
				// String csrfEncrypt = ApiTokenSecurity.encrypt(csrfToken);
				response.setHeader(X_CSRF_TOKEN, csrfToken);
				filterChain.doFilter(request, response);
			}
		};
	}

	public static final class DefaultRequiresCsrfMatcher implements RequestMatcher {
		private final Pattern allowedMethods = Pattern.compile("^(GET|HEAD|TRACE|OPTIONS)$");

		@Override
		public boolean matches(HttpServletRequest request) {
			return !allowedMethods.matcher(request.getMethod()).matches();
		}
	}

	public static final class Keys implements RandomKeyGenerator {

		private final SecureRandom random = new SecureRandom();

		@Override
		public String generateApiKey() {
			String apiKey = null;
			try {
				KeyGenerator keyGen = KeyGenerator.getInstance("AES");
				keyGen.init(128);
				SecretKey secretKey = keyGen.generateKey();
				byte[] encoded = secretKey.getEncoded();
				apiKey = DatatypeConverter.printHexBinary(encoded).toLowerCase();
			} catch (NoSuchAlgorithmException e) {
				LOGGER.error("[  Exception occurred    ]  " + e.getStackTrace(), e);
			}
			return apiKey;
		}

		@Override
		public String generateCrsfToken() {
			final byte[] bytes = new byte[32];
			random.nextBytes(bytes);
			return Base64.encode(bytes).toString();
		}

	}
	
	@Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(11);
    }
}
