package org.dbs.mydoc;

import org.apache.catalina.security.SecurityConfig;
import org.dbs.mydoc.config.MyDocConfig;
import org.dbs.mydoc.config.SessionConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@SpringBootApplication(scanBasePackages = "org.dbs.mydoc")
@ComponentScan("org.dbs.mydoc")
public class MydocApplication {

	

	public static void main(String[] args) {
		SpringApplication.run(new Class[] { MydocApplication.class, MyDocConfig.class, SecurityConfig.class, SessionConfig.class}, args);
	}

}