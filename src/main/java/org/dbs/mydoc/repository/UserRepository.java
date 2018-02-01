package org.dbs.mydoc.repository;

import org.dbs.mydoc.persistence.document.DBUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<DBUser, Integer> {

}
