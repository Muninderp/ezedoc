package org.dbs.mydoc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoURI;

@Configuration
@EnableMongoRepositories(basePackages = "org.dbs.mydoc.repository")
public class MongoConfig extends AbstractMongoConfiguration {

	@Override
	protected String getDatabaseName() {
		return "mydoc_db";
	}

	@SuppressWarnings("deprecation")
	@Override
	public Mongo mongo() throws Exception {
		@SuppressWarnings("deprecation")
		MongoURI uri = new MongoURI("mongodb://amrendra_db:testmongo@ds117913.mlab.com:17913/mydoc_db");

		return new Mongo(uri);
	}

	@Override
	protected String getMappingBasePackage() {
		return "org.dbs.mydoc.persistence.entity";
	}
}